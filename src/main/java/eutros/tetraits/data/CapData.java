package eutros.tetraits.data;

public class CapData extends ClojureData {

    public static CapData getInstance() {
        return DataManager.getInstance().capData;
    }

    @Override
    public String getPath() {
        return "capabilities";
    }

}
