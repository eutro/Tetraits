package eutros.tetraits.data;

import java.io.File;
import net.minecraft.client.Minecraft;
import net.minecraft.util.concurrent.ThreadTaskExecutor;

class ClientHelper {

    public static File gameDir() {
	return Minecraft.getInstance().gameDir;
    }

    public static ThreadTaskExecutor<? extends Runnable> getExecutor() {
	return Minecraft.getInstance();
    }
    
}
