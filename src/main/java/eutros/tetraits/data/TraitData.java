package eutros.tetraits.data;

import com.google.common.collect.Sets;
import eutros.tetraits.Tetraits;
import eutros.tetraits.handler.ActionHandler;
import eutros.tetraits.network.PacketHandler;
import eutros.tetraits.network.IntersectTraitsPacket;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import org.apache.commons.io.FilenameUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TraitData {

    private final Map<ResourceLocation, String> traitMap = new HashMap<>();
    public final Map<ResourceLocation, String> traits = new HashMap<>();

    public void sync(ServerPlayerEntity player) {
        PacketHandler.CHANNEL.sendTo(new IntersectTraitsPacket(traitMap.keySet()),
                player.connection.netManager,
                NetworkDirection.PLAY_TO_CLIENT);
    }

    public void reset() {
        traitMap.clear();
    }

    public void load(IResourceManager rm) {
        String path = "tetra_traits";
        for(ResourceLocation rl : rm.getAllResourceLocations(path, s -> FilenameUtils.getExtension(s).equals("clj"))) {
            IResource resource;
            try {
                resource = rm.getResource(rl);
            } catch(IOException e) {
                Tetraits.LOGGER.error(String.format("Couldn't load resource: %s.", rl), e);
                continue;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));

            traitMap.put(new ResourceLocation(rl.getNamespace(), FilenameUtils.removeExtension(rl.getPath()).substring(path.length() + 1)),
                    reader.lines()
                            .map(s -> s + "\n")
                            .reduce("", String::concat));
        }
        intersect(traitMap.keySet());
    }

    public void intersect(Set<ResourceLocation> filter) {
        traits.clear();
        ActionHandler.instance.clear();
        Sets.intersection(traitMap.keySet(), filter).parallelStream()
                .forEach(rl -> traits.put(rl, traitMap.get(rl)));
    }

}
