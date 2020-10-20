package eutros.tetrapsi.data;

import eutros.tetraits.data.gen.template.BuilderTemplate;
import eutros.tetraits.data.gen.template.Pattern;
import eutros.tetraits.data.gen.ModuleDataProvider;
import eutros.tetraits.data.gen.template.Pattern.IKey;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import se.mickelus.tetra.module.data.ModuleData;
import se.mickelus.tetra.module.data.ModuleModel;
import se.mickelus.tetra.module.data.ModuleVariantData;

import javax.annotation.Nonnull;
import java.io.IOException;

import static eutros.tetrapsi.data.ITetrapsiDataProvider.*;

public class TetrapsiModuleProvider extends ModuleDataProvider implements ITetrapsiDataProvider {

    public static final ResourceLocation PSI_EQIPMENT = psi("equipment");
    public static final ResourceLocation PSI_REGEN = psi("regen");
    public static final ResourceLocation PSI_TOOL = psi("tool");

    public TetrapsiModuleProvider(DataGenerator gen) {
        super(gen);
    }

    private static final IKey<String> NAME = IKey.of(String.class, "NAME");
    private static final IKey<Integer> TINT = IKey.of(Integer.class, "TINT");

    private static final IKey<Integer> CAD_EFFICIENCY = IKey.of(Integer.class, "CAD_EFFICIENCY");
    private static final IKey<Integer> CAD_POTENCY = IKey.of(Integer.class, "CAD_POTENCY");
    private static final IKey<Integer> CAD_COMPLEXITY = IKey.of(Integer.class, "CAD_COMPLEXITY");
    private static final IKey<Integer> CAD_PROJECTION = IKey.of(Integer.class, "CAD_PROJECTION");
    private static final IKey<Integer> CAD_BANDWIDTH = IKey.of(Integer.class, "CAD_BANDWIDTH");
    private static final IKey<Integer> CAD_SOCKETS = IKey.of(Integer.class, "CAD_SOCKETS");

    private static final IKey<Integer> TOOL_MAX_USES = IKey.of(Integer.class, "TOOL_MAX_USES");
    private static final IKey<Float> TOOL_EFFICIENCY = IKey.of(Float.class, "TOOL_EFFICIENCY");
    private static final IKey<Float> TOOL_ATTACK_DAMAGE = IKey.of(Float.class, "TOOL_ATTACK_DAMAGE");
    private static final IKey<Integer> TOOL_HARVEST_LEVEL = IKey.of(Integer.class, "TOOL_HARVEST_LEVEL");
    private static final IKey<Integer> TOOL_ENCHANTABILITY = IKey.of(Integer.class, "TOOL_ENCHANTABILITY");

    // vazkii.psi.common.item.component.DefaultStats

    private static Pattern PSIMETAL =
            Pattern.create()
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
                    .property(TOOL_ENCHANTABILITY, 12);

    private static Pattern EBONY =
            Pattern.create()
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
                    .property(TOOL_HARVEST_LEVEL, 3)
                    .property(TOOL_ENCHANTABILITY, 15); // sockets * 1.5

    private static Pattern IVORY =
            Pattern.create()
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
                    .property(TOOL_HARVEST_LEVEL, 3)
                    .property(TOOL_ENCHANTABILITY, 18); // sockets * 1.5

    private static final IKey<BuilderTemplate<ModuleVariantData>> VARIANT_TEMPLATE_KEY =
            IKey.ofUnchecked(BuilderTemplate.class, "VARIANT_KEY");
    private static final IKey<Pattern> MODULE_PATTERN_KEY = IKey.of(Pattern.class, "MODULE_PATTERN");
    private static final IKey<String> MODULE_TYPE_KEY = IKey.of(String.class, "MODULE_TYPE");

    private static BuilderTemplate<ModuleData> MODULE_TEMPLATE =
            BuilderTemplate.TemplateBuilder.<ModuleBuilder>create()
                    .handle(p -> b -> {
                        BuilderTemplate<ModuleVariantData> t = p.get(VARIANT_TEMPLATE_KEY);
                        return b.variant(t.apply(PSIMETAL.copy().property(MODULE_PATTERN_KEY, p)))
                                .variant(t.apply(EBONY.copy().property(MODULE_PATTERN_KEY, p)))
                                .variant(t.apply(IVORY.copy().property(MODULE_PATTERN_KEY, p)));
                    })
                    .build(p -> ModuleBuilder.create(),
                            p -> ModuleBuilder::build);

    private static final IKey<Integer> GLYPH_X = IKey.of(Integer.class, "GLYPH_X");
    private static final IKey<Integer> GLYPH_Y = IKey.of(Integer.class, "GLYPH_Y");
    private static final IKey<ResourceLocation> MODEL = IKey.of(ResourceLocation.class, "MODEL");

    private static BuilderTemplate.TemplateBuilder<ModuleVariantBuilder> VARIANT_BASE =
            BuilderTemplate.TemplateBuilder.<ModuleVariantBuilder>create()
                    .handle(p -> b -> {
                        Pattern module = p.get(MODULE_PATTERN_KEY);
                        return b.key(join(module.get(MODULE_TYPE_KEY), p.get(NAME)))
                                .glyph(p.get(TINT), module.get(GLYPH_X), module.get(GLYPH_Y))
                                .model(new ModuleModel("item", module.get(MODEL), p.get(TINT)))
                                .integrity(-p.get(TOOL_HARVEST_LEVEL))
                                .trait(PSI_EQIPMENT, true)
                                .trait(PSI_REGEN, true)
                                .trait(PSI_TOOL, true)
                                .capability(PSI_EQIPMENT, p.get(CAD_SOCKETS) / 2)
                                .magicCapacity(p.get(TOOL_ENCHANTABILITY))
                                .durability(p.get(TOOL_MAX_USES));
                    });

    @Override
    protected void collectData(@Nonnull DataConsumer<ModuleData> consumer) throws IOException {
        consumer.accept(tetra(join("double", "basic_pickaxe")),
                MODULE_TEMPLATE.apply(Pattern.create()
                        .property(VARIANT_TEMPLATE_KEY, VARIANT_BASE.copy()
                                .handle(p -> b -> b.attackSpeed(p.get(TOOL_EFFICIENCY)))
                                .build(p -> ModuleVariantBuilder.create(),
                                        p -> ModuleVariantBuilder::build))
                        .property(MODULE_TYPE_KEY, "basic_pickaxe")
                        .property(GLYPH_X, 176)
                        .property(GLYPH_Y, 0)
                        .property(MODEL, tetra("items/module/double/head/basic_pickaxe/metal"))));
    }

}
