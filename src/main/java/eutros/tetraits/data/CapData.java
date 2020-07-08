package eutros.tetraits.data;

import clojure.lang.IFn;
import eutros.tetraits.Tetraits;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class CapData extends ClojureData {

    public final Map<ResourceLocation, IFn> capMap = new HashMap<>();

    public CapData() {
        onPreLoad(capMap::clear);
    }

    @Override
    protected String getPath() {
        return "tetra_caps";
    }

    @Override
    protected void store(ResourceLocation location, Object loaded) {
        if(!(loaded instanceof IFn)) {
            Tetraits.LOGGER.error(String.format("\"%s\" does not evaluate to IFn", location));
            return;
        }

        Tetraits.LOGGER.debug("Loaded cap function: \"{}\".", location);
        capMap.put(location, (IFn) loaded);
    }

}
