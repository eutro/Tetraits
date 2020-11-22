package eutros.tetrapsi.data;

import eutros.tetraits.data.gen.ModuleDataProvider;
import eutros.tetraits.data.gen.template.BuilderTemplate;
import eutros.tetraits.data.gen.template.Pattern;
import eutros.tetraits.data.gen.template.Pattern.IKey;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import se.mickelus.tetra.capabilities.Capability;
import se.mickelus.tetra.module.ItemEffect;
import se.mickelus.tetra.module.data.*;

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

    private static final IKey<Pattern> MODULE_PATTERN = IKey.of(Pattern.class, "MODULE_PATTERN");

    private static BuilderTemplate<ModuleVariantData> VARIANT_TEMPLATE =
            BuilderTemplate.TemplateBuilder.<ModuleVariantBuilder>create()
                    .handle(p -> b -> {
                        Pattern module = p.get(MODULE_PATTERN);
                        EnumDataBuilder<CapabilityData, Capability> cedb = EnumDataBuilder.create(CapabilityData.class);
                        Capability[] caps = module.get(CAPS);
                        ValFunc[] valFuncs = module.get(CAP_VAL_FUNCS);
                        EffFunc[] effFuncs = module.get(CAP_EFF_FUNCS);
                        for(int i = 0; i < caps.length; i++) {
                            if (effFuncs[i] == null) cedb.val(caps[i], valFuncs[i].val(p));
                            else cedb.cap(caps[i], valFuncs[i].val(p), effFuncs[i].eff(p));
                        }
                        EnumDataBuilder<EffectData, ItemEffect> eedb = EnumDataBuilder.create(EffectData.class);
                        ItemEffect[] effects = module.get(EFFS);
                        valFuncs = module.get(EFF_VAL_FUNCS);
                        effFuncs = module.get(EFF_EFF_FUNCS);
                        for(int i = 0; i < effects.length; i++) {
                            if (effFuncs[i] == null) eedb.val(effects[i], valFuncs[i].val(p));
                            else eedb.cap(effects[i], valFuncs[i].val(p), effFuncs[i].eff(p));
                        }
                        return b.key(join(module.get(MODULE_KEY), p.get(NAME)))
                                .glyph(p.get(TINT), module.get(GLYPH_X), module.get(GLYPH_Y))
                                .model(new ModuleModel("item",
                                        tetra(join("items/module", module.get(MODULE_TYPE_KEY), "psionic")),
                                        p.get(TINT)))
                                .integrity(Math.round(p.get(TOOL_HARVEST_LEVEL) * module.get(INTEGRITY_MULTIPLIER)))
                                .trait(PSI_EQIPMENT, true)
                                .trait(PSI_REGEN, true)
                                .trait(PSI_TOOL, true)
                                .capability(PSI_EQIPMENT, p.get(CAD_SOCKETS) / 2)
                                .magicCapacity(p.get(TOOL_ENCHANTABILITY))
                                .durability((int) (p.get(TOOL_MAX_USES) * module.get(DURABILITY_MULTIPLIER) + module.get(DURABILITY_OFFSET)))
                                .attackSpeed(p.get(TOOL_EFFICIENCY) * module.get(SPEED_MULTIPLIER) + module.get(SPEED_OFFSET))
                                .damage(p.get(TOOL_ATTACK_DAMAGE) * module.get(DAMAGE_MULTIPLIER) + module.get(DAMAGE_OFFSET))
                                .capabilities(cedb.build())
                                .effects(eedb.build());
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
        for(Pattern module : modules) {
            consumer.accept(tetra(module.get(MODULE_TYPE_KEY)), MODULE_TEMPLATE.apply(module));
        }
    }

}
