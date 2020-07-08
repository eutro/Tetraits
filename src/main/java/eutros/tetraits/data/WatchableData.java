package eutros.tetraits.data;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.resources.IResourceManager;
import net.minecraft.server.MinecraftServer;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public abstract class WatchableData {

    private List<Runnable> preloads = new ArrayList<>();

    public void onPreLoad(Runnable preload) {
        preloads.add(preload);
    }

    public void preload() {
        preloads.forEach(Runnable::run);
    }

    public final void doPre(IResourceManager rm) {
        preload();
        pre(rm);
    }

    protected void pre(IResourceManager rm) {
    }

    private List<Runnable> postLoads = new ArrayList<>();

    public void onPostLoad(Runnable postLoad) {
        postLoads.add(postLoad);
    }

    public void postLoad() {
        postLoads.forEach(Runnable::run);
    }

    public final void doApply(IResourceManager rm) {
        apply(rm);
        postLoad();
    }

    protected void apply(IResourceManager rm) {
    }

    protected abstract void sync(ServerPlayerEntity player);

    @Nullable
    public MinecraftServer server;

    public void syncAll() {
        if(server == null) return;
        server.getPlayerList()
                .getPlayers()
                .forEach(this::sync);
    }

}
