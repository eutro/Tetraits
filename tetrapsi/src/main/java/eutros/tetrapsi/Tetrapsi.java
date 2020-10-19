package eutros.tetrapsi;

import eutros.tetrapsi.data.TetrapsiDataGen;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Tetrapsi.MOD_ID)
public class Tetrapsi {

    public static final String MOD_ID = "tetrapsi";

    public Tetrapsi() {
        FMLJavaModLoadingContext.get()
                .getModEventBus()
                .addListener(TetrapsiDataGen::onGatherData);
    }

}
