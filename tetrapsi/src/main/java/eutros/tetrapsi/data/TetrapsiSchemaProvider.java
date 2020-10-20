package eutros.tetrapsi.data;

import eutros.tetraits.data.gen.SchemaDataProvider;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.data.DataGenerator;
import se.mickelus.tetra.capabilities.Capability;
import se.mickelus.tetra.module.data.CapabilityData;
import se.mickelus.tetra.module.schema.SchemaDefinition;
import vazkii.psi.common.lib.ModTags;

import javax.annotation.Nonnull;
import java.io.IOException;

import static eutros.tetrapsi.data.ITetrapsiDataProvider.join;
import static eutros.tetrapsi.data.ITetrapsiDataProvider.tetra;

public class TetrapsiSchemaProvider extends SchemaDataProvider implements ITetrapsiDataProvider {

    public TetrapsiSchemaProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void collectData(@Nonnull DataConsumer<SchemaDefinition> consumer) throws IOException {
        consumer.accept(tetra(join("double", "basic_pickaxe")),
                SchemaBuilder.create()
                        .outcome(OutcomeBuilder.create()
                                .material(ItemPredicate.Builder.create()
                                                .tag(ModTags.INGOT_PSIMETAL)
                                                .build(),
                                        1)
                                .requiredCapabilities(EnumDataBuilder.create(CapabilityData.class)
                                        .val(Capability.hammer, 2)
                                        .build())
                                .moduleKey("double/basic_pickaxe")
                                .moduleVariant("basic_pickaxe/psimetal")
                                .build())
                        .outcome(OutcomeBuilder.create()
                                .material(ItemPredicate.Builder.create()
                                                .tag(ModTags.INGOT_EBONY_PSIMETAL)
                                                .build(),
                                        1)
                                .requiredCapabilities(EnumDataBuilder.create(CapabilityData.class)
                                        .val(Capability.hammer, 3)
                                        .build())
                                .moduleKey("double/basic_pickaxe")
                                .moduleVariant("basic_pickaxe/ebony_psimetal")
                                .build())
                        .outcome(OutcomeBuilder.create()
                                .material(ItemPredicate.Builder.create()
                                                .tag(ModTags.INGOT_IVORY_PSIMETAL)
                                                .build(),
                                        1)
                                .requiredCapabilities(EnumDataBuilder.create(CapabilityData.class)
                                        .val(Capability.hammer, 3)
                                        .build())
                                .moduleKey("double/basic_pickaxe")
                                .moduleVariant("basic_pickaxe/ivory_psimetal")
                                .build())
                        .build());
    }

}
