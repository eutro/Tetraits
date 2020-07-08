package eutros.tetraits;

import eutros.tetraits.data.ClientDataManager;
import eutros.tetraits.data.DataManager;
import eutros.tetraits.handler.CapabilityHandler;
import eutros.tetraits.handler.TraitHandler;
import eutros.tetraits.network.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.SimpleReloadableResourceManager;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Tetraits.MOD_ID)
public class Tetraits {

    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "tetraits";

    public Tetraits() {
        DataManager.init();
        PacketHandler.init();
        TraitHandler.init();
        CapabilityHandler.init();
        if(FMLEnvironment.dist.isClient()) {
            Minecraft mc = Minecraft.getInstance();
            mc.enqueue(() -> {
                DataManager dm = DataManager.getInstance();
                ClientDataManager rm = new ClientDataManager((SimpleReloadableResourceManager) mc.getResourceManager());
                MinecraftForge.EVENT_BUS.addListener((ClientPlayerNetworkEvent.LoggedInEvent evt) -> new Thread(() -> {
                            dm.prepare(rm, null);
                            dm.apply(null, rm, null);
                        }).start()
                );
            });
        }
    }

}
