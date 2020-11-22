package eutros.tetrapsi.data;

import eutros.tetraits.data.gen.template.Pattern;
import eutros.tetraits.data.gen.template.Pattern.IKey;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.data.IDataProvider;
import net.minecraft.util.ResourceLocation;
import se.mickelus.tetra.capabilities.Capability;
import se.mickelus.tetra.module.ItemEffect;
import vazkii.psi.common.lib.ModTags;

import javax.annotation.Nonnull;
import java.lang.reflect.Array;
import java.util.StringJoiner;

import static se.mickelus.tetra.capabilities.Capability.*;
import static se.mickelus.tetra.module.ItemEffect.*;

public interface ITetrapsiDataProvider extends IDataProvider {

    static String join(CharSequence... strings) {
        StringJoiner sj = new StringJoiner("/");
        for(CharSequence string : strings) {
            sj.add(string);
        }
        return sj.toString();
    }

    static ResourceLocation tetra(String path) {
        return new ResourceLocation("tetra", path);
    }

    static ResourceLocation psi(String path) {
        return new ResourceLocation("psi", path);
    }

    static ResourceLocation tetrapsi(String path) {
        return new ResourceLocation("tetrapsi", path);
    }

    IKey<String> NAME = IKey.of(String.class, "NAME");
    IKey<Integer> TINT = IKey.of(Integer.class, "TINT");

    IKey<Integer> CAD_EFFICIENCY = IKey.of(Integer.class, "CAD_EFFICIENCY");
    IKey<Integer> CAD_POTENCY = IKey.of(Integer.class, "CAD_POTENCY");
    IKey<Integer> CAD_COMPLEXITY = IKey.of(Integer.class, "CAD_COMPLEXITY");
    IKey<Integer> CAD_PROJECTION = IKey.of(Integer.class, "CAD_PROJECTION");
    IKey<Integer> CAD_BANDWIDTH = IKey.of(Integer.class, "CAD_BANDWIDTH");
    IKey<Integer> CAD_SOCKETS = IKey.of(Integer.class, "CAD_SOCKETS");

    IKey<Integer> TOOL_MAX_USES = IKey.of(Integer.class, "TOOL_MAX_USES");
    IKey<Float> TOOL_EFFICIENCY = IKey.of(Float.class, "TOOL_EFFICIENCY");
    IKey<Float> TOOL_ATTACK_DAMAGE = IKey.of(Float.class, "TOOL_ATTACK_DAMAGE");
    IKey<Integer> TOOL_HARVEST_LEVEL = IKey.of(Integer.class, "TOOL_HARVEST_LEVEL");
    IKey<Integer> TOOL_ENCHANTABILITY = IKey.of(Integer.class, "TOOL_ENCHANTABILITY");

    IKey<ItemPredicate> PREDICATE = IKey.of(ItemPredicate.class, "PREDICATE");

    // vazkii.psi.common.item.component.DefaultStats

    Pattern PSIMETAL = Pattern.create()
            .property(NAME, "psimetal")
            .property(TINT, 0xd2c8f1)
            // Psimetal assembly
            .property(CAD_EFFICIENCY, 85)
            .property(CAD_POTENCY, 250)
            // Conductive core
            .property(CAD_COMPLEXITY, 20)
            .property(CAD_PROJECTION, 4)
            // Large socket
            .property(CAD_BANDWIDTH, 6)
            .property(CAD_SOCKETS, 8)
            // vazkii.psi.api.material.PsimetalToolMaterial
            .property(TOOL_MAX_USES, 900)
            .property(TOOL_EFFICIENCY, 7.8F)
            .property(TOOL_ATTACK_DAMAGE, 2F)
            .property(TOOL_HARVEST_LEVEL, 3)
            .property(TOOL_ENCHANTABILITY, 12)
            .property(PREDICATE, ItemPredicate.Builder.create().tag(ModTags.INGOT_PSIMETAL).build());

    Pattern EBONY = Pattern.create()
            .property(NAME, "ebony_psimetal")
            .property(TINT, 0x201f1f)
            // Ebony assembly
            .property(CAD_EFFICIENCY, 90)
            .property(CAD_POTENCY, 350)
            // Hyperclocked core
            .property(CAD_COMPLEXITY, 36)
            .property(CAD_PROJECTION, 6)
            // Transmissive socket
            .property(CAD_BANDWIDTH, 9)
            .property(CAD_SOCKETS, 10)
            // eh
            .property(TOOL_MAX_USES, 1200) // (potency + 50) * 3
            .property(TOOL_EFFICIENCY, 8.3F) // (efficiency - 7) / 10
            .property(TOOL_ATTACK_DAMAGE, 3.5F) // bandwidth / 2 - 1
            .property(TOOL_HARVEST_LEVEL, 4)
            .property(TOOL_ENCHANTABILITY, 15) // sockets * 1.5
            .property(PREDICATE, ItemPredicate.Builder.create().tag(ModTags.INGOT_EBONY_PSIMETAL).build());

    Pattern IVORY = Pattern.create()
            .property(NAME, "ivory_psimetal")
            .property(TINT, 0xf3f7e6)
            // Ivory assembly
            .property(CAD_EFFICIENCY, 95)
            .property(CAD_POTENCY, 320)
            // Radiative core
            .property(CAD_COMPLEXITY, 30)
            .property(CAD_PROJECTION, 7)
            // Huge socket
            .property(CAD_BANDWIDTH, 8)
            .property(CAD_SOCKETS, 12)
            // eh
            .property(TOOL_MAX_USES, 1110) // (potency + 50) * 3
            .property(TOOL_EFFICIENCY, 8.8F) // (efficiency - 7) / 10
            .property(TOOL_ATTACK_DAMAGE, 3F) // bandwidth / 2 - 1
            .property(TOOL_HARVEST_LEVEL, 4)
            .property(TOOL_ENCHANTABILITY, 18) // sockets * 1.5
            .property(PREDICATE, ItemPredicate.Builder.create().tag(ModTags.INGOT_IVORY_PSIMETAL).build());

    IKey<Integer> MATERIAL_COUNT = IKey.of(Integer.class, "MATERIAL_COUNT");
    IKey<String> MODULE_TYPE_KEY = IKey.of(String.class, "MODULE_TYPE");
    IKey<String> MODULE_KEY = IKey.of(String.class, "MODULE_KEY");

    IKey<Integer> GLYPH_X = IKey.of(Integer.class, "GLYPH_X");
    IKey<Integer> GLYPH_Y = IKey.of(Integer.class, "GLYPH_Y");
    IKey<ResourceLocation> MODEL = IKey.of(ResourceLocation.class, "MODEL");

    IKey<Capability[]> REQUIRED_CAPABILITIES = IKey.of(Capability[].class, "REQUIRED_CAPABILITIES");
    IKey<Integer> DURABILITY_OFFSET = IKey.of(Integer.class, "DURABILITY_OFFSET");
    IKey<Float> DURABILITY_MULTIPLIER = IKey.of(Float.class, "DURABILITY_MULTIPLIER");
    IKey<Float> SPEED_OFFSET = IKey.of(Float.class, "SPEED_OFFSET");
    IKey<Float> SPEED_MULTIPLIER = IKey.of(Float.class, "SPEED_MULTIPLIER");
    IKey<Float> DAMAGE_OFFSET = IKey.of(Float.class, "DAMAGE_OFFSET");
    IKey<Float> DAMAGE_MULTIPLIER = IKey.of(Float.class, "DAMAGE_MULTIPLIER");
    IKey<Float> INTEGRITY_MULTIPLIER = IKey.of(Float.class, "INTEGRITY_MULTIPLIER");
    IKey<Integer> CAPABILITY_OFFSET = IKey.of(Integer.class, "CAPABILITY_OFFSET");

    IKey<Capability[]> CAPS = IKey.of(Capability[].class, "CAPS");
    IKey<ValFunc[]> CAP_VAL_FUNCS = IKey.ofUnchecked(ValFunc[].class, "CAP_VAL_FUNCS");
    IKey<EffFunc[]> CAP_EFF_FUNCS = IKey.ofUnchecked(EffFunc[].class, "CAP_EFF_FUNCS");

    IKey<ItemEffect[]> EFFS = IKey.of(ItemEffect[].class, "EFFS");
    IKey<ValFunc[]> EFF_VAL_FUNCS = IKey.ofUnchecked(ValFunc[].class, "EFF_VAL_FUNCS");
    IKey<EffFunc[]> EFF_EFF_FUNCS = IKey.ofUnchecked(EffFunc[].class, "EFF_EFF_FUNCS");

    interface ValFunc {

        int val(Pattern pattern);

    }

    interface EffFunc {

        float eff(Pattern pattern);

    }

    @SuppressWarnings("unchecked")
    static <T> T[] times(int n, @Nonnull T val) {
        T[] arr = (T[]) Array.newInstance(val.getClass(), n);
        for(int i = 0; i < n; i++) {
            arr[i] = val;
        }
        return arr;
    }
    
    @SafeVarargs
    static <T> T[] arr(T... vals) {
        return vals;
    }

    static Pattern module(String moduleType, String module, Capability[] requiredCaps,
                          int materialCount,
                          int glyphX, int glyphY, ResourceLocation model,
                          int durabilityOffset, float durabilityMultiplier,
                          float speedOffset, float speedMultiplier,
                          float integrityMultiplier,
                          float damageOffset, float damageMultiplier,
                          int capabilityOffset,
                          Capability[] suppliedCaps, float capEffOffset, float capEffMultiplier) {
        return module(moduleType, module, requiredCaps,
                materialCount,
                glyphX, glyphY, model,
                durabilityOffset, durabilityMultiplier,
                speedOffset, speedMultiplier,
                integrityMultiplier,
                damageOffset, damageMultiplier,
                capabilityOffset,
                suppliedCaps,
                times(suppliedCaps.length, p -> p.get(TOOL_HARVEST_LEVEL)),
                times(suppliedCaps.length, p -> p.get(TOOL_EFFICIENCY) * capEffMultiplier + capEffOffset),
                arr(), arr(), arr());
    }

    static Pattern module(String moduleType, String module, Capability[] requiredCaps,
                          int materialCount,
                          int glyphX, int glyphY, ResourceLocation model,
                          int durabilityOffset, float durabilityMultiplier,
                          float speedOffset, float speedMultiplier,
                          float integrityMultiplier,
                          float damageOffset, float damageMultiplier,
                          int capabilityOffset,
                          Capability[] suppliedCaps, ValFunc[] capVals, EffFunc[] capEffs,
                          ItemEffect[] suppliedEffs, ValFunc[] effVals, EffFunc[] effEffs) {
        return Pattern.create()
                .property(MATERIAL_COUNT, materialCount)
                .property(MODULE_TYPE_KEY, moduleType)
                .property(MODULE_KEY, module)
                .property(GLYPH_X, glyphX)
                .property(GLYPH_Y, glyphY)
                .property(MODEL, model)
                .property(REQUIRED_CAPABILITIES, requiredCaps)
                .property(DURABILITY_OFFSET, durabilityOffset)
                .property(DURABILITY_MULTIPLIER, durabilityMultiplier)
                .property(SPEED_OFFSET, speedOffset)
                .property(SPEED_MULTIPLIER, speedMultiplier)
                .property(INTEGRITY_MULTIPLIER, integrityMultiplier)
                .property(DAMAGE_OFFSET, damageOffset)
                .property(DAMAGE_MULTIPLIER, damageMultiplier)
                .property(CAPABILITY_OFFSET, capabilityOffset)
                .property(CAPS, suppliedCaps)
                .property(CAP_VAL_FUNCS, capVals)
                .property(CAP_EFF_FUNCS, capEffs)
                .property(EFFS, suppliedEffs)
                .property(EFF_VAL_FUNCS, effVals)
                .property(EFF_EFF_FUNCS, effEffs);
    }

    Pattern[] modules = {
            module(join("double", "adze"), "adze", arr(hammer),
                    2,
                    32, 0, tetra("items/module/double/head/adze/metal"),
                    0, 0.95F,
                    0, -0.2F,
                    -1,
                    -1, 1,
                    0,
                    arr(axe, shovel), 0, 0.7F),
            module(join("double", "basic_axe"), "basic_axe", arr(hammer),
                    2,
                    144, 0, tetra("items/module/double/head/basic_axe/metal"),
                    100, 1.1F,
                    -0.2F, -0.3F,
                    -1,
                    0, 2,
                    0,
                    arr(axe),
                    arr(p -> p.get(TOOL_HARVEST_LEVEL) + 1),
                    arr(p -> p.get(TOOL_EFFICIENCY) * 1.2F + 1),
                    arr(ItemEffect.stripping),
                    arr(p -> 1),
                    arr((EffFunc) null)),
            module(join("double", "basic_hammer"), "basic_hammer", arr(hammer),
                    2,
                    64, 0, tetra("items/module/double/head/basic_hammer/metal"),
                    0, 1,
                    0, -0.2F,
                    -1,
                    1, 1,
                    0,
                    arr(hammer), 0, 0.7F),
            module(join("double", "basic_handle"), "basic_handle", arr(hammer),
                    1,
                    16, 0, tetra("items/module/double/handle/basic/default"),
                    -10, 0.5F,
                    0, -0.2F,
                    2.5F,
                    0, 0,
                    0,
                    arr(), 0, 0),
            module(join("double", "basic_pickaxe"), "basic_pickaxe", arr(hammer),
                    2,
                    176, 0, tetra("items/module/double/head/basic_pickaxe/metal"),
                    0, 1,
                    0, -0.2F,
                    -1,
                    0, 1,
                    0,
                    arr(pickaxe), 0, 1),
            module(join("double", "butt"), "butt", arr(hammer),
                    1,
                    160, 0, tetra("items/module/double/head/butt/metal"),
                    0, 0.6F,
                    0, 0,
                    -0.5F,
                    0, 1,
                    0,
                    arr(hammer),
                    arr(p -> 1),
                    arr((EffFunc) null),
                    arr(), arr(), arr()),
            module(join("double", "claw"), "claw", arr(hammer),
                    1,
                    64, 32, tetra("items/module/double/head/claw/metal"),
                    0, 0.8F,
                    0, -0.4F,
                    -1,
                    0, 0.25F,
                    -1,
                    arr(pry),
                    arr(p -> 1),
                    arr(p -> p.get(TOOL_EFFICIENCY) * 0.6F),
                    arr(denailing),
                    arr(p -> 1),
                    arr((EffFunc) null)),
            module(join("double", "hoe"), "hoe", arr(hammer),
                    2,
                    16, 32, tetra("items/module/double/head/hoe/metal"),
                    0, 1,
                    -0.05F, -0.2F,
                    -1,
                    0, 1,
                    0,
                    arr(), arr(), arr(),
                    arr(tilling),
                    arr(p -> 1),
                    arr((EffFunc) null)),
            module(join("double", "sickle"), "sickle", arr(hammer),
                    2,
                    32, 32, tetra("items/module/double/head/sickle/metal"),
                    0, 0.95F,
                    0, -0.2F,
                    -1,
                    -1, 1,
                    0,
                    arr(cut), arr(p -> 1), arr(p -> p.get(TOOL_EFFICIENCY)),
                    arr(strikingCut, sweepingStrike, sweeping),
                    times(3, p -> 1),
                    arr(null, null, null)),
    };

}
