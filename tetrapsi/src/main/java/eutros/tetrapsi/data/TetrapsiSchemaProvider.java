package eutros.tetrapsi.data;

import eutros.tetraits.data.gen.SchemaDataProvider;
import eutros.tetraits.data.gen.template.BuilderTemplate;
import eutros.tetraits.data.gen.template.Pattern;
import eutros.tetraits.data.gen.template.Pattern.IKey;
import net.minecraft.data.DataGenerator;
import se.mickelus.tetra.capabilities.Capability;
import se.mickelus.tetra.module.data.CapabilityData;
import se.mickelus.tetra.module.schema.OutcomeDefinition;
import se.mickelus.tetra.module.schema.SchemaDefinition;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.function.Function;

import static eutros.tetrapsi.data.ITetrapsiDataProvider.*;

public class TetrapsiSchemaProvider extends SchemaDataProvider implements ITetrapsiDataProvider {

    public TetrapsiSchemaProvider(DataGenerator generator) {
        super(generator);
    }

    private static final IKey<Function<Pattern, Function<EnumDataBuilder<CapabilityData, Capability>, EnumDataBuilder<CapabilityData, Capability>>>> REQUIRED_CAPABILITIES =
            IKey.ofUnchecked(Function.class, "REQUIRED_CAPABILITIES");
    private static final IKey<Integer> MATERIAL_COUNT = IKey.of(Integer.class, "MATERIAL_COUNT");
    private static final IKey<Pattern> SCHEMA_PATTERN = IKey.of(Pattern.class, "SCHEMA_PATTERN");

    private static final IKey<String> MODULE_KEY = IKey.of(String.class, "MODULE_KEY");

    private static final BuilderTemplate<OutcomeDefinition> OUTCOME_TEMPLATE =
            BuilderTemplate.TemplateBuilder.<OutcomeBuilder>create()
                    .handle(p -> b -> {
                        Pattern schema = p.get(SCHEMA_PATTERN);
                        return b
                                .requiredCapabilities(schema.get(REQUIRED_CAPABILITIES)
                                        .apply(p)
                                        .apply(EnumDataBuilder.create(CapabilityData.class))
                                        .build())
                                .material(p.get(PREDICATE), schema.get(MATERIAL_COUNT))
                                .moduleVariant(join(schema.get(MODULE_TYPE_KEY), p.get(NAME)))
                                .moduleKey(schema.get(MODULE_KEY));
                    })
                    .starter(p -> OutcomeBuilder.create())
                    .build(OutcomeBuilder::build);

    private static final BuilderTemplate<SchemaDefinition> SCHEMA_TEMPLATE =
            BuilderTemplate.TemplateBuilder.<SchemaBuilder>create()
                    .handle(p -> b -> b
                            .outcome(OUTCOME_TEMPLATE.apply(PSIMETAL.copy().property(SCHEMA_PATTERN, p)))
                            .outcome(OUTCOME_TEMPLATE.apply(EBONY.copy().property(SCHEMA_PATTERN, p)))
                            .outcome(OUTCOME_TEMPLATE.apply(IVORY.copy().property(SCHEMA_PATTERN, p))))
                    .starter(p -> SchemaBuilder.create())
                    .build(SchemaBuilder::build);

    @Override
    protected void collectData(@Nonnull DataConsumer<SchemaDefinition> consumer) throws IOException {
        consumer.accept(tetra(join("double", "basic_pickaxe")),
                SCHEMA_TEMPLATE.apply(Pattern.create()
                        .property(MODULE_KEY, join("double", "basic_pickaxe"))
                        .property(MODULE_TYPE_KEY, "basic_pickaxe")
                        .property(MATERIAL_COUNT, 2)
                        .property(REQUIRED_CAPABILITIES, p -> b -> b
                                .val(Capability.hammer, p.get(TOOL_HARVEST_LEVEL) - 1))));
        consumer.accept(tetra(join("double", "basic_hammer")),
                SCHEMA_TEMPLATE.apply(Pattern.create()
                        .property(MODULE_KEY, join("double", "basic_hammer"))
                        .property(MODULE_TYPE_KEY, "basic_hammer")
                        .property(MATERIAL_COUNT, 2)
                        .property(REQUIRED_CAPABILITIES, p -> b -> b
                                .val(Capability.hammer, p.get(TOOL_HARVEST_LEVEL) - 1))));
    }

}
