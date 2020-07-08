package eutros.tetraits.network;

import eutros.tetraits.Tetraits;
import eutros.tetraits.data.DataManager;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class PacketHandler {

    private static final String PROTOCOL = "0.0.1";
    public static SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Tetraits.MOD_ID, "chan"),
            () -> PROTOCOL,
            PROTOCOL::equals,
            PROTOCOL::equals
    );

    public static void init() {
        int index = 0;
        CHANNEL.registerMessage(index++, IntersectTraitsPacket.class, IntersectTraitsPacket::encode, IntersectTraitsPacket::decode, IntersectTraitsPacket::handle);
        CHANNEL.registerMessage(index++, UpdateModuleExtPacket.class, UpdateModuleExtPacket::encode, UpdateModuleExtPacket::decode, UpdateModuleExtPacket::handle);
        CHANNEL.registerMessage(index, CustomPacket.class, CustomPacket::encode, CustomPacket::decode, CustomPacket::handle);

        DataManager.getInstance().traitData.onPreLoad(CustomPacket::clearSchemes);
    }

    public static void sendToPlayer(ServerPlayerEntity player, Object packet) {
        CHANNEL.sendTo(packet, player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
    }

}
