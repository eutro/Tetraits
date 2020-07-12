package eutros.tetraits.util;

import clojure.lang.IFn;
import eutros.tetraits.data.ClojureData;
import eutros.tetraits.handler.ASMFieldHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import se.mickelus.tetra.items.modular.IItemModular;
import se.mickelus.tetra.module.data.ModuleVariantData;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Stream;

public class TetraHelper {

    @Nullable
    public static Stream<ModuleVariantData> getRelevantVariantData(ItemStack stack) {
        if(!(stack.getItem() instanceof IItemModular)) {
            return null;
        }

        IItemModular im = (IItemModular) stack.getItem();
        return Stream.concat(
                Arrays.stream(im.getMajorModules(stack)),
                Arrays.stream(im.getMinorModules(stack))
        )
                .filter(Objects::nonNull)
                .map(module -> module.getVariantData(stack));
    }

    public static <T> Optional<T> forAllFrom(ItemStack stack,
                                             ClojureData store,
                                             CljActionFunc<T> action) {
        Stream<ModuleVariantData> stream = getRelevantVariantData(stack);
        if(stream == null) {
            return Optional.empty();
        }

        Set<IFn> invoked = new HashSet<>();

        //noinspection unchecked
        return stream.map(ASMFieldHandler::getTetraitsField)
                .filter(Objects::nonNull)
                .map(tetraits -> tetraits.get(store.getPath()))
                .filter(Map.class::isInstance)
                .map(obj -> (Map<Object, Object>) obj)
                .map(Map::entrySet)
                .flatMap(Collection::stream)
                .map((entry) -> {
                    ResourceLocation rl = new ResourceLocation(entry.getKey().toString());
                    IFn fn = store.get(rl);
                    if(fn == null || invoked.contains(fn)) {
                        return null;
                    }

                    invoked.add(fn);
                    return action.apply(rl, fn, entry.getValue());
                })
                .filter(Objects::nonNull)
                .findFirst();
    }

    @FunctionalInterface
    public interface CljActionFunc<T> {

        T apply(ResourceLocation rl, IFn fn, Object data);

    }

}
