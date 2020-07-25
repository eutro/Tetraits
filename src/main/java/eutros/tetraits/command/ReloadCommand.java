package eutros.tetraits.command;

import com.mojang.brigadier.CommandDispatcher;
import eutros.tetraits.data.DataManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.command.CommandSource;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.event.ClientChatEvent;

import static net.minecraft.command.Commands.literal;

public class ReloadCommand {

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(literal("tetraits")
                .then(literal("reload")
                        .then(literal("server")
                                .requires(source -> source.hasPermissionLevel(2))
                                .executes(context -> {
                                    context.getSource()
                                            .sendFeedback(
                                                    new TranslationTextComponent("tetraits.commands.reload.start"),
                                                    true
                                            );
                                    DataManager.getInstance().load();
                                    context.getSource()
                                            .sendFeedback(
                                                    new TranslationTextComponent("tetraits.commands.reload.finished")
                                                            .applyTextStyle(TextFormatting.GREEN),
                                                    true
                                            );
                                    return 0;
                                }))
                        .then(literal("client").executes(context -> 0))));
    }

    public static void clientSend(ClientChatEvent evt) {
        if(!"/tetraits reload client".equals(evt.getMessage())) {
            return;
        }

        ClientPlayerEntity player = Minecraft.getInstance().player;
        if(player == null) {
            DataManager.getInstance().load();
            return;
        }

        player.sendMessage(new TranslationTextComponent("tetraits.commands.reload.start"));
        DataManager.getInstance().load();
        player.sendMessage(new TranslationTextComponent("tetraits.commands.reload.finished")
                .applyTextStyle(TextFormatting.GREEN));
    }

}
