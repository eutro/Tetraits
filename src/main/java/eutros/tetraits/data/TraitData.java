package eutros.tetraits.data;

import com.google.common.collect.Sets;
import eutros.tetraits.Tetraits;
import eutros.tetraits.network.IntersectTraitsPacket;
import eutros.tetraits.network.PacketHandler;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.FilenameUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TraitData extends WatchableData {

    private final Map<ResourceLocation, String> traitMap = new HashMap<>();
    public final Map<ResourceLocation, String> traits = new HashMap<>();

    public TraitData() {
        onPreLoad(traitMap::clear);
    }

    public void sync(ServerPlayerEntity player) {
        PacketHandler.sendToPlayer(player, new IntersectTraitsPacket(traitMap.keySet()));
    }

    protected void pre(IResourceManager rm) {
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
        Sets.intersection(traitMap.keySet(), filter).parallelStream()
                .forEach(rl -> traits.put(rl, traitMap.get(rl)));
    }

}
