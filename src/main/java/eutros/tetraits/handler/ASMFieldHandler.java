package eutros.tetraits.handler;

import eutros.tetraits.Tetraits;
import org.apache.commons.lang3.reflect.FieldUtils;
import se.mickelus.tetra.module.data.ModuleVariantData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.Map;

public class ASMFieldHandler {

    @Nullable
    public static Map<String, Object> getTetraitsField(@Nonnull ModuleVariantData data) {
        return getReflected(data); // Replaced with ASM.
    }

    // fallback reflection stuff

    private static boolean logged = false;
    private static Field field = FieldUtils.getField(ModuleVariantData.class, "tetraits", false);

    @SuppressWarnings("unchecked")
    @Nullable
    private static Map<String, Object> getReflected(@Nonnull ModuleVariantData data) {
        if(field != null) {
            try {
                return (Map<String, Object>) field.get(data);
            } catch(IllegalAccessException | ClassCastException ignored) {
            }
        }
        if(!logged) {
            Tetraits.LOGGER.fatal("Couldn't access tetraits field, even through reflection. Something went terribly wrong! The mod will not work properly.");
            logged = true;
        }
        return null;
    }

}
