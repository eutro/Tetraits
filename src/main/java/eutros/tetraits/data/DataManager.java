package eutros.tetraits.data;

import com.google.common.collect.ImmutableList;
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

public class DataManager extends ReloadListener<Object> {

    private static final DataManager instance = new DataManager(new TraitData(), new ModuleExt());

    public TraitData traitData;
    public ModuleExt moduleExt;

    private final ImmutableList<WatchableData> data;

    public static void init() {
        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOW, instance::serverStarting);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOW, instance::playerConnected);
    }

    protected DataManager(TraitData traitData, ModuleExt moduleExt) {
        this.traitData = traitData;
        this.moduleExt = moduleExt;
        data = ImmutableList.of(traitData, moduleExt);
        data.forEach(d -> d.onPostLoad(d::syncAll));
    }

    public static DataManager getInstance() {
        return instance;
    }

    public void serverStarting(FMLServerAboutToStartEvent event) {
        MinecraftServer server = event.getServer();
        server.getResourceManager().addReloadListener(this);
        data.forEach(d -> d.server = server);
    }

    public void playerConnected(PlayerEvent.PlayerLoggedInEvent event) {
        ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
        data.forEach(d -> d.sync(player));
    }

    @Nonnull
    @Override
    protected Object prepare(@Nonnull IResourceManager rm, @Nonnull IProfiler profilerIn) {
        data.forEach(d -> d.doPre(rm));
        return this;
    }

    @Override
    protected void apply(@Nonnull Object obj, @Nonnull IResourceManager rm, @Nonnull IProfiler profilerIn) {
        data.forEach(d -> d.doApply(rm));
    }

}
