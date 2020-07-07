package eutros.tetraits.network;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import eutros.tetraits.data.DataManager;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class UpdateModuleExtPacket {

    private final Multimap<Pair<String, String>, ResourceLocation> data;

    public UpdateModuleExtPacket(Multimap<Pair<String, String>, ResourceLocation> map) {
        this.data = map;
    }

    public void encode(PacketBuffer buf) {
        buf.writeVarInt(data.keySet().size());
        data.asMap().forEach((key, values) -> {
            buf.writeString(key.getLeft());
            buf.writeString(key.getRight());
            buf.writeVarInt(values.size());
            values.forEach(buf::writeResourceLocation);
        });
    }

    @Nonnull
    public static UpdateModuleExtPacket decode(PacketBuffer buf) {
        int size = buf.readVarInt();
        Multimap<Pair<String, String>, ResourceLocation> data = HashMultimap.create(size, 1);
        for(int i = 0; i < size; i++) {
            Pair<String, String> key = Pair.of(buf.readString(), buf.readString());
            int valCount = buf.readVarInt();
            for(int j = 0; j < valCount; j++) {
                data.put(key, buf.readResourceLocation());
            }
        }
        return new UpdateModuleExtPacket(data);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        if(ctx.getDirection() != NetworkDirection.PLAY_TO_CLIENT) {
            ctx.setPacketHandled(true);
            return;
        }

        DataManager.getInstance().moduleExt.setMap(data);
        ctx.setPacketHandled(true);
    }

}
