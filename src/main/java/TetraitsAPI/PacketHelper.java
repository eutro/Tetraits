package TetraitsAPI;

import eutros.tetraits.network.CustomPacket;
import eutros.tetraits.network.PacketHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.util.ByteProcessor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.SectionPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkDirection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@SuppressWarnings("unused")
public class PacketHelper {

    public static void addScheme(String name, IPacketScheme scheme) {
        CustomPacket.putScheme(name, scheme);
    }

    public static boolean sendToServer(String scheme, Object data) {
        Optional<CustomPacket> packet = CustomPacket.create(scheme, data);
        packet.ifPresent(PacketHandler.CHANNEL::sendToServer);
        return packet.isPresent();
    }

    public static boolean sendTo(String scheme, Object data, NetworkManager manager, NetworkDirection direction) {
        Optional<CustomPacket> packet = CustomPacket.create(scheme, data);
        packet.ifPresent(message -> PacketHandler.CHANNEL.sendTo(message, manager, direction));
        return packet.isPresent();
    }

    public static int getVarIntSize(int input) {
        return PacketBuffer.getVarIntSize(input);
    }

    public static PacketBuffer writeByteArray(@Nonnull PacketBuffer buf, byte[] array) {
        return buf.writeByteArray(array);
    }

    public static byte[] readByteArray(@Nonnull PacketBuffer buf) {
        return buf.readByteArray();
    }

    public static byte[] readByteArray(@Nonnull PacketBuffer buf, int maxLength) {
        return buf.readByteArray(maxLength);
    }

    public static PacketBuffer writeVarIntArray(@Nonnull PacketBuffer buf, int[] array) {
        return buf.writeVarIntArray(array);
    }

    public static int[] readVarIntArray(@Nonnull PacketBuffer buf) {
        return buf.readVarIntArray();
    }

    public static int[] readVarIntArray(@Nonnull PacketBuffer buf, int maxLength) {
        return buf.readVarIntArray(maxLength);
    }

    public static PacketBuffer writeLongArray(@Nonnull PacketBuffer buf, long[] array) {
        return buf.writeLongArray(array);
    }

    @OnlyIn(Dist.CLIENT)
    public static long[] readLongArray(@Nonnull PacketBuffer buf, @Nullable long[] array) {
        return buf.readLongArray(array);
    }

    @OnlyIn(Dist.CLIENT)
    public static long[] readLongArray(@Nonnull PacketBuffer buf, @Nullable long[] array, int maxLength) {
        return buf.readLongArray(array, maxLength);
    }

    public static BlockPos readBlockPos(@Nonnull PacketBuffer buf) {
        return buf.readBlockPos();
    }

    public static PacketBuffer writeBlockPos(@Nonnull PacketBuffer buf, BlockPos pos) {
        return buf.writeBlockPos(pos);
    }

    @OnlyIn(Dist.CLIENT)
    public static SectionPos readSectionPos(@Nonnull PacketBuffer buf) {
        return buf.readSectionPos();
    }

    public static ITextComponent readTextComponent(@Nonnull PacketBuffer buf) {
        return buf.readTextComponent();
    }

    public static PacketBuffer writeTextComponent(@Nonnull PacketBuffer buf, ITextComponent component) {
        return buf.writeTextComponent(component);
    }

    public static <T extends Enum<T>> T readEnumValue(@Nonnull PacketBuffer buf, Class<T> enumClass) {
        return buf.readEnumValue(enumClass);
    }

    public static PacketBuffer writeEnumValue(@Nonnull PacketBuffer buf, Enum<?> value) {
        return buf.writeEnumValue(value);
    }

    public static int readVarInt(@Nonnull PacketBuffer buf) {
        return buf.readVarInt();
    }

    public static long readVarLong(@Nonnull PacketBuffer buf) {
        return buf.readVarLong();
    }

    public static PacketBuffer writeUniqueId(@Nonnull PacketBuffer buf, UUID uuid) {
        return buf.writeUniqueId(uuid);
    }

    public static UUID readUniqueId(@Nonnull PacketBuffer buf) {
        return buf.readUniqueId();
    }

    public static PacketBuffer writeVarInt(@Nonnull PacketBuffer buf, int input) {
        return buf.writeVarInt(input);
    }

    public static PacketBuffer writeVarLong(@Nonnull PacketBuffer buf, long value) {
        return buf.writeVarLong(value);
    }

    public static PacketBuffer writeCompoundTag(@Nonnull PacketBuffer buf, @Nullable CompoundNBT nbt) {
        return buf.writeCompoundTag(nbt);
    }

    @Nullable
    public static CompoundNBT readCompoundTag(@Nonnull PacketBuffer buf) {
        return buf.readCompoundTag();
    }

    public static PacketBuffer writeItemStack(@Nonnull PacketBuffer buf, ItemStack stack) {
        return buf.writeItemStack(stack);
    }

    public static PacketBuffer writeItemStack(@Nonnull PacketBuffer buf, ItemStack stack, boolean limitedTag) {
        return buf.writeItemStack(stack, limitedTag);
    }

    public static ItemStack readItemStack(@Nonnull PacketBuffer buf) {
        return buf.readItemStack();
    }

    @OnlyIn(Dist.CLIENT)
    public static String readString(@Nonnull PacketBuffer buf) {
        return buf.readString();
    }

    public static String readString(@Nonnull PacketBuffer buf, int maxLength) {
        return buf.readString(maxLength);
    }

    public static PacketBuffer writeString(@Nonnull PacketBuffer buf, String string) {
        return buf.writeString(string);
    }

    public static PacketBuffer writeString(@Nonnull PacketBuffer buf, String string, int maxLength) {
        return buf.writeString(string, maxLength);
    }

    public static ResourceLocation readResourceLocation(@Nonnull PacketBuffer buf) {
        return buf.readResourceLocation();
    }

    public static PacketBuffer writeResourceLocation(@Nonnull PacketBuffer buf, ResourceLocation resourceLocationIn) {
        return buf.writeResourceLocation(resourceLocationIn);
    }

    public static Date readTime(@Nonnull PacketBuffer buf) {
        return buf.readTime();
    }

    public static PacketBuffer writeTime(@Nonnull PacketBuffer buf, Date time) {
        return buf.writeTime(time);
    }

    public static BlockRayTraceResult readBlockRay(@Nonnull PacketBuffer buf) {
        return buf.readBlockRay();
    }

    public static void writeBlockRay(@Nonnull PacketBuffer buf, BlockRayTraceResult resultIn) {
        buf.writeBlockRay(resultIn);
    }

    public static int capacity(@Nonnull PacketBuffer buf) {
        return buf.capacity();
    }

    public static ByteBuf capacity(@Nonnull PacketBuffer buf, int p_capacity_1_) {
        return buf.capacity(p_capacity_1_);
    }

    public static int maxCapacity(@Nonnull PacketBuffer buf) {
        return buf.maxCapacity();
    }

    public static ByteBufAllocator alloc(@Nonnull PacketBuffer buf) {
        return buf.alloc();
    }

    public static ByteOrder order(@Nonnull PacketBuffer buf) {
        return buf.order();
    }

    public static ByteBuf order(@Nonnull PacketBuffer buf, ByteOrder p_order_1_) {
        return buf.order(p_order_1_);
    }

    public static ByteBuf unwrap(@Nonnull PacketBuffer buf) {
        return buf.unwrap();
    }

    public static boolean isDirect(@Nonnull PacketBuffer buf) {
        return buf.isDirect();
    }

    public static boolean isReadOnly(@Nonnull PacketBuffer buf) {
        return buf.isReadOnly();
    }

    public static ByteBuf asReadOnly(@Nonnull PacketBuffer buf) {
        return buf.asReadOnly();
    }

    public static int readerIndex(@Nonnull PacketBuffer buf) {
        return buf.readerIndex();
    }

    public static ByteBuf readerIndex(@Nonnull PacketBuffer buf, int p_readerIndex_1_) {
        return buf.readerIndex(p_readerIndex_1_);
    }

    public static int writerIndex(@Nonnull PacketBuffer buf) {
        return buf.writerIndex();
    }

    public static ByteBuf writerIndex(@Nonnull PacketBuffer buf, int p_writerIndex_1_) {
        return buf.writerIndex(p_writerIndex_1_);
    }

    public static ByteBuf setIndex(@Nonnull PacketBuffer buf, int p_setIndex_1_, int p_setIndex_2_) {
        return buf.setIndex(p_setIndex_1_, p_setIndex_2_);
    }

    public static int readableBytes(@Nonnull PacketBuffer buf) {
        return buf.readableBytes();
    }

    public static int writableBytes(@Nonnull PacketBuffer buf) {
        return buf.writableBytes();
    }

    public static int maxWritableBytes(@Nonnull PacketBuffer buf) {
        return buf.maxWritableBytes();
    }

    public static boolean isReadable(@Nonnull PacketBuffer buf) {
        return buf.isReadable();
    }

    public static boolean isReadable(@Nonnull PacketBuffer buf, int p_isReadable_1_) {
        return buf.isReadable(p_isReadable_1_);
    }

    public static boolean isWritable(@Nonnull PacketBuffer buf) {
        return buf.isWritable();
    }

    public static boolean isWritable(@Nonnull PacketBuffer buf, int p_isWritable_1_) {
        return buf.isWritable(p_isWritable_1_);
    }

    public static ByteBuf clear(@Nonnull PacketBuffer buf) {
        return buf.clear();
    }

    public static ByteBuf markReaderIndex(@Nonnull PacketBuffer buf) {
        return buf.markReaderIndex();
    }

    public static ByteBuf resetReaderIndex(@Nonnull PacketBuffer buf) {
        return buf.resetReaderIndex();
    }

    public static ByteBuf markWriterIndex(@Nonnull PacketBuffer buf) {
        return buf.markWriterIndex();
    }

    public static ByteBuf resetWriterIndex(@Nonnull PacketBuffer buf) {
        return buf.resetWriterIndex();
    }

    public static ByteBuf discardReadBytes(@Nonnull PacketBuffer buf) {
        return buf.discardReadBytes();
    }

    public static ByteBuf discardSomeReadBytes(@Nonnull PacketBuffer buf) {
        return buf.discardSomeReadBytes();
    }

    public static ByteBuf ensureWritable(@Nonnull PacketBuffer buf, int p_ensureWritable_1_) {
        return buf.ensureWritable(p_ensureWritable_1_);
    }

    public static int ensureWritable(@Nonnull PacketBuffer buf, int p_ensureWritable_1_, boolean p_ensureWritable_2_) {
        return buf.ensureWritable(p_ensureWritable_1_, p_ensureWritable_2_);
    }

    public static boolean getBoolean(@Nonnull PacketBuffer buf, int p_getBoolean_1_) {
        return buf.getBoolean(p_getBoolean_1_);
    }

    public static byte getByte(@Nonnull PacketBuffer buf, int p_getByte_1_) {
        return buf.getByte(p_getByte_1_);
    }

    public static short getUnsignedByte(@Nonnull PacketBuffer buf, int p_getUnsignedByte_1_) {
        return buf.getUnsignedByte(p_getUnsignedByte_1_);
    }

    public static short getShort(@Nonnull PacketBuffer buf, int p_getShort_1_) {
        return buf.getShort(p_getShort_1_);
    }

    public static short getShortLE(@Nonnull PacketBuffer buf, int p_getShortLE_1_) {
        return buf.getShortLE(p_getShortLE_1_);
    }

    public static int getUnsignedShort(@Nonnull PacketBuffer buf, int p_getUnsignedShort_1_) {
        return buf.getUnsignedShort(p_getUnsignedShort_1_);
    }

    public static int getUnsignedShortLE(@Nonnull PacketBuffer buf, int p_getUnsignedShortLE_1_) {
        return buf.getUnsignedShortLE(p_getUnsignedShortLE_1_);
    }

    public static int getMedium(@Nonnull PacketBuffer buf, int p_getMedium_1_) {
        return buf.getMedium(p_getMedium_1_);
    }

    public static int getMediumLE(@Nonnull PacketBuffer buf, int p_getMediumLE_1_) {
        return buf.getMediumLE(p_getMediumLE_1_);
    }

    public static int getUnsignedMedium(@Nonnull PacketBuffer buf, int p_getUnsignedMedium_1_) {
        return buf.getUnsignedMedium(p_getUnsignedMedium_1_);
    }

    public static int getUnsignedMediumLE(@Nonnull PacketBuffer buf, int p_getUnsignedMediumLE_1_) {
        return buf.getUnsignedMediumLE(p_getUnsignedMediumLE_1_);
    }

    public static int getInt(@Nonnull PacketBuffer buf, int p_getInt_1_) {
        return buf.getInt(p_getInt_1_);
    }

    public static int getIntLE(@Nonnull PacketBuffer buf, int p_getIntLE_1_) {
        return buf.getIntLE(p_getIntLE_1_);
    }

    public static long getUnsignedInt(@Nonnull PacketBuffer buf, int p_getUnsignedInt_1_) {
        return buf.getUnsignedInt(p_getUnsignedInt_1_);
    }

    public static long getUnsignedIntLE(@Nonnull PacketBuffer buf, int p_getUnsignedIntLE_1_) {
        return buf.getUnsignedIntLE(p_getUnsignedIntLE_1_);
    }

    public static long getLong(@Nonnull PacketBuffer buf, int p_getLong_1_) {
        return buf.getLong(p_getLong_1_);
    }

    public static long getLongLE(@Nonnull PacketBuffer buf, int p_getLongLE_1_) {
        return buf.getLongLE(p_getLongLE_1_);
    }

    public static char getChar(@Nonnull PacketBuffer buf, int p_getChar_1_) {
        return buf.getChar(p_getChar_1_);
    }

    public static float getFloat(@Nonnull PacketBuffer buf, int p_getFloat_1_) {
        return buf.getFloat(p_getFloat_1_);
    }

    public static double getDouble(@Nonnull PacketBuffer buf, int p_getDouble_1_) {
        return buf.getDouble(p_getDouble_1_);
    }

    public static ByteBuf getBytes(@Nonnull PacketBuffer buf, int p_getBytes_1_, ByteBuf p_getBytes_2_) {
        return buf.getBytes(p_getBytes_1_, p_getBytes_2_);
    }

    public static ByteBuf getBytes(@Nonnull PacketBuffer buf, int p_getBytes_1_, ByteBuf p_getBytes_2_, int p_getBytes_3_) {
        return buf.getBytes(p_getBytes_1_, p_getBytes_2_, p_getBytes_3_);
    }

    public static ByteBuf getBytes(@Nonnull PacketBuffer buf, int p_getBytes_1_, ByteBuf p_getBytes_2_, int p_getBytes_3_, int p_getBytes_4_) {
        return buf.getBytes(p_getBytes_1_, p_getBytes_2_, p_getBytes_3_, p_getBytes_4_);
    }

    public static ByteBuf getBytes(@Nonnull PacketBuffer buf, int p_getBytes_1_, byte[] p_getBytes_2_) {
        return buf.getBytes(p_getBytes_1_, p_getBytes_2_);
    }

    public static ByteBuf getBytes(@Nonnull PacketBuffer buf, int p_getBytes_1_, byte[] p_getBytes_2_, int p_getBytes_3_, int p_getBytes_4_) {
        return buf.getBytes(p_getBytes_1_, p_getBytes_2_, p_getBytes_3_, p_getBytes_4_);
    }

    public static ByteBuf getBytes(@Nonnull PacketBuffer buf, int p_getBytes_1_, ByteBuffer p_getBytes_2_) {
        return buf.getBytes(p_getBytes_1_, p_getBytes_2_);
    }

    public static ByteBuf getBytes(@Nonnull PacketBuffer buf, int p_getBytes_1_, OutputStream p_getBytes_2_, int p_getBytes_3_) throws IOException {
        return buf.getBytes(p_getBytes_1_, p_getBytes_2_, p_getBytes_3_);
    }

    public static int getBytes(@Nonnull PacketBuffer buf, int p_getBytes_1_, GatheringByteChannel p_getBytes_2_, int p_getBytes_3_) throws IOException {
        return buf.getBytes(p_getBytes_1_, p_getBytes_2_, p_getBytes_3_);
    }

    public static int getBytes(@Nonnull PacketBuffer buf, int p_getBytes_1_, FileChannel p_getBytes_2_, long p_getBytes_3_, int p_getBytes_5_) throws IOException {
        return buf.getBytes(p_getBytes_1_, p_getBytes_2_, p_getBytes_3_, p_getBytes_5_);
    }

    public static CharSequence getCharSequence(@Nonnull PacketBuffer buf, int p_getCharSequence_1_, int p_getCharSequence_2_, Charset p_getCharSequence_3_) {
        return buf.getCharSequence(p_getCharSequence_1_, p_getCharSequence_2_, p_getCharSequence_3_);
    }

    public static ByteBuf setBoolean(@Nonnull PacketBuffer buf, int p_setBoolean_1_, boolean p_setBoolean_2_) {
        return buf.setBoolean(p_setBoolean_1_, p_setBoolean_2_);
    }

    public static ByteBuf setByte(@Nonnull PacketBuffer buf, int p_setByte_1_, int p_setByte_2_) {
        return buf.setByte(p_setByte_1_, p_setByte_2_);
    }

    public static ByteBuf setShort(@Nonnull PacketBuffer buf, int p_setShort_1_, int p_setShort_2_) {
        return buf.setShort(p_setShort_1_, p_setShort_2_);
    }

    public static ByteBuf setShortLE(@Nonnull PacketBuffer buf, int p_setShortLE_1_, int p_setShortLE_2_) {
        return buf.setShortLE(p_setShortLE_1_, p_setShortLE_2_);
    }

    public static ByteBuf setMedium(@Nonnull PacketBuffer buf, int p_setMedium_1_, int p_setMedium_2_) {
        return buf.setMedium(p_setMedium_1_, p_setMedium_2_);
    }

    public static ByteBuf setMediumLE(@Nonnull PacketBuffer buf, int p_setMediumLE_1_, int p_setMediumLE_2_) {
        return buf.setMediumLE(p_setMediumLE_1_, p_setMediumLE_2_);
    }

    public static ByteBuf setInt(@Nonnull PacketBuffer buf, int p_setInt_1_, int p_setInt_2_) {
        return buf.setInt(p_setInt_1_, p_setInt_2_);
    }

    public static ByteBuf setIntLE(@Nonnull PacketBuffer buf, int p_setIntLE_1_, int p_setIntLE_2_) {
        return buf.setIntLE(p_setIntLE_1_, p_setIntLE_2_);
    }

    public static ByteBuf setLong(@Nonnull PacketBuffer buf, int p_setLong_1_, long p_setLong_2_) {
        return buf.setLong(p_setLong_1_, p_setLong_2_);
    }

    public static ByteBuf setLongLE(@Nonnull PacketBuffer buf, int p_setLongLE_1_, long p_setLongLE_2_) {
        return buf.setLongLE(p_setLongLE_1_, p_setLongLE_2_);
    }

    public static ByteBuf setChar(@Nonnull PacketBuffer buf, int p_setChar_1_, int p_setChar_2_) {
        return buf.setChar(p_setChar_1_, p_setChar_2_);
    }

    public static ByteBuf setFloat(@Nonnull PacketBuffer buf, int p_setFloat_1_, float p_setFloat_2_) {
        return buf.setFloat(p_setFloat_1_, p_setFloat_2_);
    }

    public static ByteBuf setDouble(@Nonnull PacketBuffer buf, int p_setDouble_1_, double p_setDouble_2_) {
        return buf.setDouble(p_setDouble_1_, p_setDouble_2_);
    }

    public static ByteBuf setBytes(@Nonnull PacketBuffer buf, int p_setBytes_1_, ByteBuf p_setBytes_2_) {
        return buf.setBytes(p_setBytes_1_, p_setBytes_2_);
    }

    public static ByteBuf setBytes(@Nonnull PacketBuffer buf, int p_setBytes_1_, ByteBuf p_setBytes_2_, int p_setBytes_3_) {
        return buf.setBytes(p_setBytes_1_, p_setBytes_2_, p_setBytes_3_);
    }

    public static ByteBuf setBytes(@Nonnull PacketBuffer buf, int p_setBytes_1_, ByteBuf p_setBytes_2_, int p_setBytes_3_, int p_setBytes_4_) {
        return buf.setBytes(p_setBytes_1_, p_setBytes_2_, p_setBytes_3_, p_setBytes_4_);
    }

    public static ByteBuf setBytes(@Nonnull PacketBuffer buf, int p_setBytes_1_, byte[] p_setBytes_2_) {
        return buf.setBytes(p_setBytes_1_, p_setBytes_2_);
    }

    public static ByteBuf setBytes(@Nonnull PacketBuffer buf, int p_setBytes_1_, byte[] p_setBytes_2_, int p_setBytes_3_, int p_setBytes_4_) {
        return buf.setBytes(p_setBytes_1_, p_setBytes_2_, p_setBytes_3_, p_setBytes_4_);
    }

    public static ByteBuf setBytes(@Nonnull PacketBuffer buf, int p_setBytes_1_, ByteBuffer p_setBytes_2_) {
        return buf.setBytes(p_setBytes_1_, p_setBytes_2_);
    }

    public static int setBytes(@Nonnull PacketBuffer buf, int p_setBytes_1_, InputStream p_setBytes_2_, int p_setBytes_3_) throws IOException {
        return buf.setBytes(p_setBytes_1_, p_setBytes_2_, p_setBytes_3_);
    }

    public static int setBytes(@Nonnull PacketBuffer buf, int p_setBytes_1_, ScatteringByteChannel p_setBytes_2_, int p_setBytes_3_) throws IOException {
        return buf.setBytes(p_setBytes_1_, p_setBytes_2_, p_setBytes_3_);
    }

    public static int setBytes(@Nonnull PacketBuffer buf, int p_setBytes_1_, FileChannel p_setBytes_2_, long p_setBytes_3_, int p_setBytes_5_) throws IOException {
        return buf.setBytes(p_setBytes_1_, p_setBytes_2_, p_setBytes_3_, p_setBytes_5_);
    }

    public static ByteBuf setZero(@Nonnull PacketBuffer buf, int p_setZero_1_, int p_setZero_2_) {
        return buf.setZero(p_setZero_1_, p_setZero_2_);
    }

    public static int setCharSequence(@Nonnull PacketBuffer buf, int p_setCharSequence_1_, CharSequence p_setCharSequence_2_, Charset p_setCharSequence_3_) {
        return buf.setCharSequence(p_setCharSequence_1_, p_setCharSequence_2_, p_setCharSequence_3_);
    }

    public static boolean readBoolean(@Nonnull PacketBuffer buf) {
        return buf.readBoolean();
    }

    public static byte readByte(@Nonnull PacketBuffer buf) {
        return buf.readByte();
    }

    public static short readUnsignedByte(@Nonnull PacketBuffer buf) {
        return buf.readUnsignedByte();
    }

    public static short readShort(@Nonnull PacketBuffer buf) {
        return buf.readShort();
    }

    public static short readShortLE(@Nonnull PacketBuffer buf) {
        return buf.readShortLE();
    }

    public static int readUnsignedShort(@Nonnull PacketBuffer buf) {
        return buf.readUnsignedShort();
    }

    public static int readUnsignedShortLE(@Nonnull PacketBuffer buf) {
        return buf.readUnsignedShortLE();
    }

    public static int readMedium(@Nonnull PacketBuffer buf) {
        return buf.readMedium();
    }

    public static int readMediumLE(@Nonnull PacketBuffer buf) {
        return buf.readMediumLE();
    }

    public static int readUnsignedMedium(@Nonnull PacketBuffer buf) {
        return buf.readUnsignedMedium();
    }

    public static int readUnsignedMediumLE(@Nonnull PacketBuffer buf) {
        return buf.readUnsignedMediumLE();
    }

    public static int readInt(@Nonnull PacketBuffer buf) {
        return buf.readInt();
    }

    public static int readIntLE(@Nonnull PacketBuffer buf) {
        return buf.readIntLE();
    }

    public static long readUnsignedInt(@Nonnull PacketBuffer buf) {
        return buf.readUnsignedInt();
    }

    public static long readUnsignedIntLE(@Nonnull PacketBuffer buf) {
        return buf.readUnsignedIntLE();
    }

    public static long readLong(@Nonnull PacketBuffer buf) {
        return buf.readLong();
    }

    public static long readLongLE(@Nonnull PacketBuffer buf) {
        return buf.readLongLE();
    }

    public static char readChar(@Nonnull PacketBuffer buf) {
        return buf.readChar();
    }

    public static float readFloat(@Nonnull PacketBuffer buf) {
        return buf.readFloat();
    }

    public static double readDouble(@Nonnull PacketBuffer buf) {
        return buf.readDouble();
    }

    public static ByteBuf readBytes(@Nonnull PacketBuffer buf, int p_readBytes_1_) {
        return buf.readBytes(p_readBytes_1_);
    }

    public static ByteBuf readSlice(@Nonnull PacketBuffer buf, int p_readSlice_1_) {
        return buf.readSlice(p_readSlice_1_);
    }

    public static ByteBuf readRetainedSlice(@Nonnull PacketBuffer buf, int p_readRetainedSlice_1_) {
        return buf.readRetainedSlice(p_readRetainedSlice_1_);
    }

    public static ByteBuf readBytes(@Nonnull PacketBuffer buf, ByteBuf p_readBytes_1_) {
        return buf.readBytes(p_readBytes_1_);
    }

    public static ByteBuf readBytes(@Nonnull PacketBuffer buf, ByteBuf p_readBytes_1_, int p_readBytes_2_) {
        return buf.readBytes(p_readBytes_1_, p_readBytes_2_);
    }

    public static ByteBuf readBytes(@Nonnull PacketBuffer buf, ByteBuf p_readBytes_1_, int p_readBytes_2_, int p_readBytes_3_) {
        return buf.readBytes(p_readBytes_1_, p_readBytes_2_, p_readBytes_3_);
    }

    public static ByteBuf readBytes(@Nonnull PacketBuffer buf, byte[] p_readBytes_1_) {
        return buf.readBytes(p_readBytes_1_);
    }

    public static ByteBuf readBytes(@Nonnull PacketBuffer buf, byte[] p_readBytes_1_, int p_readBytes_2_, int p_readBytes_3_) {
        return buf.readBytes(p_readBytes_1_, p_readBytes_2_, p_readBytes_3_);
    }

    public static ByteBuf readBytes(@Nonnull PacketBuffer buf, ByteBuffer p_readBytes_1_) {
        return buf.readBytes(p_readBytes_1_);
    }

    public static ByteBuf readBytes(@Nonnull PacketBuffer buf, OutputStream p_readBytes_1_, int p_readBytes_2_) throws IOException {
        return buf.readBytes(p_readBytes_1_, p_readBytes_2_);
    }

    public static int readBytes(@Nonnull PacketBuffer buf, GatheringByteChannel p_readBytes_1_, int p_readBytes_2_) throws IOException {
        return buf.readBytes(p_readBytes_1_, p_readBytes_2_);
    }

    public static CharSequence readCharSequence(@Nonnull PacketBuffer buf, int p_readCharSequence_1_, Charset p_readCharSequence_2_) {
        return buf.readCharSequence(p_readCharSequence_1_, p_readCharSequence_2_);
    }

    public static int readBytes(@Nonnull PacketBuffer buf, FileChannel p_readBytes_1_, long p_readBytes_2_, int p_readBytes_4_) throws IOException {
        return buf.readBytes(p_readBytes_1_, p_readBytes_2_, p_readBytes_4_);
    }

    public static ByteBuf skipBytes(@Nonnull PacketBuffer buf, int p_skipBytes_1_) {
        return buf.skipBytes(p_skipBytes_1_);
    }

    public static ByteBuf writeBoolean(@Nonnull PacketBuffer buf, boolean p_writeBoolean_1_) {
        return buf.writeBoolean(p_writeBoolean_1_);
    }

    public static ByteBuf writeByte(@Nonnull PacketBuffer buf, int p_writeByte_1_) {
        return buf.writeByte(p_writeByte_1_);
    }

    public static ByteBuf writeShort(@Nonnull PacketBuffer buf, int p_writeShort_1_) {
        return buf.writeShort(p_writeShort_1_);
    }

    public static ByteBuf writeShortLE(@Nonnull PacketBuffer buf, int p_writeShortLE_1_) {
        return buf.writeShortLE(p_writeShortLE_1_);
    }

    public static ByteBuf writeMedium(@Nonnull PacketBuffer buf, int p_writeMedium_1_) {
        return buf.writeMedium(p_writeMedium_1_);
    }

    public static ByteBuf writeMediumLE(@Nonnull PacketBuffer buf, int p_writeMediumLE_1_) {
        return buf.writeMediumLE(p_writeMediumLE_1_);
    }

    public static ByteBuf writeInt(@Nonnull PacketBuffer buf, int p_writeInt_1_) {
        return buf.writeInt(p_writeInt_1_);
    }

    public static ByteBuf writeIntLE(@Nonnull PacketBuffer buf, int p_writeIntLE_1_) {
        return buf.writeIntLE(p_writeIntLE_1_);
    }

    public static ByteBuf writeLong(@Nonnull PacketBuffer buf, long p_writeLong_1_) {
        return buf.writeLong(p_writeLong_1_);
    }

    public static ByteBuf writeLongLE(@Nonnull PacketBuffer buf, long p_writeLongLE_1_) {
        return buf.writeLongLE(p_writeLongLE_1_);
    }

    public static ByteBuf writeChar(@Nonnull PacketBuffer buf, int p_writeChar_1_) {
        return buf.writeChar(p_writeChar_1_);
    }

    public static ByteBuf writeFloat(@Nonnull PacketBuffer buf, float p_writeFloat_1_) {
        return buf.writeFloat(p_writeFloat_1_);
    }

    public static ByteBuf writeDouble(@Nonnull PacketBuffer buf, double p_writeDouble_1_) {
        return buf.writeDouble(p_writeDouble_1_);
    }

    public static ByteBuf writeBytes(@Nonnull PacketBuffer buf, ByteBuf p_writeBytes_1_) {
        return buf.writeBytes(p_writeBytes_1_);
    }

    public static ByteBuf writeBytes(@Nonnull PacketBuffer buf, ByteBuf p_writeBytes_1_, int p_writeBytes_2_) {
        return buf.writeBytes(p_writeBytes_1_, p_writeBytes_2_);
    }

    public static ByteBuf writeBytes(@Nonnull PacketBuffer buf, ByteBuf p_writeBytes_1_, int p_writeBytes_2_, int p_writeBytes_3_) {
        return buf.writeBytes(p_writeBytes_1_, p_writeBytes_2_, p_writeBytes_3_);
    }

    public static ByteBuf writeBytes(@Nonnull PacketBuffer buf, byte[] p_writeBytes_1_) {
        return buf.writeBytes(p_writeBytes_1_);
    }

    public static ByteBuf writeBytes(@Nonnull PacketBuffer buf, byte[] p_writeBytes_1_, int p_writeBytes_2_, int p_writeBytes_3_) {
        return buf.writeBytes(p_writeBytes_1_, p_writeBytes_2_, p_writeBytes_3_);
    }

    public static ByteBuf writeBytes(@Nonnull PacketBuffer buf, ByteBuffer p_writeBytes_1_) {
        return buf.writeBytes(p_writeBytes_1_);
    }

    public static int writeBytes(@Nonnull PacketBuffer buf, InputStream p_writeBytes_1_, int p_writeBytes_2_) throws IOException {
        return buf.writeBytes(p_writeBytes_1_, p_writeBytes_2_);
    }

    public static int writeBytes(@Nonnull PacketBuffer buf, ScatteringByteChannel p_writeBytes_1_, int p_writeBytes_2_) throws IOException {
        return buf.writeBytes(p_writeBytes_1_, p_writeBytes_2_);
    }

    public static int writeBytes(@Nonnull PacketBuffer buf, FileChannel p_writeBytes_1_, long p_writeBytes_2_, int p_writeBytes_4_) throws IOException {
        return buf.writeBytes(p_writeBytes_1_, p_writeBytes_2_, p_writeBytes_4_);
    }

    public static ByteBuf writeZero(@Nonnull PacketBuffer buf, int p_writeZero_1_) {
        return buf.writeZero(p_writeZero_1_);
    }

    public static int writeCharSequence(@Nonnull PacketBuffer buf, CharSequence p_writeCharSequence_1_, Charset p_writeCharSequence_2_) {
        return buf.writeCharSequence(p_writeCharSequence_1_, p_writeCharSequence_2_);
    }

    public static int indexOf(@Nonnull PacketBuffer buf, int p_indexOf_1_, int p_indexOf_2_, byte p_indexOf_3_) {
        return buf.indexOf(p_indexOf_1_, p_indexOf_2_, p_indexOf_3_);
    }

    public static int bytesBefore(@Nonnull PacketBuffer buf, byte p_bytesBefore_1_) {
        return buf.bytesBefore(p_bytesBefore_1_);
    }

    public static int bytesBefore(@Nonnull PacketBuffer buf, int p_bytesBefore_1_, byte p_bytesBefore_2_) {
        return buf.bytesBefore(p_bytesBefore_1_, p_bytesBefore_2_);
    }

    public static int bytesBefore(@Nonnull PacketBuffer buf, int p_bytesBefore_1_, int p_bytesBefore_2_, byte p_bytesBefore_3_) {
        return buf.bytesBefore(p_bytesBefore_1_, p_bytesBefore_2_, p_bytesBefore_3_);
    }

    public static int forEachByte(@Nonnull PacketBuffer buf, ByteProcessor p_forEachByte_1_) {
        return buf.forEachByte(p_forEachByte_1_);
    }

    public static int forEachByte(@Nonnull PacketBuffer buf, int p_forEachByte_1_, int p_forEachByte_2_, ByteProcessor p_forEachByte_3_) {
        return buf.forEachByte(p_forEachByte_1_, p_forEachByte_2_, p_forEachByte_3_);
    }

    public static int forEachByteDesc(@Nonnull PacketBuffer buf, ByteProcessor p_forEachByteDesc_1_) {
        return buf.forEachByteDesc(p_forEachByteDesc_1_);
    }

    public static int forEachByteDesc(@Nonnull PacketBuffer buf, int p_forEachByteDesc_1_, int p_forEachByteDesc_2_, ByteProcessor p_forEachByteDesc_3_) {
        return buf.forEachByteDesc(p_forEachByteDesc_1_, p_forEachByteDesc_2_, p_forEachByteDesc_3_);
    }

    public static ByteBuf copy(@Nonnull PacketBuffer buf) {
        return buf.copy();
    }

    public static ByteBuf copy(@Nonnull PacketBuffer buf, int p_copy_1_, int p_copy_2_) {
        return buf.copy(p_copy_1_, p_copy_2_);
    }

    public static ByteBuf slice(@Nonnull PacketBuffer buf) {
        return buf.slice();
    }

    public static ByteBuf retainedSlice(@Nonnull PacketBuffer buf) {
        return buf.retainedSlice();
    }

    public static ByteBuf slice(@Nonnull PacketBuffer buf, int p_slice_1_, int p_slice_2_) {
        return buf.slice(p_slice_1_, p_slice_2_);
    }

    public static ByteBuf retainedSlice(@Nonnull PacketBuffer buf, int p_retainedSlice_1_, int p_retainedSlice_2_) {
        return buf.retainedSlice(p_retainedSlice_1_, p_retainedSlice_2_);
    }

    public static ByteBuf duplicate(@Nonnull PacketBuffer buf) {
        return buf.duplicate();
    }

    public static ByteBuf retainedDuplicate(@Nonnull PacketBuffer buf) {
        return buf.retainedDuplicate();
    }

    public static int nioBufferCount(@Nonnull PacketBuffer buf) {
        return buf.nioBufferCount();
    }

    public static ByteBuffer nioBuffer(@Nonnull PacketBuffer buf) {
        return buf.nioBuffer();
    }

    public static ByteBuffer nioBuffer(@Nonnull PacketBuffer buf, int p_nioBuffer_1_, int p_nioBuffer_2_) {
        return buf.nioBuffer(p_nioBuffer_1_, p_nioBuffer_2_);
    }

    public static ByteBuffer internalNioBuffer(@Nonnull PacketBuffer buf, int p_internalNioBuffer_1_, int p_internalNioBuffer_2_) {
        return buf.internalNioBuffer(p_internalNioBuffer_1_, p_internalNioBuffer_2_);
    }

    public static ByteBuffer[] nioBuffers(@Nonnull PacketBuffer buf) {
        return buf.nioBuffers();
    }

    public static ByteBuffer[] nioBuffers(@Nonnull PacketBuffer buf, int p_nioBuffers_1_, int p_nioBuffers_2_) {
        return buf.nioBuffers(p_nioBuffers_1_, p_nioBuffers_2_);
    }

    public static boolean hasArray(@Nonnull PacketBuffer buf) {
        return buf.hasArray();
    }

    public static byte[] array(@Nonnull PacketBuffer buf) {
        return buf.array();
    }

    public static int arrayOffset(@Nonnull PacketBuffer buf) {
        return buf.arrayOffset();
    }

    public static boolean hasMemoryAddress(@Nonnull PacketBuffer buf) {
        return buf.hasMemoryAddress();
    }

    public static long memoryAddress(@Nonnull PacketBuffer buf) {
        return buf.memoryAddress();
    }

    public static String toString(@Nonnull PacketBuffer buf, Charset p_toString_1_) {
        return buf.toString(p_toString_1_);
    }

    public static String toString(@Nonnull PacketBuffer buf, int p_toString_1_, int p_toString_2_, Charset p_toString_3_) {
        return buf.toString(p_toString_1_, p_toString_2_, p_toString_3_);
    }

    public static int compareTo(@Nonnull PacketBuffer buf, ByteBuf p_compareTo_1_) {
        return buf.compareTo(p_compareTo_1_);
    }

    public static ByteBuf retain(@Nonnull PacketBuffer buf, int p_retain_1_) {
        return buf.retain(p_retain_1_);
    }

    public static ByteBuf retain(@Nonnull PacketBuffer buf) {
        return buf.retain();
    }

    public static ByteBuf touch(@Nonnull PacketBuffer buf) {
        return buf.touch();
    }

    public static ByteBuf touch(@Nonnull PacketBuffer buf, Object p_touch_1_) {
        return buf.touch(p_touch_1_);
    }

    public static int refCnt(@Nonnull PacketBuffer buf) {
        return buf.refCnt();
    }

    public static boolean release(@Nonnull PacketBuffer buf) {
        return buf.release();
    }

    public static boolean release(@Nonnull PacketBuffer buf, int p_release_1_) {
        return buf.release(p_release_1_);
    }

}
