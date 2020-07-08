package eutros.tetraits.data;

import com.google.common.collect.ImmutableList;
import eutros.tetraits.network.CustomPacket;
import net.minecraft.client.resources.ReloadListener;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class DataManager extends ReloadListener<Object> {

    private static final ThreadLocal<DataManager> instance = ThreadLocal.withInitial(() ->
            new DataManager(new TraitData(), new ModuleExt(), new CapData()));

    public TraitData traitData;
    public CapData capData;
    public ModuleExt moduleExt;

    private final ImmutableList<WatchableData> data;

    public static void init() {
        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOW, DataManager::serverStarting);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOW, DataManager::playerConnected);
    }

    protected DataManager(TraitData traitData, ModuleExt moduleExt, CapData capData) {
        this.traitData = traitData;
        this.moduleExt = moduleExt;
        this.capData = capData;
        data = ImmutableList.of(traitData, moduleExt, capData);
        data.forEach(d -> d.onPostLoad(d::syncAll));
    }

    public static DataManager getInstance() {
        return instance.get();
    }

    public static void serverStarting(FMLServerAboutToStartEvent event) {
        MinecraftServer server = event.getServer();
        server.getResourceManager().addReloadListener(getInstance());
        getInstance().data.forEach(d -> d.server = server);
    }

    public static void playerConnected(PlayerEvent.PlayerLoggedInEvent event) {
        ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
        getInstance().data.forEach(d -> d.sync(player));
    }

    @Nonnull
    @Override
    public Object prepare(@Nonnull IResourceManager rm, @Nullable IProfiler profilerIn) {
        CustomPacket.clearSchemes();
        data.forEach(d -> d.doPre(rm));
        return this;
    }

    @Override
    public void apply(@Nullable Object obj, @Nonnull IResourceManager rm, @Nullable IProfiler profilerIn) {
        data.forEach(d -> d.doApply(rm));
    }

}
