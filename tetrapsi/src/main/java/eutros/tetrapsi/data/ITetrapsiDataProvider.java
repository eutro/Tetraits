package eutros.tetrapsi.data;

import eutros.tetraits.data.gen.template.Pattern;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.util.ResourceLocation;
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

    Pattern.IKey<String> NAME = Pattern.IKey.of(String.class, "NAME");
    Pattern.IKey<Integer> TINT = Pattern.IKey.of(Integer.class, "TINT");

    Pattern.IKey<Integer> CAD_EFFICIENCY = Pattern.IKey.of(Integer.class, "CAD_EFFICIENCY");
    Pattern.IKey<Integer> CAD_POTENCY = Pattern.IKey.of(Integer.class, "CAD_POTENCY");
    Pattern.IKey<Integer> CAD_COMPLEXITY = Pattern.IKey.of(Integer.class, "CAD_COMPLEXITY");
    Pattern.IKey<Integer> CAD_PROJECTION = Pattern.IKey.of(Integer.class, "CAD_PROJECTION");
    Pattern.IKey<Integer> CAD_BANDWIDTH = Pattern.IKey.of(Integer.class, "CAD_BANDWIDTH");
    Pattern.IKey<Integer> CAD_SOCKETS = Pattern.IKey.of(Integer.class, "CAD_SOCKETS");

    Pattern.IKey<Integer> TOOL_MAX_USES = Pattern.IKey.of(Integer.class, "TOOL_MAX_USES");
    Pattern.IKey<Float> TOOL_EFFICIENCY = Pattern.IKey.of(Float.class, "TOOL_EFFICIENCY");
    Pattern.IKey<Float> TOOL_ATTACK_DAMAGE = Pattern.IKey.of(Float.class, "TOOL_ATTACK_DAMAGE");
    Pattern.IKey<Integer> TOOL_HARVEST_LEVEL = Pattern.IKey.of(Integer.class, "TOOL_HARVEST_LEVEL");
    Pattern.IKey<Integer> TOOL_ENCHANTABILITY = Pattern.IKey.of(Integer.class, "TOOL_ENCHANTABILITY");

    Pattern.IKey<ItemPredicate> PREDICATE = Pattern.IKey.of(ItemPredicate.class, "PREDICATE");

    Pattern.IKey<String> MODULE_TYPE_KEY = Pattern.IKey.of(String.class, "MODULE_TYPE");

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

}
