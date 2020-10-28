package eutros.tetraits.data.gen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.util.ResourceLocation;
import se.mickelus.tetra.module.data.EnumTierData;

import javax.annotation.Nullable;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public abstract class TetraDataProvider<T> implements IDataProvider {

    private final DataGenerator generator;

    protected final Gson gson = addToBuilder(new GsonBuilder())
            .registerTypeAdapterFactory(new TypeAdapterFactory() {
                @Override
                @Nullable
                @SuppressWarnings("unchecked")
                public <C> TypeAdapter<C> create(Gson gson, TypeToken<C> type) {
                    if(!EnumTierData.class.isAssignableFrom(type.getRawType())) return null;
                    return (TypeAdapter<C>) new TypeAdapter<EnumTierData<?>>() {
                        @Override
                        public void write(JsonWriter out, EnumTierData<?> src) throws IOException {
                            out.beginObject();
                            for(Enum<?> e : src.valueMap.keySet()) {
                                out.name(e.name().toLowerCase());
                                if(src.efficiencyMap.containsKey(e)) {
                                    out.beginArray();
                                    out.value(src.valueMap.get(e));
                                    out.value(src.efficiencyMap.get(e));
                                    out.endArray();
                                } else {
                                    out.value(src.valueMap.get(e));
                                }
                            }
                            out.endObject();
                        }

                        @Override
                        public EnumTierData<?> read(JsonReader in) {
                            throw new UnsupportedOperationException();
                        }
                    };
                }
            })
            .setPrettyPrinting()
            .create();

    protected GsonBuilder addToBuilder(GsonBuilder gsonBuilder) {
        return gsonBuilder;
    }

    public TetraDataProvider(DataGenerator generator) {
        this.generator = generator;
    }

    @Override
    public void act(DirectoryCache cache) throws IOException {
        collectData((loc, t) -> {
            Path outputPath = generator.getOutputFolder()
                    .resolve(loc.getNamespace())
                    .resolve(getPath())
                    .resolve(loc.getPath() + ".json");
            cache.addProtectedPath(outputPath);
            outputPath.getParent().toFile().mkdirs();
            try(final FileWriter fr = new FileWriter(outputPath.toFile());
                final BufferedWriter br = new BufferedWriter(fr);
                final JsonWriter jr = gson.newJsonWriter(br)) {
                gson.toJson(t, t.getClass(), jr);
            }
        });
    }

    abstract protected String getPath();

    abstract protected void collectData(DataConsumer<T> consumer) throws IOException;

    public interface DataConsumer<T> {

        void accept(ResourceLocation loc, T t) throws IOException;

    }

    @Override
    public String getName() {
        return "Tetra Data: " + getPath();
    }

    protected interface IBuilder<T> {

        T build();

    }

    protected static class EnumDataBuilder<T extends EnumTierData<E>, E extends Enum<E>> implements IBuilder<T> {

        private final T obj;

        protected EnumDataBuilder(T obj) {
            this.obj = obj;
        }

        public static <T extends EnumTierData<E>, E extends Enum<E>> EnumDataBuilder<T, E> create(Class<T> clazz) {
            try {
                return new EnumDataBuilder<>(clazz.newInstance());
            } catch(InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        public EnumDataBuilder<T, E> cap(E key, int value, float efficiency) {
            obj.valueMap.put(key, value);
            obj.efficiencyMap.put(key, efficiency);
            return this;
        }

        public EnumDataBuilder<T, E> val(E key, int value) {
            obj.valueMap.put(key, value);
            return this;
        }

        @Override
        public T build() {
            return obj;
        }

    }

}
