package eutros.tetraits.data;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.resources.*;
import net.minecraft.util.ResourceLocation;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ClientDataManager extends SimpleReloadableResourceManager {

    private final SimpleReloadableResourceManager rm;

    public ClientDataManager(SimpleReloadableResourceManager rm) {
        super(ResourcePackType.SERVER_DATA, Thread.currentThread());
        this.rm = rm;
    }

    @Override
    public Collection<ResourceLocation> getAllResourceLocations(String pathIn, Predicate<String> filter) {
        Set<ResourceLocation> set = Sets.newHashSet();

        for(FallbackResourceManager frm : rm.namespaceResourceManagers.values()) {
            List<ResourceLocation> list = Lists.newArrayList();

            for(IResourcePack rp : frm.resourcePacks) {
               list.addAll(rp.getAllResourceLocations(ResourcePackType.SERVER_DATA, frm.namespace, pathIn, Integer.MAX_VALUE, filter));
            }

            Collections.sort(list);
            set.addAll(list);
        }

        List<ResourceLocation> list = Lists.newArrayList(set);
        Collections.sort(list);
        return list;
    }

    @Override
    public IResource getResource(ResourceLocation rlIn) throws IOException {
        FallbackResourceManager frm = rm.namespaceResourceManagers.get(rlIn.getNamespace());
        if (frm != null) {
            frm.checkResourcePath(rlIn);
            IResourcePack rp = null;
            ResourceLocation mcMetaLoc = new ResourceLocation(rlIn.getNamespace(), rlIn.getPath() + ".mcmeta");

            for(int i = frm.resourcePacks.size() - 1; i >= 0; --i) {
               IResourcePack orp = frm.resourcePacks.get(i);
               if (rp == null && orp.resourceExists(ResourcePackType.SERVER_DATA, mcMetaLoc)) {
                  rp = orp;
               }

               if (orp.resourceExists(ResourcePackType.SERVER_DATA, rlIn)) {
                  InputStream is = null;
                  if (rp != null) {
                      is = rp.getResourceStream(ResourcePackType.SERVER_DATA, mcMetaLoc);
                  }

                   InputStream inputstream = orp.getResourceStream(ResourcePackType.SERVER_DATA, rlIn);
                   return new SimpleResource(orp.getName(), rlIn, inputstream, is);
               }
            }
        }
        throw new FileNotFoundException(rlIn.toString());
    }

}
