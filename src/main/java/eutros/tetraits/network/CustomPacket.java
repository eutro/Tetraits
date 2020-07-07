package eutros.tetraits.network;

import TetraitsAPI.IPacketScheme;
import eutros.tetraits.Tetraits;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class CustomPacket {

    private static final ThreadLocal<Map<String, IPacketScheme>> schemeMap = ThreadLocal.withInitial(HashMap::new);
    private final String schemeKey;
    private final IPacketScheme scheme;
    private final Object data;

    private static Map<String, IPacketScheme> getSchemeMap() {
        return schemeMap.get();
    }

    public static void clearSchemes() {
        getSchemeMap().clear();
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
        } catch(Throwable t) {
            removeScheme("encode", schemeKey, t);
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
            } catch(Throwable t) {
                removeScheme("decode", schemeKey, t);
                return null;
            }
        }
        return new CustomPacket(schemeKey, scheme, data);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        try {
            scheme.handle(data, ctx);
        } catch(Throwable t) {
            removeScheme("handle", schemeKey, t);
            return;
        }
        ctx.setPacketHandled(true);
    }

    private static void removeScheme(String failure, String schemeKey, Throwable t) {
        Tetraits.LOGGER.fatal(String.format("Scheme: \"%s\" failed to %s data. Removing scheme.", schemeKey, failure), t);
        getSchemeMap().remove(schemeKey);
    }

}
