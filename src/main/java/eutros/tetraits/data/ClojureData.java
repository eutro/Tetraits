package eutros.tetraits.data;

import clojure.lang.Compiler;
import eutros.tetraits.Tetraits;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.io.InputStreamReader;

public abstract class ClojureData extends WatchableData {

    protected abstract String getPath();

    protected abstract void store(ResourceLocation location, Object loaded);

    @Override
    protected void pre(IResourceManager rm) {
        String path = getPath();
        for(ResourceLocation rl : rm.getAllResourceLocations(path, s -> FilenameUtils.getExtension(s).equals("clj"))) {
            IResource resource;
            try {
                resource = rm.getResource(rl);
            } catch(IOException e) {
                Tetraits.LOGGER.error(String.format("Couldn't load resource: %s.", rl), e);
                continue;
            }

            ResourceLocation location = new ResourceLocation(
                    rl.getNamespace(),
                    FilenameUtils.removeExtension(rl.getPath())
                            .substring(path.length() + 1)
            );

            getExecutor().runImmediately(() -> {
                try {
                    Object loaded = Compiler.load(new InputStreamReader(resource.getInputStream()), null, rl.toString());
                    store(location, loaded);
                } catch(Throwable t) {
                    Tetraits.LOGGER.error("Couldn't compile \"{}\": {}\n{}", rl, t.getMessage(), t.getCause().getMessage());
                }
            });
        }
    }

}
