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

public class ActionHandler {

    private ActionHandler() {
    }

    public static final ActionHandler instance = new ActionHandler();

    public static void init() {
        DataManager.getInstance().traitData.onPostLoad(instance::refresh);
    }

    private Map<ResourceLocation, IFn> actions = new HashMap<>();

    @Nullable
    public IFn getAction(ResourceLocation location) {
        return actions.get(location);
    }

    public void remove(ResourceLocation location) {
        actions.remove(location);
    }

    private void makeAction(ResourceLocation loc, String code) {
        Object load;
        try {
            load = Compiler.load(new StringReader(code), null, loc.toString());
        } catch(Throwable t) {
            Tetraits.LOGGER.error("Couldn't compile \"{}\": {}", loc, t.getMessage());
            return;
        }
        if(!(load instanceof IFn)) {
            Tetraits.LOGGER.error(String.format("\"%s\" does not evaluate to IFn", loc));
            return;
        }

        Tetraits.LOGGER.info("Loaded function: {}.", loc.toString());
        actions.put(loc, (IFn) load);
    }

    public void refresh() {
        actions.clear();
        DataManager.getInstance().traitData.traits.forEach(this::makeAction);
    }

}
