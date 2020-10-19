package eutros.tetraits.handler;

import com.google.common.base.Suppliers;
import se.mickelus.tetra.module.data.ModuleVariantData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.function.Supplier;

public class ASMFieldHandler {

    @Nullable
    public static Map<String, Object> getTetraitsField(@Nonnull ModuleVariantData data) {
        return getReflected(data); // Replaced with ASM.
    }

    public static void setTetraitsField(@Nonnull ModuleVariantData data, Map<String, Object> tetraits) {
        setReflected(data, tetraits); // Not replaced with ASM.
    }

    // fallback reflection stuff

    private static <T> T throwException(Throwable t) {
        throw new IllegalStateException("Could not get Tetraits field.", t);
    }

    private static boolean logged = false;
    private static Supplier<Field> field = Suppliers.memoize(() ->
            {
                try {
                    //noinspection JavaReflectionMemberAccess
                    return ModuleVariantData.class.getField("tetraits");
                } catch(NoSuchFieldException e) {
                    return throwException(e);
                }
            }
    );
    private static Supplier<MethodHandle> getterHandle = Suppliers.memoize(() ->
            {
                try {
                    return MethodHandles.lookup().unreflectGetter(field.get());
                } catch(IllegalAccessException e) {
                    return throwException(e);
                }
            }
    );
    private static Supplier<MethodHandle> setterHandle = Suppliers.memoize(() -> {
        {
            try {
                return MethodHandles.lookup().unreflectSetter(field.get());
            } catch(IllegalAccessException e) {
                return throwException(e);
            }
        }
    });

    @SuppressWarnings("unchecked")
    @Nullable
    private static Map<String, Object> getReflected(@Nonnull ModuleVariantData data) {
        try {
            return (Map<String, Object>) getterHandle.get().invokeExact(data);
        } catch(Throwable throwable) {
            return throwException(throwable);
        }
    }

    private static void setReflected(@Nonnull ModuleVariantData data, Map<String, Object> tetraits) {
        try {
            setterHandle.get().invokeExact(data, tetraits);
        } catch(Throwable throwable) {
            throwException(throwable);
        }
    }

}
