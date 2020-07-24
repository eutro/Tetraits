package eutros.tetraits.clojure_api;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public interface IPacketScheme {

    void encode(Object data, PacketBuffer buf);

    Object decode(PacketBuffer buf);

    void handle(Object data, NetworkEvent.Context ctx);

}
