package eutros.tetraits.data;

import com.google.common.collect.ImmutableList;
import eutros.tetraits.Tetraits;
import eutros.tetraits.network.CustomPacket;
import eutros.tetraits.util.FileHelper;
import eutros.tetraits.util.TextComponentLogger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.ReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import org.apache.logging.log4j.LogManager;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.Optional;

public class DataManager extends ReloadListener<Object> {

    public final TextComponentLogger LOGGER = new TextComponentLogger(LogManager.getLogger());

    private static final ThreadLocal<DataManager> instance = ThreadLocal.withInitial(DataManager::new);
    private MinecraftServer server;

    public static DataManager getInstance() {
        return instance.get();
    }

    public static void init() {
        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOW, (FMLServerAboutToStartEvent event) -> {
            MinecraftServer server = event.getServer();
            DataManager instance = getInstance();
            server.getResourceManager().addReloadListener(instance);
            instance.data.forEach(d -> d.server = server);
            instance.server = server;
        });
    }

    public final TraitData traitData = new TraitData();
    public final CapData capData = new CapData();

    public final CustomPacket.Handler customPacketHandler = new CustomPacket.Handler();

    private final ImmutableList<ClojureData> data;

    protected DataManager() {
        data = ImmutableList.of(traitData, capData);
    }

    public void load() {
        loadDefaults();
        customPacketHandler.clearSchemes();
        data.forEach(ClojureData::load);
    }

    private File getTetraitsDir() {
        return new File((server == null ?
                         Minecraft.getInstance().gameDir :
                         server.getDataDirectory()),
                Tetraits.MOD_ID);
    }

    @Nonnull
    @Override
    public Object prepare(@Nonnull IResourceManager rm, @Nullable IProfiler profilerIn) {
        load();
        return this;
    }

    @Override
    public void apply(@Nullable Object obj, @Nonnull IResourceManager rm, @Nullable IProfiler profilerIn) {
    }

    private void loadDefaults() {
        File dir = getTetraitsDir();
        if(dir.exists()) {
            if(dir.isDirectory()) {
                return;
            }

            try {
                Files.delete(dir.toPath());
            } catch(IOException e) {
                return;
            }
        }

        if(dir.mkdir()) {
            Optional.ofNullable(DataManager.class.getClassLoader())
                    .map(cl -> cl.getResource("builtin"))
                    .map(url -> {
                        try {
                            return url.toURI();
                        } catch(URISyntaxException e) {
                            return null;
                        }
                    })
                    .map(File::new)
                    .map(File::toPath)
                    .ifPresent(resources -> FileHelper.copyAll(resources, dir.toPath()));
        }
    }

}
