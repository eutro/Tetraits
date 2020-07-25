package eutros.tetraits.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;

public class CommandHandler {

    public static void init() {
        MinecraftForge.EVENT_BUS.addListener((FMLServerAboutToStartEvent evt) -> {
            CommandDispatcher<CommandSource> dispatcher = evt.getServer()
                    .getCommandManager()
                    .getDispatcher();
            ReloadCommand.register(dispatcher);
        });
    }

}
