package eutros.tetrapsi.data;

import eutros.tetrapsi.Tetrapsi;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public abstract class TetrapsiLanguageProvider extends LanguageProvider {

    public TetrapsiLanguageProvider(DataGenerator gen, String locale) {
        super(gen, Tetrapsi.MOD_ID, locale);
    }

    public static void addAll(DataGenerator gen) {
        gen.addProvider(new EnUs(gen));
    }

    private static class EnUs extends TetrapsiLanguageProvider {

        public EnUs(DataGenerator gen) {
            super(gen, "en_us");
        }

        @Override
        protected void addTranslations() {
        }

    }

}
