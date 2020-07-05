package eutros.tetraits.handler;

import clojure.lang.Compiler;
import clojure.lang.IFn;
import eutros.tetraits.Tetraits;
import eutros.tetraits.data.DataManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ActionHandler {

    private ActionHandler() {
    }

    public static final ActionHandler instance = new ActionHandler();

    private Map<ResourceLocation, IFn> actions = new HashMap<>();

    @Nullable
    public IFn getAction(ResourceLocation location) {
        return actions.computeIfAbsent(location,
                rl -> {
                    String code = DataManager.getInstance().traitData.traits.get(rl);
                    return Optional.ofNullable(code)
                            .map(str -> {
                                try {
                                    return Compiler.load(new StringReader(str));
                                } catch(Throwable t) {
                                    Tetraits.LOGGER.error("Couldn't compile.", t);
                                }
                                return null;
                            })
                            .filter(res -> {
                                if(res instanceof IFn) return true;
                                Tetraits.LOGGER.error(String.format("\"%s\" does not evaluate to IFn", code));
                                return false;
                            })
                            .map(IFn.class::cast)
                            .orElseGet(() -> {
                                DataManager.getInstance().traitData.traits.remove(rl);
                                return null;
                            });
                });
    }

    public void clear() {
        actions.clear();
    }

}
