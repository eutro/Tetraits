package eutros.tetraits.network;

import eutros.tetraits.Tetraits;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class PacketHandler {

    private static final String PROTOCOL = "0.0.2";
    public static SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Tetraits.MOD_ID, "chan"),
            () -> PROTOCOL,
            PROTOCOL::equals,
            PROTOCOL::equals
    );

    public static void init() {
        CHANNEL.registerMessage(0, CustomPacket.class, CustomPacket::encode, CustomPacket::decode, CustomPacket::handle);
    }

}
