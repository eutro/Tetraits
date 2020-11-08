package eutros.tetrapsi.data;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

public class TetrapsiDataGen {

    public static void onGatherData(GatherDataEvent evt) {
        DataGenerator gen = evt.getGenerator();
        gen.addProvider(new TetrapsiModuleProvider(gen));
        gen.addProvider(new TetrapsiSchemaProvider(gen));
        if (evt.includeClient()) TetrapsiLanguageProvider.addAll(gen);
    }

}
