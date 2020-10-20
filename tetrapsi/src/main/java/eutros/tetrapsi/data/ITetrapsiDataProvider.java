package eutros.tetrapsi.data;

import net.minecraft.util.ResourceLocation;

public interface ITetrapsiDataProvider {

    static String join(String a, String b) {
        return a + "/" + b;
    }
    static ResourceLocation tetra(String path) {
        return new ResourceLocation("tetra", path);
    }
    static ResourceLocation psi(String path) {
        return new ResourceLocation("psi", path);
    }

}
