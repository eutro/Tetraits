package eutros.tetraits.data;

public class TraitData extends ClojureData {

    public static TraitData getInstance() {
        return DataManager.getInstance().traitData;
    }

    @Override
    protected String getPath() {
        return "traits";
    }

}
