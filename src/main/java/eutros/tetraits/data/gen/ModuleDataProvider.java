package eutros.tetraits.data.gen;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializer;
import com.google.gson.internal.bind.JsonTreeWriter;
import com.google.gson.reflect.TypeToken;
import eutros.tetraits.handler.ASMFieldHandler;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import se.mickelus.tetra.gui.GuiTextures;
import se.mickelus.tetra.items.modular.ItemColors;
import se.mickelus.tetra.module.Priority;
import se.mickelus.tetra.module.data.*;

import java.io.IOException;
import java.util.*;

public abstract class ModuleDataProvider extends TetraDataProvider<ModuleData> {

    public ModuleDataProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected GsonBuilder addToBuilder(GsonBuilder gsonBuilder) {
        return super.addToBuilder(gsonBuilder)
                .registerTypeAdapter(GlyphData.class, (JsonSerializer<GlyphData>) (src, typeOfSrc, context) -> {
                    JsonObject obj = new JsonObject();
                    addTint(obj, src.tint);
                    if(src.textureX != 0) {
                        obj.addProperty("textureX", src.textureX);
                    }
                    if(src.textureY != 0) {
                        obj.addProperty("textureY", src.textureY);
                    }
                    if(src.textureLocation != GuiTextures.glyphs) {
                        obj.addProperty("textureLocation", src.textureLocation.toString());
                    }
                    return obj;
                })
                .registerTypeAdapter(ModuleModel.class, (JsonSerializer<ModuleModel>) (src, typeOfSrc, context) -> {
                    JsonObject obj = new JsonObject();
                    addTint(obj, src.tint);
                    obj.addProperty("location", src.location.toString());
                    if(!src.type.equals("item")) {
                        obj.addProperty("type", src.type);
                    }
                    return obj;
                })
                .registerTypeAdapter(ModuleData.class, (JsonSerializer<ModuleData>) (src, typeOfSrc, context) -> {
                    JsonTreeWriter jtr = new JsonTreeWriter();
                    try {
                        gson.getDelegateAdapter(null, TypeToken.get(ModuleData.class)).write(jtr, src);
                    } catch(IOException e) {
                        throw new RuntimeException(e);
                    }
                    JsonObject obj = jtr.get().getAsJsonObject();
                    ModuleData md = new ModuleData();
                    if(Arrays.equals(md.slots, src.slots)) obj.remove("slots");
                    if(Arrays.equals(md.slotSuffixes, src.slotSuffixes)) obj.remove("slotSuffixes");
                    if(Objects.equals(md.type, src.type)) obj.remove("type");
                    if(md.replace == src.replace) obj.remove("replace");
                    if(Objects.equals(md.renderLayer, src.renderLayer)) obj.remove("renderLayer");
                    if(Objects.equals(md.tweakKey, src.tweakKey)) obj.remove("tweakKey");
                    if(Arrays.equals(md.improvements, src.improvements)) obj.remove("improvements");
                    if(Arrays.equals(md.variants, src.variants)) obj.remove("variants");
                    return obj;
                })
                .registerTypeAdapter(ModuleVariantData.class, (JsonSerializer<ModuleVariantData>) (src, typeOfSrc, context) -> {
                    JsonTreeWriter jtr = new JsonTreeWriter();
                    try {
                        gson.getDelegateAdapter(null, TypeToken.get(ModuleVariantData.class)).write(jtr, src);
                    } catch(IOException e) {
                        throw new RuntimeException(e);
                    }
                    JsonObject obj = jtr.get().getAsJsonObject();
                    ModuleVariantData md = new ModuleVariantData();
                    if(Objects.equals(md.key, src.key)) obj.remove("key");
                    if(Objects.equals(md.namePriority, src.namePriority)) obj.remove("namePriority");
                    if(Objects.equals(md.prefixPriority, src.prefixPriority)) obj.remove("prefixPriority");
                    if(md.damage == src.damage) obj.remove("damage");
                    if(md.damageMultiplier == src.damageMultiplier) obj.remove("damageMultiplier");
                    if(md.attackSpeed == src.attackSpeed) obj.remove("attackSpeed");
                    if(md.attackSpeedMultiplier == src.attackSpeedMultiplier) obj.remove("attackSpeedMultiplier");
                    if(md.range == src.range) obj.remove("range");
                    if(md.durability == src.durability) obj.remove("durability");
                    if(md.durabilityMultiplier == src.durabilityMultiplier) obj.remove("durabilityMultiplier");
                    if(md.integrity == src.integrity) obj.remove("integrity");
                    if(md.integrityMultiplier == src.integrityMultiplier) obj.remove("integrityMultiplier");
                    // equals(...) isn't even implemented properly on these but eh
                    if(Objects.equals(md.effects, src.effects)) obj.remove("effects");
                    if(Objects.equals(md.glyph, src.glyph)) obj.remove("glyph");
                    if(Objects.equals(md.capabilities, src.capabilities)) obj.remove("capabilities");
                    if(Arrays.equals(md.models, src.models)) obj.remove("models");
                    // -
                    if(md.magicCapacity == src.magicCapacity) obj.remove("magicCapacity");
                    return obj;
                });
    }

    private static Map<String, Integer> ITEM_COLORS = ObfuscationReflectionHelper.getPrivateValue(ItemColors.class, null, "colors");

    private static void addTint(JsonObject obj, int tint) {
        boolean foundKey = false;
        for(Map.Entry<String, Integer> entry : ITEM_COLORS.entrySet()) {
            if(entry.getValue() == tint) {
                foundKey = true;
                obj.addProperty("tint", entry.getKey());
                break;
            }
        }
        if(!foundKey) {
            if(tint > 0xFFFFFF || tint < 0) {
                throw new IllegalArgumentException("tint out of range!");
            }
            String tintString = Integer.toString(tint, 16);
            obj.addProperty(
                    "tint",
                    new String(new char[6 - tintString.length()])
                            .replace('\0', '0') + tintString
            );
        }
    }

    @Override
    protected String getPath() {
        return "modules";
    }

    protected static class ModuleBuilder implements IBuilder<ModuleData> {

        private final ModuleData moduleData;

        private final List<String> slots = new LinkedList<>();
        private final List<String> slotSuffixes = new LinkedList<>();
        private final List<ResourceLocation> improvements = new LinkedList<>();
        private final List<ModuleVariantData> variants = new LinkedList<>();

        protected ModuleBuilder() {
            moduleData = new ModuleData();
        }

        public static ModuleBuilder create() {
            return new ModuleBuilder();
        }

        public ModuleBuilder type(ResourceLocation type) {
            moduleData.type = type;
            return this;
        }

        public ModuleBuilder slot(String slot) {
            slots.add(slot);
            return this;
        }

        public ModuleBuilder slotSuffix(String slotSuffix) {
            slotSuffixes.add(slotSuffix);
            return this;
        }

        public ModuleBuilder replace(boolean replace) {
            moduleData.replace = replace;
            return this;
        }

        public ModuleBuilder renderLayer(Priority renderLayer) {
            moduleData.renderLayer = renderLayer;
            return this;
        }

        public ModuleBuilder tweakKey(ResourceLocation tweakKey) {
            moduleData.tweakKey = tweakKey;
            return this;
        }

        public ModuleBuilder improvement(ResourceLocation improvement) {
            improvements.add(improvement);
            return this;
        }

        public ModuleBuilder variant(ModuleVariantData variant) {
            variants.add(variant);
            return this;
        }

        @Override
        public ModuleData build() {
            moduleData.slots = slots.toArray(moduleData.slots);
            moduleData.slotSuffixes = slotSuffixes.toArray(moduleData.slotSuffixes);
            moduleData.improvements = improvements.toArray(moduleData.improvements);
            moduleData.variants = variants.toArray(moduleData.variants);
            return moduleData;
        }

    }

    protected static class ModuleVariantBuilder implements IBuilder<ModuleVariantData> {

        private final ModuleVariantData moduleVariantData;

        private final List<ModuleModel> models = new LinkedList<>();

        protected ModuleVariantBuilder() {
            moduleVariantData = new ModuleVariantData();
        }

        public static ModuleVariantBuilder create() {
            return new ModuleVariantBuilder();
        }

        public ModuleVariantBuilder key(String key) {
            moduleVariantData.key = key;
            return this;
        }

        public ModuleVariantBuilder namePriority(Priority namePriority) {
            moduleVariantData.namePriority = namePriority;
            return this;
        }

        public ModuleVariantBuilder prefixPriority(Priority prefixPriority) {
            moduleVariantData.prefixPriority = prefixPriority;
            return this;
        }

        public ModuleVariantBuilder damage(float damage) {
            moduleVariantData.damage = damage;
            return this;
        }

        public ModuleVariantBuilder damageMultiplier(float damageMultiplier) {
            moduleVariantData.damageMultiplier = damageMultiplier;
            return this;
        }

        public ModuleVariantBuilder attackSpeed(float attackSpeed) {
            moduleVariantData.attackSpeed = attackSpeed;
            return this;
        }

        public ModuleVariantBuilder attackSpeedMultiplier(float attackSpeedMultiplier) {
            moduleVariantData.attackSpeedMultiplier = attackSpeedMultiplier;
            return this;
        }

        public ModuleVariantBuilder range(float range) {
            moduleVariantData.range = range;
            return this;
        }

        public ModuleVariantBuilder durability(int durability) {
            moduleVariantData.durability = durability;
            return this;
        }

        public ModuleVariantBuilder durabilityMultiplier(float durabilityMultiplier) {
            moduleVariantData.durabilityMultiplier = durabilityMultiplier;
            return this;
        }

        public ModuleVariantBuilder integrity(int integrity) {
            moduleVariantData.integrity = integrity;
            return this;
        }

        public ModuleVariantBuilder integrityMultiplier(float integrityMultiplier) {
            moduleVariantData.integrityMultiplier = integrityMultiplier;
            return this;
        }

        public ModuleVariantBuilder effects(EffectData effects) {
            moduleVariantData.effects = effects;
            return this;
        }

        public ModuleVariantBuilder capabilities(CapabilityData capabilities) {
            moduleVariantData.capabilities = capabilities;
            return this;
        }

        public ModuleVariantBuilder glyph(int tint, int textureX, int textureY) {
            moduleVariantData.glyph.tint = tint;
            moduleVariantData.glyph.textureX = textureX;
            moduleVariantData.glyph.textureY = textureY;
            return this;
        }

        public ModuleVariantBuilder model(ModuleModel model) {
            models.add(model);
            return this;
        }

        public ModuleVariantBuilder magicCapacity(int magicCapacity) {
            moduleVariantData.magicCapacity = magicCapacity;
            return this;
        }

        private Map<String, Object> getTetraits() {
            Map<String, Object> tetraits = ASMFieldHandler.getTetraitsField(moduleVariantData);
            if(tetraits == null) {
                ASMFieldHandler.setTetraitsField(moduleVariantData, tetraits = new HashMap<>());
            }
            return tetraits;
        }

        public ModuleVariantBuilder trait(ResourceLocation location,
                                          Object data) {
            Object traits = getTetraits().computeIfAbsent("traits", s -> new HashMap<>());
            if(!(traits instanceof Map)) throw new RuntimeException("tetraits.traits is not a map!");
            @SuppressWarnings("unchecked")
            Map<Object, Object> map = (Map<Object, Object>) traits;

            map.put(location, data);
            return this;
        }

        public ModuleVariantBuilder capability(ResourceLocation location,
                                               Object data) {
            Object traits = getTetraits().computeIfAbsent("capabilities", s -> new HashMap<>());
            if(!(traits instanceof Map)) throw new RuntimeException("tetraits.capabilities is not a map!");
            @SuppressWarnings("unchecked")
            Map<Object, Object> map = (Map<Object, Object>) traits;

            map.put(location, data);
            return this;
        }

        @Override
        public ModuleVariantData build() {
            moduleVariantData.models = models.toArray(moduleVariantData.models);
            return moduleVariantData;
        }

    }

}
