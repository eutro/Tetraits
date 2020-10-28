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
import static se.mickelus.tetra.capabilities.Capability.*;

public class TetrapsiModuleProvider extends ModuleDataProvider implements ITetrapsiDataProvider {

    public static final ResourceLocation PSI_EQIPMENT = psi("equipment");
    public static final ResourceLocation PSI_REGEN = psi("regen");
    public static final ResourceLocation PSI_TOOL = psi("tool");

    public TetrapsiModuleProvider(DataGenerator gen) {
        super(gen);
    }

    private static final IKey<Pattern> MODULE_PATTERN = IKey.of(Pattern.class, "MODULE_PATTERN");

    private static final IKey<Integer> GLYPH_X = IKey.of(Integer.class, "GLYPH_X");
    private static final IKey<Integer> GLYPH_Y = IKey.of(Integer.class, "GLYPH_Y");
    private static final IKey<ResourceLocation> MODEL = IKey.of(ResourceLocation.class, "MODEL");

    private static final IKey<Function<Pattern, Function<EnumDataBuilder<CapabilityData, Capability>, EnumDataBuilder<CapabilityData, Capability>>>> CAPABILITIES =
            IKey.ofUnchecked(Function.class, "CAPABILITIES");
    private static final IKey<Function<Pattern, Function<EnumDataBuilder<EffectData, ItemEffect>, EnumDataBuilder<EffectData, ItemEffect>>>> EFFECTS =
            IKey.ofUnchecked(Function.class, "EFFECTS");

    private static final IKey<Function<Pattern, Float>> ATTACK_SPEED = IKey.ofUnchecked(Function.class, "ATTACK_SPEED");
    private static final IKey<Function<Pattern, Float>> ATTACK_DAMAGE = IKey.ofUnchecked(Function.class, "ATTACK_DAMAGE");
    private static final IKey<Float> DURABILITY_MULTIPLIER = IKey.of(Float.class, "ATTACK_DAMAGE");

    private static BuilderTemplate<ModuleVariantData> VARIANT_TEMPLATE =
            BuilderTemplate.TemplateBuilder.<ModuleVariantBuilder>create()
                    .handle(p -> b -> {
                        Pattern module = p.get(MODULE_PATTERN);
                        return b.key(join(module.get(MODULE_TYPE_KEY), p.get(NAME)))
                                .glyph(p.get(TINT), module.get(GLYPH_X), module.get(GLYPH_Y))
                                .model(new ModuleModel("item", module.get(MODEL), p.get(TINT)))
                                .integrity(-p.get(TOOL_HARVEST_LEVEL))
                                .trait(PSI_EQIPMENT, true)
                                .trait(PSI_REGEN, true)
                                .trait(PSI_TOOL, true)
                                .capability(PSI_EQIPMENT, p.get(CAD_SOCKETS) / 2)
                                .magicCapacity(p.get(TOOL_ENCHANTABILITY))
                                .durability((int) (p.get(TOOL_MAX_USES) * module.get(DURABILITY_MULTIPLIER)))
                                .attackSpeed(module.get(ATTACK_SPEED).apply(p))
                                .damage(module.get(ATTACK_DAMAGE).apply(p))
                                .capabilities(module.get(CAPABILITIES)
                                        .apply(p)
                                        .apply(EnumDataBuilder.create(CapabilityData.class))
                                        .build())
                                .effects(module.get(EFFECTS)
                                        .apply(p)
                                        .apply(EnumDataBuilder.create(EffectData.class))
                                        .build());
                    })
                    .starter(p -> ModuleVariantBuilder.create())
                    .build(ModuleVariantBuilder::build);

    private static BuilderTemplate<ModuleData> MODULE_TEMPLATE =
            BuilderTemplate.TemplateBuilder.<ModuleBuilder>create()
                    .handle(p -> b -> b
                            .variant(VARIANT_TEMPLATE.apply(PSIMETAL.copy().property(MODULE_PATTERN, p)))
                            .variant(VARIANT_TEMPLATE.apply(EBONY.copy().property(MODULE_PATTERN, p)))
                            .variant(VARIANT_TEMPLATE.apply(IVORY.copy().property(MODULE_PATTERN, p))))
                    .starter(p -> ModuleBuilder.create())
                    .build(ModuleBuilder::build);

    @Override
    protected void collectData(@Nonnull DataConsumer<ModuleData> consumer) throws IOException {
        consumer.accept(tetra(join("double", "adze")),
                MODULE_TEMPLATE.apply(Pattern.create()
                        .property(CAPABILITIES, p -> b -> b
                                .cap(axe, p.get(TOOL_HARVEST_LEVEL), p.get(TOOL_EFFICIENCY) * 0.6F)
                                .cap(shovel, p.get(TOOL_HARVEST_LEVEL), p.get(TOOL_EFFICIENCY) * 0.6F))
                        .property(EFFECTS, p -> b -> b)

                        .property(ATTACK_SPEED, p -> p.get(TOOL_EFFICIENCY) - 9)
                        .property(ATTACK_DAMAGE, p -> p.get(TOOL_ATTACK_DAMAGE) - 1)
                        .property(DURABILITY_MULTIPLIER, 0.95F)

                        .property(MODULE_TYPE_KEY, "adze")
                        .property(GLYPH_X, 32)
                        .property(GLYPH_Y, 0)
                        .property(MODEL, tetra("items/module/double/head/adze/metal"))));

        consumer.accept(tetra(join("double", "basic_pickaxe")),
                MODULE_TEMPLATE.apply(Pattern.create()
                        .property(CAPABILITIES, p -> b -> b
                                .cap(pickaxe, p.get(TOOL_HARVEST_LEVEL), p.get(TOOL_EFFICIENCY)))
                        .property(EFFECTS, p -> b -> b)

                        .property(ATTACK_SPEED, p -> (p.get(TOOL_EFFICIENCY) - 9) / 2)
                        .property(ATTACK_DAMAGE, p -> p.get(TOOL_ATTACK_DAMAGE))
                        .property(DURABILITY_MULTIPLIER, 1F)

                        .property(MODULE_TYPE_KEY, "basic_pickaxe")
                        .property(GLYPH_X, 176)
                        .property(GLYPH_Y, 0)
                        .property(MODEL, tetra("items/module/double/head/basic_pickaxe/metal"))));

        consumer.accept(tetra(join("double", "basic_hammer")),
                MODULE_TEMPLATE.apply(Pattern.create()
                        .property(CAPABILITIES, p -> b -> b
                                .cap(hammer, p.get(TOOL_HARVEST_LEVEL), p.get(TOOL_EFFICIENCY) * 0.7F))
                        .property(EFFECTS, p -> b -> b)

                        .property(ATTACK_SPEED, p -> p.get(TOOL_EFFICIENCY) - 9)
                        .property(ATTACK_DAMAGE, p -> p.get(TOOL_ATTACK_DAMAGE) + 1)
                        .property(DURABILITY_MULTIPLIER, 1F)

                        .property(MODULE_TYPE_KEY, "basic_hammer")
                        .property(GLYPH_X, 64)
                        .property(GLYPH_Y, 0)
                        .property(MODEL, tetra("items/module/double/head/basic_hammer/metal"))));
    }

}
