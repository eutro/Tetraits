package eutros.tetraits.data;

import net.minecraft.client.resources.ReloadListener;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;

import javax.annotation.Nonnull;

public class DataManager extends ReloadListener<Object> {

    private static final DataManager instance = new DataManager();

    public TraitData traitData = new TraitData();
    public ModuleExt moduleExt = new ModuleExt();

    public static void init() {
    }

    private DataManager() {
        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOW, this::serverStarting);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOW, this::playerConnected);
    }

    public static DataManager getInstance() {
        return instance;
    }

    public void serverStarting(FMLServerAboutToStartEvent event) {
        event.getServer().getResourceManager().addReloadListener(this);
    }

    public void playerConnected(PlayerEvent.PlayerLoggedInEvent event) {
        traitData.sync(((ServerPlayerEntity) event.getPlayer()));
    }

    @Nonnull
    @Override
    protected Object prepare(@Nonnull IResourceManager rm, @Nonnull IProfiler profilerIn) {
        traitData.reset();
        moduleExt.reset();
        return this;
    }

    @Override
    protected void apply(@Nonnull Object obj, @Nonnull IResourceManager rm, @Nonnull IProfiler profilerIn) {
        traitData.load(rm);
        moduleExt.load(rm);
    }

}
