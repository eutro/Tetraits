package eutros.tetraits.data;

import clojure.lang.IFn;
import eutros.tetraits.Tetraits;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class TraitData extends ClojureData {

    public final Map<ResourceLocation, IFn> traitMap = new HashMap<>();

    public TraitData() {
        onPreLoad(traitMap::clear);
    }

    @Override
    protected String getPath() {
        return "tetra_traits";
    }

    @Override
    protected void store(ResourceLocation location, Object loaded) {
        if(!(loaded instanceof IFn)) {
            Tetraits.LOGGER.error(String.format("\"%s\" does not evaluate to IFn", location));
            return;
        }

        Tetraits.LOGGER.debug("Loaded function: \"{}\".", location);
        traitMap.put(location, (IFn) loaded);
    }

}
