package eutros.tetrapsi.data;

import eutros.tetraits.data.gen.template.BuilderTemplate;
import eutros.tetraits.data.gen.template.Pattern;
import eutros.tetraits.data.gen.ModuleDataProvider;
import eutros.tetraits.data.gen.template.Pattern.IKey;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import se.mickelus.tetra.capabilities.Capability;
import se.mickelus.tetra.module.ItemEffect;
import se.mickelus.tetra.module.data.*;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.function.Function;

import static eutros.tetrapsi.data.ITetrapsiDataProvider.*;
import static se.mickelus.tetra.capabilities.Capability.hammer;
import static se.mickelus.tetra.capabilities.Capability.pickaxe;

public class TetrapsiModuleProvider extends ModuleDataProvider implements ITetrapsiDataProvider {

    public static final ResourceLocation PSI_EQIPMENT = psi("equipment");
    public static final ResourceLocation PSI_REGEN = psi("regen");
    public static final ResourceLocation PSI_TOOL = psi("tool");

    public TetrapsiModuleProvider(DataGenerator gen) {
        super(gen);
    }

    private static final IKey<BuilderTemplate<ModuleVariantData>> VARIANT_TEMPLATE_KEY =
            IKey.ofUnchecked(BuilderTemplate.class, "VARIANT_KEY");
    private static final IKey<Pattern> MODULE_PATTERN_KEY = IKey.of(Pattern.class, "MODULE_PATTERN");

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

    private static final IKey<Function<Pattern, Function<EnumDataBuilder<CapabilityData, Capability>, EnumDataBuilder<CapabilityData, Capability>>>> CAPABILITIES =
            IKey.ofUnchecked(Function.class, "CAPABILITIES");
    private static final IKey<Function<Pattern, Function<EnumDataBuilder<EffectData, ItemEffect>, EnumDataBuilder<EffectData, ItemEffect>>>> EFFECTS =
            IKey.ofUnchecked(Function.class, "EFFECTS");

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
                                .durability(p.get(TOOL_MAX_USES))
                                .capabilities(module.get(CAPABILITIES)
                                        .apply(p)
                                        .apply(EnumDataBuilder.create(CapabilityData.class))
                                        .build())
                                .effects(module.get(EFFECTS)
                                        .apply(p)
                                        .apply(EnumDataBuilder.create(EffectData.class))
                                        .build());
                    });

    @Override
    protected void collectData(@Nonnull DataConsumer<ModuleData> consumer) throws IOException {
        consumer.accept(tetra(join("double", "basic_pickaxe")),
                MODULE_TEMPLATE.apply(Pattern.create()
                        .property(VARIANT_TEMPLATE_KEY, VARIANT_BASE.copy()
                                .handle(p -> b -> b.attackSpeed(p.get(TOOL_EFFICIENCY)))
                                .build(p -> ModuleVariantBuilder.create(),
                                        p -> ModuleVariantBuilder::build))
                        .property(CAPABILITIES, p -> b -> b
                                .cap(pickaxe, p.get(TOOL_HARVEST_LEVEL), p.get(TOOL_EFFICIENCY)))
                        .property(EFFECTS, p -> b -> b)
                        .property(MODULE_TYPE_KEY, "basic_pickaxe")
                        .property(GLYPH_X, 176)
                        .property(GLYPH_Y, 0)
                        .property(MODEL, tetra("items/module/double/head/basic_pickaxe/metal"))));
        consumer.accept(tetra(join("double", "basic_hammer")),
                MODULE_TEMPLATE.apply(Pattern.create()
                        .property(VARIANT_TEMPLATE_KEY, VARIANT_BASE.copy()
                                .handle(p -> b -> b.attackSpeed(p.get(TOOL_EFFICIENCY) * 0.7F))
                                .build(p -> ModuleVariantBuilder.create(),
                                        p -> ModuleVariantBuilder::build))
                        .property(CAPABILITIES, p -> b -> b
                                .cap(hammer, p.get(TOOL_HARVEST_LEVEL), p.get(TOOL_EFFICIENCY) * 0.7F))
                        .property(EFFECTS, p -> b -> b)
                        .property(MODULE_TYPE_KEY, "basic_hammer")
                        .property(GLYPH_X, 64)
                        .property(GLYPH_Y, 0)
                        .property(MODEL, tetra("items/module/double/head/basic_hammer/metal"))));
    }

}
