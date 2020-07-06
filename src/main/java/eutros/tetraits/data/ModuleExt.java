package eutros.tetraits.data;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.*;
import eutros.tetraits.Tetraits;
import eutros.tetraits.network.PacketHandler;
import eutros.tetraits.network.UpdateModuleExtPacket;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.tuple.Pair;
import se.mickelus.tetra.items.modular.IItemModular;
import se.mickelus.tetra.module.ItemModule;
import se.mickelus.tetra.module.ModuleRegistry;
import se.mickelus.tetra.module.data.ModuleVariantData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Simulate existing item effects.
 * <p>
 * Waiting for API...
 */
public class ModuleExt {

    public static final JsonParser PARSER = new JsonParser();
    //                moduleKey, variantKey
    private Multimap<Pair<String, String>, ResourceLocation> moduleEffectMap = HashMultimap.create();

    public List<ResourceLocation> getTraits(ItemStack stack) {
        Item item = stack.getItem();
        if(item instanceof IItemModular) {
            IItemModular iim = (IItemModular) item;

            return Stream.concat(
                    Arrays.stream(iim.getMinorModules(stack)),
                    Arrays.stream(iim.getMajorModules(stack))
            )
                    .filter(Objects::nonNull)
                    .map(im -> getModulePair(im, im.getVariantData(stack)))
                    .map(moduleEffectMap::get)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Nonnull
    private Pair<String, String> getModulePair(ItemModule module, ModuleVariantData variant) {
        return Pair.of(module.getKey(), variant.getKey());
    }

    public void reset() {
        moduleEffectMap.clear();
    }

    public void load(IResourceManager rm) {
        for(ResourceLocation rl : rm.getAllResourceLocations("module_ext", s -> FilenameUtils.getExtension(s).equals("json"))) {
            IResource resource;
            try {
                resource = rm.getResource(rl);
            } catch(IOException e) {
                Tetraits.LOGGER.error(String.format("Couldn't load resource: %s.", rl), e);
                continue;
            }

            JsonElement el;
            try {
                el = PARSER.parse(new InputStreamReader(resource.getInputStream()));
            } catch(JsonSyntaxException e) {
                Tetraits.LOGGER.error(String.format("Error reading: %s", rl), e);
                continue;
            }

            if(el.isJsonArray()) {
                el.getAsJsonArray().forEach(e -> add(e, rl));
            } else {
                add(el, rl);
            }
        }
    }

    private void add(JsonElement el, ResourceLocation rl) {
        if(!el.isJsonObject()) {
            logError(rl, null, ErrorType.BAD_TYPE, "Object or Array of Objects");
            return;
        }

        JsonObject obj = el.getAsJsonObject();
        ResourceLocation moduleRl;
        try {
            moduleRl = new ResourceLocation(obj.getAsJsonPrimitive("module").getAsString());
        } catch(JsonSyntaxException | NullPointerException e) {
            logError(rl, String.format("Object: \"%s\". ", obj), ErrorType.BAD_KEY, "String", "module");
            return;
        }

        ItemModule module = ModuleRegistry.instance.getModule(moduleRl);
        if(module == null) {
            logError(rl, null, ErrorType.MISSING_REGISTRY, null, "module", moduleRl);
            return;
        }

        JsonArray variants = obj.getAsJsonArray("variants");
        if(variants == null) {
            logError(rl, String.format("Object: \"%s\". ", obj), ErrorType.BAD_KEY, "Array of Objects", "variants");
            return;
        }

        for(JsonElement variant : variants) {
            if(!variant.isJsonObject()) {
                logError(rl, String.format("Got: \"%s\". ", variants), ErrorType.BAD_KEY, "Array of Objects", "variants");
                continue;
            }
            JsonObject vObj = variant.getAsJsonObject();
            JsonPrimitive key = vObj.getAsJsonPrimitive("key");
            if(key == null) {
                logError(rl, String.format("Variant: \"%s\". ", vObj), ErrorType.BAD_KEY, "String", "key");
                continue;
            }

            JsonArray traits = vObj.getAsJsonArray("traits");
            if(traits == null) {
                logError(rl, String.format("Variant: \"%s\". ", vObj), ErrorType.BAD_KEY, "Array of Strings", "traits");
                continue;
            }

            StreamSupport.stream(traits.spliterator(), true)
                    .filter(e -> {
                        if(e.isJsonPrimitive()) return true;
                        Tetraits.LOGGER.error(String.format("Ignoring trait: %s, not a String.", e));
                        return false;
                    })
                    .map(JsonElement::getAsString)
                    .map(ResourceLocation::new)
                    .forEach(trait -> {
                        moduleEffectMap.put(Pair.of(module.getKey(), key.getAsString()), trait);
                        Tetraits.LOGGER.info(String.format("Added trait \"%s\" for variant \"%s\" of module \"%s\".", trait, key.getAsString(), module.getKey()));
                    });
        }
    }

    public void setMap(Multimap<Pair<String, String>, ResourceLocation> map) {
        moduleEffectMap.clear();
        moduleEffectMap.putAll(map);
    }

    public void sync(ServerPlayerEntity player) {
        PacketHandler.sendToPlayer(player, new UpdateModuleExtPacket(moduleEffectMap));
    }

    private enum ErrorType {
        BAD_TYPE("Bad type. "),
        MISSING_REGISTRY("No %s for key: \"%s\". "),
        BAD_KEY("Missing/invalid key: \"%s\". ");

        private final String s;

        ErrorType(String s) {
            this.s = s;
        }
    }

    private void logError(ResourceLocation rl, @Nullable String obj, ErrorType type, @Nullable String expected, Object... typeExtra) {
        StringBuilder log = new StringBuilder(String.format("Bad format: %s. ", rl));
        if(obj != null) {
            log.append(obj);
        }
        log.append(String.format(type.s, typeExtra));
        if(expected != null) {
            log.append(String.format("Expected: %s.", expected));
        }
        Tetraits.LOGGER.error(log);
    }

}
