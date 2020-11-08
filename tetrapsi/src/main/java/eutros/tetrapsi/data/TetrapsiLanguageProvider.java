package eutros.tetrapsi.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import eutros.tetraits.data.gen.template.Pattern;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static eutros.tetrapsi.Tetrapsi.MOD_ID;

public class TetrapsiLanguageProvider extends LanguageProvider implements ITetrapsiDataProvider {

    public static final Gson GSON = new GsonBuilder().create();
    private Map<String, String> existing = new HashMap<>();

    public TetrapsiLanguageProvider(DataGenerator gen, String locale) {
        super(gen, MOD_ID, locale);

        try {
            Type type = getClass()
                    .getDeclaredField("existing")
                    .getGenericType();
            Enumeration<URL> enumeration = getClass()
                    .getClassLoader()
                    .getResources("assets/tetrapsi/lang_base/" + locale + ".json");
            while(enumeration.hasMoreElements()) {
                try(Reader rd = new InputStreamReader(enumeration.nextElement().openStream())) {
                    this.existing.putAll(GSON.fromJson(rd, type));
                }
            }
        } catch(IOException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addAll(DataGenerator gen) {
        String[] locales = {"en_us"};
        for(String locale : locales) {
            gen.addProvider(create(gen, locale));
        }
    }

    private static TetrapsiLanguageProvider create(DataGenerator gen, String locale) {
        return new TetrapsiLanguageProvider(gen, locale);
    }

    private Optional<String> localize(String key, Object... format) {
        return existing.containsKey(key) ?
               Optional.of(String.format(existing.get(key), format)) :
               Optional.empty();
    }

    @Override
    protected void addTranslations() {
        Pattern[] metals = {PSIMETAL, EBONY, IVORY};
        for(Pattern module : modules) {
            for(Pattern metal : metals) {
                getTranslation(module, metal).ifPresent(v ->
                        add("tetra.variant." +
                                        ITetrapsiDataProvider.join(module.get(MODULE_KEY), metal.get(NAME)),
                                v));
                getPrefix(metal).ifPresent(v ->
                        add("tetra.variant."
                                        + ITetrapsiDataProvider.join(module.get(MODULE_KEY), metal.get(NAME))
                                        + ".prefix",
                                v));
            }
        }
    }

    @Nonnull
    protected Optional<String> getPrefix(Pattern metal) {
        return localize("tetrapsi.prefix." + metal.get(NAME));
    }

    @Nonnull
    protected Optional<String> getTranslation(Pattern module, Pattern metal) {
        return getPrefix(metal).flatMap(prefix -> localize("tetrapsi.module." + module.get(MODULE_KEY), prefix));
    }

}
