package eutros.tetraits.network;

import TetraitsAPI.IPacketScheme;
import eutros.tetraits.Tetraits;
import eutros.tetraits.data.DataManager;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class CustomPacket {

    public static class Handler {

        public final Map<String, IPacketScheme> schemeMap = new HashMap<>();

        public final void clearSchemes() {
            schemeMap.clear();
        }

    }

    private final String schemeKey;
    private final IPacketScheme scheme;
    private final Object data;

    private static Map<String, IPacketScheme> getSchemeMap() {
        return DataManager.getInstance().customPacketHandler.schemeMap;
    }

    public static void putScheme(String key, IPacketScheme scheme) {
        if(getSchemeMap().containsKey(key)) {
            throw new IllegalStateException(String.format("Key: \"%s\" registered more than once.", key));
        }
        getSchemeMap().put(key, scheme);
    }

    public CustomPacket(String schemeKey, IPacketScheme scheme, Object data) {
        this.schemeKey = schemeKey;
        this.scheme = scheme;
        this.data = data;
    }

    public static Optional<CustomPacket> create(String scheme, Object data) {
        Optional<CustomPacket> ret = Optional.ofNullable(getSchemeMap().get(scheme))
                .map(sc -> new CustomPacket(scheme, sc, data));

        if(!ret.isPresent()) {
            Tetraits.LOGGER.error("Couldn't create packet, no scheme: \"{}\".", scheme);
        }
        return ret;
    }

    public void encode(PacketBuffer buf) {
        buf.writeString(schemeKey);
        try {
            scheme.encode(data, buf);
        } catch(Exception e) {
            removeScheme("encode", schemeKey, e);
        }
    }

    public static CustomPacket decode(PacketBuffer buf) {
        String schemeKey = buf.readString();
        IPacketScheme scheme = getSchemeMap().get(schemeKey);
        Object data;
        if(scheme == null) {
            Tetraits.LOGGER.error("Received packet of unregistered scheme: \"{}\".", schemeKey);
            return null;
        } else {
            try {
                data = scheme.decode(buf);
            } catch(Exception e) {
                removeScheme("decode", schemeKey, e);
                return null;
            }
        }
        return new CustomPacket(schemeKey, scheme, data);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        try {
            scheme.handle(data, ctx);
        } catch(Exception e) {
            removeScheme("handle", schemeKey, e);
            return;
        }
        ctx.setPacketHandled(true);
    }

    private static void removeScheme(String failure, String schemeKey, Exception e) {
        Tetraits.LOGGER.fatal(String.format("Scheme: \"%s\" failed to %s data. Removing scheme.", schemeKey, failure), e);
        getSchemeMap().remove(schemeKey);
    }

}
