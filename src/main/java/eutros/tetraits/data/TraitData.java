package eutros.tetraits.data;

public class TraitData extends ClojureData {

    public static TraitData getInstance() {
        return DataManager.getInstance().traitData;
    }

    @Override
    public String getPath() {
        return "traits";
    }

}
