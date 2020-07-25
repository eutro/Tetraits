package eutros.tetraits;

import clojure.java.api.Clojure;
import eutros.tetraits.command.CommandHandler;
import eutros.tetraits.command.ReloadCommand;
import eutros.tetraits.data.DataManager;
import eutros.tetraits.handler.CapabilityHandler;
import eutros.tetraits.handler.TraitHandler;
import eutros.tetraits.network.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.io.InputStreamReader;

@Mod(Tetraits.MOD_ID)
public class Tetraits {

    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "tetraits";

    public Tetraits() {
        DataManager.init();
        PacketHandler.init();
        TraitHandler.init();
        CapabilityHandler.init();
        CommandHandler.init();

        loadClojure();

        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener((FMLClientSetupEvent event) -> {
                    Minecraft.getInstance().enqueue(() -> {
                                DataManager dm = DataManager.getInstance();
                                MinecraftForge.EVENT_BUS.addListener((ClientPlayerNetworkEvent.LoggedInEvent evt) ->
                                        new Thread(dm::load).start());
                            }
                    );
                    MinecraftForge.EVENT_BUS.addListener(ReloadCommand::clientSend);
                }
        );
    }

    private void loadClojure() {
        InputStream resource = getClass()
                .getClassLoader()
                .getResourceAsStream("clojure/tetraits.clj");

        if(resource != null) {
            try {
                Clojure.var("clojure.core", "load-reader")
                        .invoke(new InputStreamReader(resource));
            } catch(RuntimeException e) {
                LOGGER.error("Tetraits clojure init failed to load.", e);
            }
        }
    }

}
