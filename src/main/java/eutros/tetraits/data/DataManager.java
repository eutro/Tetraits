package eutros.tetraits.data;

import com.google.common.collect.ImmutableList;
import eutros.tetraits.network.CustomPacket;
import net.minecraft.client.resources.ReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class DataManager extends ReloadListener<Object> {

    private static final ThreadLocal<DataManager> instance = ThreadLocal.withInitial(DataManager::new);
    public static DataManager getInstance() {
        return instance.get();
    }

    public static void init() {
        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOW, (FMLServerAboutToStartEvent event) -> {
            MinecraftServer server = event.getServer();
            server.getResourceManager().addReloadListener(getInstance());
            getInstance().data.forEach(d -> d.server = server);
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
        customPacketHandler.clearSchemes();
        data.forEach(ClojureData::load);
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

}
