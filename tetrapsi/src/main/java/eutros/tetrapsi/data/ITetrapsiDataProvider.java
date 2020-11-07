package eutros.tetrapsi.data;

import eutros.tetraits.data.gen.template.Pattern;
import eutros.tetraits.data.gen.template.Pattern.IKey;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.util.ResourceLocation;
import se.mickelus.tetra.capabilities.Capability;
import vazkii.psi.common.lib.ModTags;

import java.util.StringJoiner;

public interface ITetrapsiDataProvider {

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
            .property(PREDICATE,
                    ItemPredicate.Builder
                            .create()
                            .tag(ModTags.INGOT_PSIMETAL)
                            .build());

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
            .property(PREDICATE,
                    ItemPredicate.Builder
                            .create()
                            .tag(ModTags.INGOT_EBONY_PSIMETAL)
                            .build());

    Pattern IVORY = Pattern.create()
            .property(NAME, "ivory_psimetal")
            .property(TINT, 0x201f1f)
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
            .property(PREDICATE,
                    ItemPredicate.Builder
                            .create()
                            .tag(ModTags.INGOT_IVORY_PSIMETAL)
                            .build());

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
    IKey<Integer> INTEGRITY_OFFSET = IKey.of(Integer.class, "INTEGRITY_OFFSET");
    IKey<Integer> CAPABILITY_OFFSET = IKey.of(Integer.class, "CAPABILITY_OFFSET");

    IKey<Capability[]> SUPPLIED_CAPS = IKey.of(Capability[].class, "SUPPLIED_CAPS");
    IKey<Float> CAP_EFF_OFFSET = IKey.of(Float.class, "CAP_EFF_OFFSET");
    IKey<Float> CAP_EFF_MUL = IKey.of(Float.class, "CAP_EFF_MUL");

    static Pattern module(String moduleType, String module, Capability[] requiredCaps,
                          int materialCount,
                          int glyphX, int glyphY, ResourceLocation model,
                          int durabilityOffset, float durabilityMultiplier,
                          float speedOffset, float speedMultiplier,
                          int integrityOffset,
                          float damageOffset, float damageMultiplier,
                          int capabilityOffset,
                          Capability[] suppliedCaps, float capEffOffset, float capEffMultiplier) {
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
                .property(INTEGRITY_OFFSET, integrityOffset)
                .property(DAMAGE_OFFSET, damageOffset)
                .property(DAMAGE_MULTIPLIER, damageMultiplier)
                .property(CAPABILITY_OFFSET, capabilityOffset)
                .property(SUPPLIED_CAPS, suppliedCaps)
                .property(CAP_EFF_OFFSET, capEffOffset)
                .property(CAP_EFF_MUL, capEffMultiplier);
    }

    Pattern[] modules = {
            module(join("double", "basic_pickaxe"), "basic_pickaxe", new Capability[] {Capability.hammer},
                    2,
                    32, 0, tetra("items/module/double/head/basic_pickaxe/metal"),
                    0, 1,
                    -9, 0.5F,
                    0,
                    0, 1,
                    0,
                    new Capability[] {Capability.pickaxe}, 0, 1),
            module(join("double", "basic_hammer"), "basic_hammer", new Capability[] {Capability.hammer},
                    2,
                    64, 0, tetra("items/module/double/head/basic_hammer/metal"),
                    0, 1,
                    0, 1,
                    0,
                    1, 1,
                    0,
                    new Capability[] {Capability.hammer}, 0, 0.7F),
    };

}
