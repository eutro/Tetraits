package eutros.tetraits.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import eutros.tetraits.data.DataManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.command.CommandSource;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.event.ClientChatEvent;

import java.util.function.Predicate;
import java.util.regex.Pattern;

import static net.minecraft.command.Commands.literal;

public class ReloadCommand {

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(literal("tetraits")
                .then(literal("reload")
                        .then(literal("server")
                                .requires(source -> source.hasPermissionLevel(2))
                                .executes(ReloadCommand::reloadServer))
                        .then(literal("both")
                                .requires(source -> source.hasPermissionLevel(2))
                                .executes(ReloadCommand::reloadServer))
                        .then(literal("client").executes(context -> 0))));
    }

    private static int reloadServer(CommandContext<CommandSource> context) {
        context.getSource()
                .sendFeedback(new TranslationTextComponent("tetraits.commands.reload.start.server")
                        .applyTextStyle(TextFormatting.LIGHT_PURPLE), true);
        DataManager dm = DataManager.getInstance();
        dm.LOGGER.record();
        dm.load();
        dm.LOGGER.forEach(component -> context.getSource().sendFeedback(component, false));
        context.getSource()
                .sendFeedback(new TranslationTextComponent("tetraits.commands.reload.finished")
                        .applyTextStyle(TextFormatting.GREEN), true);
        return 0;
    }

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
