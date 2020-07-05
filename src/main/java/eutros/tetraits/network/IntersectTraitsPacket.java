package eutros.tetraits.network;

import eutros.tetraits.data.DataManager;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Only sync names, not functionality.
 */
public class IntersectTraitsPacket {

    private final Set<ResourceLocation> data;

    public IntersectTraitsPacket(Set<ResourceLocation> data) {
        this.data = data;
    }

    public void encode(PacketBuffer buf) {
        buf.writeVarInt(data.size());
        data.forEach(buf::writeResourceLocation);
    }

    @Nonnull
    public static IntersectTraitsPacket decode(PacketBuffer buf) {
        int size = buf.readVarInt();
        HashSet<ResourceLocation> data = new HashSet<>(size);
        for(int i = 0; i < size; i++) {
            data.add(buf.readResourceLocation());
        }
        return new IntersectTraitsPacket(data);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        if(ctx.getDirection() != NetworkDirection.PLAY_TO_CLIENT) {
            ctx.setPacketHandled(true);
            return;
        }

        DataManager.getInstance().traitData.intersect(data);
        ctx.setPacketHandled(true);
    }

}
