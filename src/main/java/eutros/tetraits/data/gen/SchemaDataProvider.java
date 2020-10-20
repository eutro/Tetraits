package eutros.tetraits.data.gen;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializer;
import com.google.gson.internal.bind.JsonTreeWriter;
import com.google.gson.reflect.TypeToken;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.data.DataGenerator;
import se.mickelus.tetra.module.data.CapabilityData;
import se.mickelus.tetra.module.data.GlyphData;
import se.mickelus.tetra.module.schema.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public abstract class SchemaDataProvider extends TetraDataProvider<SchemaDefinition> {

    @Override
    protected GsonBuilder addToBuilder(GsonBuilder gsonBuilder) {
        return super.addToBuilder(gsonBuilder)
                .registerTypeAdapter(ItemPredicate.class, (JsonSerializer<ItemPredicate>) (src, typeOfSrc, context) -> src.serialize())
                .registerTypeAdapter(SchemaDefinition.class, (JsonSerializer<SchemaDefinition>) (src, typeOfSrc, context) -> {
                    JsonTreeWriter jtr = new JsonTreeWriter();
                    try {
                        gson.getDelegateAdapter(null, TypeToken.get(SchemaDefinition.class)).write(jtr, src);
                    } catch(IOException e) {
                        throw new RuntimeException(e);
                    }
                    JsonObject obj = jtr.get().getAsJsonObject();
                    SchemaDefinition sd = new SchemaDefinition();
                    if(sd.replace == src.replace) obj.remove("replace");
                    if(Objects.equals(sd.localizationKey, src.localizationKey)) obj.remove("localizationKey");
                    if(Arrays.equals(sd.slots, src.slots)) obj.remove("slots");
                    if(Arrays.equals(sd.keySuffixes, src.keySuffixes)) obj.remove("keySuffixes");
                    if(sd.materialSlotCount == src.materialSlotCount) obj.remove("materialSlotCount");
                    if(sd.repair == src.repair) obj.remove("repair");
                    if(sd.hone == src.hone) obj.remove("hone");
                    if(Objects.equals(sd.requirement, src.requirement)) obj.remove("requirement");
                    if(sd.materialRevealSlot == src.materialRevealSlot) obj.remove("materialRevealSlot");
                    if(Objects.equals(sd.displayType, src.displayType)) obj.remove("displayType");
                    if(Objects.equals(sd.rarity, src.rarity)) obj.remove("rarity");
                    if(Objects.equals(sd.glyph, src.glyph)) obj.remove("glyph");
                    if(Arrays.equals(sd.outcomes, src.outcomes)) obj.remove("outcomes");
                    if(Objects.equals(sd.key, src.key)) obj.remove("key");
                    return obj;
                })
                .registerTypeAdapter(OutcomeDefinition.class, (JsonSerializer<OutcomeDefinition>) (src, typeOfSrc, context) -> {
                    JsonTreeWriter jtr = new JsonTreeWriter();
                    try {
                        gson.getDelegateAdapter(null, TypeToken.get(OutcomeDefinition.class)).write(jtr, src);
                    } catch(IOException e) {
                        throw new RuntimeException(e);
                    }
                    JsonObject obj = jtr.get().getAsJsonObject();
                    OutcomeDefinition od = new OutcomeDefinition();
                    if(Objects.equals(od.material, src.material)) obj.remove("material");
                    if(od.materialSlot == src.materialSlot) obj.remove("materialSlot");
                    if(od.experienceCost == src.experienceCost) obj.remove("experienceCost");
                    if(Objects.equals(od.requiredCapabilities, src.requiredCapabilities)) obj.remove("requiredCapabilities");
                    if(Objects.equals(od.moduleKey, src.moduleKey)) obj.remove("moduleKey");
                    if(Objects.equals(od.moduleVariant, src.moduleVariant)) obj.remove("moduleVariant");
                    if(Objects.equals(od.improvements, src.improvements)) obj.remove("improvements");
                    return obj;
                })
                .registerTypeAdapter(Material.class, (JsonSerializer<Material>) (src, typeOfSrc, context) -> {
                    // the reflective type adapter actually struggles on this for some reason anyway
                    JsonObject obj = context.serialize(src.predicate).getAsJsonObject();
                    if(src.count != 1) obj.addProperty("count", src.count);
                    return obj;
                });
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

        public static SchemaBuilder create() {
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

        public static OutcomeBuilder create() {
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
