package eutros.tetraits.data;

public class CapData extends ClojureData {

    public static CapData getInstance() {
        return DataManager.getInstance().capData;
    }

    @Override
    protected String getPath() {
        return "capabilities";
    }

}
