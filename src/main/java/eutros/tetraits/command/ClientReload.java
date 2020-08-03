package eutros.tetraits.command;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import eutros.tetraits.data.DataManager;

import java.util.function.Predicate;
import java.util.regex.Pattern;

public class ClientReload {
    
    public static final Predicate<String> RELOAD_CLIENT = Pattern.compile("/tetraits reload (client|both)").asPredicate();

    public static void clientSend(ClientChatEvent evt) {
        if(!RELOAD_CLIENT.test(evt.getMessage())) {
            return;
        }

        ClientPlayerEntity player = Minecraft.getInstance().player;
        if(player == null) {
            DataManager.getInstance().load();
            return;
        }

        player.sendMessage(new TranslationTextComponent("tetraits.commands.reload.start.client")
                .applyTextStyle(TextFormatting.BLUE));
        DataManager dm = DataManager.getInstance();
        dm.LOGGER.record();
        dm.load();
        dm.LOGGER.forEach(player::sendMessage);
        player.sendMessage(new TranslationTextComponent("tetraits.commands.reload.finished")
                .applyTextStyle(TextFormatting.GREEN));
    }
}
