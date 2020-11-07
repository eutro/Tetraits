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

import static eutros.tetrapsi.data.ITetrapsiDataProvider.join;
import static eutros.tetrapsi.data.ITetrapsiDataProvider.tetra;

public class TetrapsiSchemaProvider extends SchemaDataProvider implements ITetrapsiDataProvider {

    public TetrapsiSchemaProvider(DataGenerator generator) {
        super(generator);
    }

    private static final IKey<Pattern> SCHEMA_PATTERN = IKey.of(Pattern.class, "SCHEMA_PATTERN");

    private static final BuilderTemplate<OutcomeDefinition> OUTCOME_TEMPLATE =
            BuilderTemplate.TemplateBuilder.<OutcomeBuilder>create()
                    .handle(p -> b -> {
                        Pattern schema = p.get(SCHEMA_PATTERN);
                        EnumDataBuilder<CapabilityData, Capability> edb = EnumDataBuilder.create(CapabilityData.class);
                        for(Capability capability : schema.get(REQUIRED_CAPABILITIES)) {
                            edb.val(capability, p.get(TOOL_HARVEST_LEVEL) + schema.get(CAPABILITY_OFFSET) - 1);
                        }
                        return b
                                .requiredCapabilities(edb.build())
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
        for(Pattern module : modules) {
            consumer.accept(tetra(join(module.get(MODULE_TYPE_KEY), module.get(MODULE_KEY))), SCHEMA_TEMPLATE.apply(module));
        }
    }

}
