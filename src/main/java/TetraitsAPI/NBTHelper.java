package TetraitsAPI;

import net.minecraft.nbt.*;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nullable;
import java.io.DataOutput;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@SuppressWarnings("unused")
public class NBTHelper {

    public static void write(CompoundNBT cBT, DataOutput output) throws IOException {
        cBT.write(output);
    }

    public static Set<String> keySet(CompoundNBT cBT) {
        return cBT.keySet();
    }

    public static byte getId(CompoundNBT cBT) {
        return cBT.getId();
    }

    public static INBTType<CompoundNBT> getType(CompoundNBT cBT) {
        return cBT.getType();
    }

    public static int size(CompoundNBT cBT) {
        return cBT.size();
    }

    @Nullable
    public static INBT put(CompoundNBT cBT, String key, INBT value) {
        return cBT.put(key, value);
    }

    public static void putByte(CompoundNBT cBT, String key, byte value) {
        cBT.putByte(key, value);
    }

    public static void putShort(CompoundNBT cBT, String key, short value) {
        cBT.putShort(key, value);
    }

    public static void putInt(CompoundNBT cBT, String key, int value) {
        cBT.putInt(key, value);
    }

    public static void putLong(CompoundNBT cBT, String key, long value) {
        cBT.putLong(key, value);
    }

    public static void putUniqueId(CompoundNBT cBT, String key, UUID value) {
        cBT.putUniqueId(key, value);
    }

    public static UUID getUniqueId(CompoundNBT cBT, String key) {
        return cBT.getUniqueId(key);
    }

    public static boolean hasUniqueId(CompoundNBT cBT, String key) {
        return cBT.hasUniqueId(key);
    }

    public static void removeUniqueId(CompoundNBT cBT, String key) {
        cBT.removeUniqueId(key);
    }

    public static void putFloat(CompoundNBT cBT, String key, float value) {
        cBT.putFloat(key, value);
    }

    public static void putDouble(CompoundNBT cBT, String key, double value) {
        cBT.putDouble(key, value);
    }

    public static void putString(CompoundNBT cBT, String key, String value) {
        cBT.putString(key, value);
    }

    public static void putByteArray(CompoundNBT cBT, String key, byte[] value) {
        cBT.putByteArray(key, value);
    }

    public static void putIntArray(CompoundNBT cBT, String key, int[] value) {
        cBT.putIntArray(key, value);
    }

    public static void putIntArray(CompoundNBT cBT, String key, List<Integer> value) {
        cBT.putIntArray(key, value);
    }

    public static void putLongArray(CompoundNBT cBT, String key, long[] value) {
        cBT.putLongArray(key, value);
    }

    public static void putLongArray(CompoundNBT cBT, String key, List<Long> value) {
        cBT.putLongArray(key, value);
    }

    public static void putBoolean(CompoundNBT cBT, String key, boolean value) {
        cBT.putBoolean(key, value);
    }

    @Nullable
    public static INBT get(CompoundNBT cBT, String key) {
        return cBT.get(key);
    }

    public static byte getTagId(CompoundNBT cBT, String key) {
        return cBT.getTagId(key);
    }

    public static boolean contains(CompoundNBT cBT, String key) {
        return cBT.contains(key);
    }

    public static boolean contains(CompoundNBT cBT, String key, int type) {
        return cBT.contains(key, type);
    }

    public static byte getByte(CompoundNBT cBT, String key) {
        return cBT.getByte(key);
    }

    public static short getShort(CompoundNBT cBT, String key) {
        return cBT.getShort(key);
    }

    public static int getInt(CompoundNBT cBT, String key) {
        return cBT.getInt(key);
    }

    public static long getLong(CompoundNBT cBT, String key) {
        return cBT.getLong(key);
    }

    public static float getFloat(CompoundNBT cBT, String key) {
        return cBT.getFloat(key);
    }

    public static double getDouble(CompoundNBT cBT, String key) {
        return cBT.getDouble(key);
    }

    public static String getString(CompoundNBT cBT, String key) {
        return cBT.getString(key);
    }

    public static byte[] getByteArray(CompoundNBT cBT, String key) {
        return cBT.getByteArray(key);
    }

    public static int[] getIntArray(CompoundNBT cBT, String key) {
        return cBT.getIntArray(key);
    }

    public static long[] getLongArray(CompoundNBT cBT, String key) {
        return cBT.getLongArray(key);
    }

    public static CompoundNBT getCompound(CompoundNBT cBT, String key) {
        return cBT.getCompound(key);
    }

    public static ListNBT getList(CompoundNBT cBT, String key, int type) {
        return cBT.getList(key, type);
    }

    public static boolean getBoolean(CompoundNBT cBT, String key) {
        return cBT.getBoolean(key);
    }

    public static void remove(CompoundNBT cBT, String key) {
        cBT.remove(key);
    }

    public static boolean isEmpty(CompoundNBT cBT) {
        return cBT.isEmpty();
    }

    public static CompoundNBT copy(CompoundNBT cBT) {
        return cBT.copy();
    }

    public static CompoundNBT merge(CompoundNBT cBT, CompoundNBT other) {
        return cBT.merge(other);
    }

    public static long getLong(NumberNBT numBT) {
        return numBT.getLong();
    }

    public static int getInt(NumberNBT numBT) {
        return numBT.getInt();
    }

    public static short getShort(NumberNBT numBT) {
        return numBT.getShort();
    }

    public static byte getByte(NumberNBT numBT) {
        return numBT.getByte();
    }

    public static double getDouble(NumberNBT numBT) {
        return numBT.getDouble();
    }

    public static float getFloat(NumberNBT numBT) {
        return numBT.getFloat();
    }

    public static Number getAsNumber(NumberNBT numBT) {
        return numBT.getAsNumber();
    }

    public static void write(INBT inbt, DataOutput output) throws IOException {
        inbt.write(output);
    }

    public static byte getId(INBT inbt) {
        return inbt.getId();
    }

    public static INBTType<?> getType(INBT inbt) {
        return inbt.getType();
    }

    public static INBT copy(INBT inbt) {
        return inbt.copy();
    }

    public static String getString(INBT inbt) {
        return inbt.getString();
    }

    public static ITextComponent toFormattedComponent(INBT inbt) {
        return inbt.toFormattedComponent();
    }

    public static ITextComponent toFormattedComponent(INBT inbt, String indentation, int indentDepth) {
        return inbt.toFormattedComponent(indentation, indentDepth);
    }

}
