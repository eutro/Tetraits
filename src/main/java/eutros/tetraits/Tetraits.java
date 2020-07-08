package eutros.tetraits;

import eutros.tetraits.data.DataManager;
import eutros.tetraits.handler.ActionHandler;
import eutros.tetraits.handler.TraitHandler;
import eutros.tetraits.network.PacketHandler;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Tetraits.MOD_ID)
public class Tetraits {

    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "tetraits";

    public Tetraits() {
        DataManager.init();
        PacketHandler.init();
        ActionHandler.init();
        TraitHandler.init();
        MinecraftForge.EVENT_BUS.addListener((FMLClientSetupEvent evt) -> ((IReloadableResourceManager) evt.getMinecraftSupplier().get()
                .getResourceManager())
                .addReloadListener(DataManager.getInstance()));
    }

}
