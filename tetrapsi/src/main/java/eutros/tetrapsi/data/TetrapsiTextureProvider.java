package eutros.tetrapsi.data;

import com.google.gson.stream.JsonReader;
import eutros.tetraits.data.gen.template.Pattern;
import eutros.tetrapsi.Tetrapsi;
import eutros.tetrapsi.data.image.Pixel;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.world.gen.PerlinNoiseGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.mickelus.tetra.data.DataManager;
import se.mickelus.tetra.module.data.ModuleData;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.Iterator;

import static eutros.tetrapsi.data.ITetrapsiDataProvider.join;
import static eutros.tetrapsi.data.ITetrapsiDataProvider.tetra;

public class TetrapsiTextureProvider implements ITetrapsiDataProvider {

    public static final Logger LOGGER = LogManager.getLogger();
    private final DataGenerator gen;

    public TetrapsiTextureProvider(DataGenerator gen) {
        this.gen = gen;
    }

    private Iterable<InputStream> findResource(ResourceLocation path, ResourcePackType type, String suffix, String prefix) throws IOException {
        Enumeration<URL> resources = getClass().getClassLoader()
                .getResources(join(
                        type.getDirectoryName(),
                        path.getNamespace(),
                        prefix,
                        path.getPath() + suffix
                ));
        return () -> new Iterator<InputStream>() {
            @Override
            public boolean hasNext() {
                return resources.hasMoreElements();
            }

            @Override
            public InputStream next() {
                try {
                    return resources.nextElement().openStream();
                } catch(IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    @Override
    public void act(@Nonnull DirectoryCache cache) throws IOException {
        for(Pattern module : modules) {
            ModuleData moduleData = new ModuleData();
            for(InputStream inputStream : findResource(
                    tetra(module.get(MODULE_TYPE_KEY)),
                    ResourcePackType.SERVER_DATA,
                    ".json",
                    "modules"
            )) {
                try(final JsonReader reader =
                            new JsonReader(new InputStreamReader(inputStream))) {
                    ModuleData.copyFields(DataManager.gson.fromJson(reader, ModuleData.class), moduleData);
                } catch(Exception e) {
                    LOGGER.catching(e);
                }
            }
            String[] suffixes = moduleData.slotSuffixes;
            if(suffixes.length == 0) suffixes = new String[] {""};
            for(String suffix : suffixes) {
                ResourceLocation model = module.get(MODEL);
                for(InputStream in : findResource(model, ResourcePackType.CLIENT_RESOURCES, suffix + ".png", "textures")) {
                    try(InputStream ignored = in;
                        NativeImage image = NativeImage.read(in);
                        NativeImage transformed = transform(image)) {
                        Path path = gen.getOutputFolder()
                                .resolve("assets")
                                .resolve("tetra") // yes this is intentional, multi-slot modules force this namespace
                                .resolve("textures")
                                .resolve("items")
                                .resolve("module")
                                .resolve(module.get(MODULE_TYPE_KEY))
                                .resolve("psionic" + suffix + ".png");
                        cache.addProtectedPath(path);
                        Files.createDirectories(path.getParent());
                        transformed.write(path);
                    }
                }
            }
        }
    }

    private static final Pixel GRAD_BEG = Pixel.of(0x83, 0xad, 0xda, 0xff).withS(0);
    private static final Pixel GRAD_END = Pixel.of(0x6e, 0x55, 0xb9, 0xff).withS(0);

    private static final PerlinNoiseGenerator NOISE =
            new PerlinNoiseGenerator(new SharedSeedRandom(Tetrapsi.MOD_ID.hashCode()), 3, 0);

    @Nonnull
    private NativeImage transform(NativeImage image) {
        for(int x = 0; x < image.getWidth(); x++) {
            for(int y = 0; y < image.getHeight(); y++) {
                Pixel pix = Pixel.from(image, x, y);
                if(pix.ad() > 0) {
                    if(pix.v() < 0.3) {
                        Pixel.lerp(GRAD_BEG, GRAD_END, Math.floorDiv(x + y, 16) / 16.0)
                                .set(image, x, y);
                    } else {
                        Pixel.lerp(GRAD_BEG, GRAD_END, NOISE.noiseAt(x, y, false))
                                .set(image, x, y);
                    }
                }
            }
        }
        return image;
    }

    @Nonnull
    @Override
    public String getName() {
        return "Tetrapsi Textures";
    }

}
