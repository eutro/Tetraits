package eutros.tetraits.data.gen;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonSerializer;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.data.DataGenerator;
import se.mickelus.tetra.module.data.CapabilityData;
import se.mickelus.tetra.module.data.GlyphData;
import se.mickelus.tetra.module.schema.OutcomeDefinition;
import se.mickelus.tetra.module.schema.SchemaDefinition;
import se.mickelus.tetra.module.schema.SchemaRarity;
import se.mickelus.tetra.module.schema.SchemaType;

import java.util.LinkedList;
import java.util.List;

public abstract class SchemaDataProvider extends TetraDataProvider<SchemaDefinition> {

    @Override
    protected GsonBuilder addToBuilder(GsonBuilder gsonBuilder) {
        return super.addToBuilder(gsonBuilder)
                .registerTypeAdapter(ItemPredicate.class, (JsonSerializer<ItemPredicate>) (src, typeOfSrc, context) -> src.serialize());
    }

    public SchemaDataProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected String getPath() {
        return "schemas";
    }

    protected static class SchemaBuilder {

        private final SchemaDefinition schemaDefinition;

        private final List<String> slots = new LinkedList<>();
        private final List<String> keySuffixes = new LinkedList<>();
        private final List<OutcomeDefinition> outcomes = new LinkedList<>();

        protected SchemaBuilder() {
            schemaDefinition = new SchemaDefinition();
        }

        public SchemaBuilder create() {
            return new SchemaBuilder();
        }

        public SchemaBuilder replace(boolean replace) {
            schemaDefinition.replace = replace;
            return this;
        }

        public SchemaBuilder localizationKey(String localizationKey) {
            schemaDefinition.localizationKey = localizationKey;
            return this;
        }

        public SchemaBuilder slot(String slot) {
            slots.add(slot);
            return this;
        }

        public SchemaBuilder keySuffix(String keySuffix) {
            keySuffixes.add(keySuffix);
            return this;
        }

        public SchemaBuilder materialSlotCount(int materialSlotCount) {
            schemaDefinition.materialSlotCount = materialSlotCount;
            return this;
        }

        public SchemaBuilder repair(boolean repair) {
            schemaDefinition.repair = repair;
            return this;
        }

        public SchemaBuilder hone(boolean hone) {
            schemaDefinition.hone = hone;
            return this;
        }

        public SchemaBuilder requirement(ItemPredicate requirement) {
            schemaDefinition.requirement = requirement;
            return this;
        }

        public SchemaBuilder materialRevealSlot(int materialRevealSlot) {
            schemaDefinition.materialRevealSlot = materialRevealSlot;
            return this;
        }

        public SchemaBuilder displayType(SchemaType displayType) {
            schemaDefinition.displayType = displayType;
            return this;
        }

        public SchemaBuilder rarity(SchemaRarity rarity) {
            schemaDefinition.rarity = rarity;
            return this;
        }

        public SchemaBuilder glyph(GlyphData glyph) {
            schemaDefinition.glyph = glyph;
            return this;
        }

        public SchemaBuilder outcome(OutcomeDefinition outcome) {
            outcomes.add(outcome);
            return this;
        }

        public SchemaBuilder key(String key) {
            schemaDefinition.key = key;
            return this;
        }

        public SchemaDefinition build() {
            schemaDefinition.slots = slots.toArray(schemaDefinition.slots);
            schemaDefinition.keySuffixes = keySuffixes.toArray(schemaDefinition.keySuffixes);
            schemaDefinition.outcomes = outcomes.toArray(schemaDefinition.outcomes);
            return schemaDefinition;
        }

    }

    protected static class OutcomeBuilder implements IBuilder<OutcomeDefinition> {

        private final OutcomeDefinition outcomeDefinition;

        protected OutcomeBuilder() {
            outcomeDefinition = new OutcomeDefinition();
        }

        public OutcomeBuilder create() {
            return new OutcomeBuilder();
        }

        public OutcomeBuilder material(ItemPredicate predicate, int count) {
            outcomeDefinition.material.predicate = predicate;
            outcomeDefinition.material.count = count;
            return this;
        }

        public OutcomeBuilder materialSlot(int materialSlot) {
            outcomeDefinition.materialSlot = materialSlot;
            return this;
        }

        public OutcomeBuilder experienceCost(int experienceCost) {
            outcomeDefinition.experienceCost = experienceCost;
            return this;
        }

        public OutcomeBuilder requiredCapabilities(CapabilityData requiredCapabilities) {
            outcomeDefinition.requiredCapabilities = requiredCapabilities;
            return this;
        }

        public OutcomeBuilder moduleKey(String moduleKey) {
            outcomeDefinition.moduleKey = moduleKey;
            return this;
        }

        public OutcomeBuilder moduleVariant(String moduleVariant) {
            outcomeDefinition.moduleVariant = moduleVariant;
            return this;
        }

        public OutcomeBuilder improvement(String key, int val) {
            outcomeDefinition.improvements.put(key, val);
            return this;
        }

        @Override
        public OutcomeDefinition build() {
            return outcomeDefinition;
        }

    }

}
