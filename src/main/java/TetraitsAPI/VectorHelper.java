package TetraitsAPI;

import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import net.minecraft.dispenser.IPosition;
import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.EnumSet;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class VectorHelper {

    public static float getX(@Nonnull Vec2f vec2f) {
        return vec2f.x;
    }

    public static float getY(@Nonnull Vec2f vec2f) {
        return vec2f.y;
    }

    public static <T> BlockPos deserialize(Dynamic<T> p_218286_0_) {
        return BlockPos.deserialize(p_218286_0_);
    }

    public static <T> T serialize(@Nonnull BlockPos blockPos, DynamicOps<T> p_218175_1_) {
        return blockPos.serialize(p_218175_1_);
    }

    public static long offset(long pos, Direction p_218289_2_) {
        return BlockPos.offset(pos, p_218289_2_);
    }

    public static long offset(long pos, int dx, int dy, int dz) {
        return BlockPos.offset(pos, dx, dy, dz);
    }

    public static int unpackX(long p_218290_0_) {
        return BlockPos.unpackX(p_218290_0_);
    }

    public static int unpackY(long p_218274_0_) {
        return BlockPos.unpackY(p_218274_0_);
    }

    public static int unpackZ(long p_218282_0_) {
        return BlockPos.unpackZ(p_218282_0_);
    }

    public static BlockPos fromLong(long p_218283_0_) {
        return BlockPos.fromLong(p_218283_0_);
    }

    public static long pack(int p_218276_0_, int p_218276_1_, int p_218276_2_) {
        return BlockPos.pack(p_218276_0_, p_218276_1_, p_218276_2_);
    }

    public static long func_218288_f(long p_218288_0_) {
        return BlockPos.func_218288_f(p_218288_0_);
    }

    public static long toLong(@Nonnull BlockPos blockPos) {
        return blockPos.toLong();
    }

    public static BlockPos add(@Nonnull BlockPos blockPos, double x, double y, double z) {
        return blockPos.add(x, y, z);
    }

    public static BlockPos add(@Nonnull BlockPos blockPos, int x, int y, int z) {
        return blockPos.add(x, y, z);
    }

    public static BlockPos add(@Nonnull BlockPos blockPos, Vec3i vec) {
        return blockPos.add(vec);
    }

    public static BlockPos subtract(@Nonnull BlockPos blockPos, Vec3i vec) {
        return blockPos.subtract(vec);
    }

    public static BlockPos up(@Nonnull BlockPos blockPos) {
        return blockPos.up();
    }

    public static BlockPos up(@Nonnull BlockPos blockPos, int n) {
        return blockPos.up(n);
    }

    public static BlockPos down(@Nonnull BlockPos blockPos) {
        return blockPos.down();
    }

    public static BlockPos down(@Nonnull BlockPos blockPos, int n) {
        return blockPos.down(n);
    }

    public static BlockPos north(@Nonnull BlockPos blockPos) {
        return blockPos.north();
    }

    public static BlockPos north(@Nonnull BlockPos blockPos, int n) {
        return blockPos.north(n);
    }

    public static BlockPos south(@Nonnull BlockPos blockPos) {
        return blockPos.south();
    }

    public static BlockPos south(@Nonnull BlockPos blockPos, int n) {
        return blockPos.south(n);
    }

    public static BlockPos west(@Nonnull BlockPos blockPos) {
        return blockPos.west();
    }

    public static BlockPos west(@Nonnull BlockPos blockPos, int n) {
        return blockPos.west(n);
    }

    public static BlockPos east(@Nonnull BlockPos blockPos) {
        return blockPos.east();
    }

    public static BlockPos east(@Nonnull BlockPos blockPos, int n) {
        return blockPos.east(n);
    }

    public static BlockPos offset(@Nonnull BlockPos blockPos, Direction facing) {
        return blockPos.offset(facing);
    }

    public static BlockPos offset(@Nonnull BlockPos blockPos, Direction facing, int n) {
        return blockPos.offset(facing, n);
    }

    public static BlockPos rotate(@Nonnull BlockPos blockPos, Rotation rotationIn) {
        return blockPos.rotate(rotationIn);
    }

    public static BlockPos crossProduct(@Nonnull BlockPos blockPos, Vec3i vec) {
        return blockPos.crossProduct(vec);
    }

    public static BlockPos toImmutable(@Nonnull BlockPos blockPos) {
        return blockPos.toImmutable();
    }

    public static Iterable<BlockPos> getAllInBoxMutable(BlockPos firstPos, BlockPos secondPos) {
        return BlockPos.getAllInBoxMutable(firstPos, secondPos);
    }

    public static Stream<BlockPos> getAllInBox(BlockPos firstPos, BlockPos secondPos) {
        return BlockPos.getAllInBox(firstPos, secondPos);
    }

    public static Stream<BlockPos> getAllInBox(MutableBoundingBox p_229383_0_) {
        return BlockPos.getAllInBox(p_229383_0_);
    }

    public static Stream<BlockPos> getAllInBox(int p_218287_0_, int p_218287_1_, int p_218287_2_, int p_218287_3_, int p_218287_4_, int p_218287_5_) {
        return BlockPos.getAllInBox(p_218287_0_, p_218287_1_, p_218287_2_, p_218287_3_, p_218287_4_, p_218287_5_);
    }

    public static Iterable<BlockPos> getAllInBoxMutable(int x1, int y1, int z1, int x2, int y2, int z2) {
        return BlockPos.getAllInBoxMutable(x1, y1, z1, x2, y2, z2);
    }

    public static int compareTo(@Nonnull Vec3i vec3i, Vec3i p_compareTo_1_) {
        return vec3i.compareTo(p_compareTo_1_);
    }

    public static int getX(@Nonnull Vec3i vec3i) {
        return vec3i.getX();
    }

    public static int getY(@Nonnull Vec3i vec3i) {
        return vec3i.getY();
    }

    public static int getZ(@Nonnull Vec3i vec3i) {
        return vec3i.getZ();
    }

    public static Vec3i down(@Nonnull Vec3i vec3i) {
        return vec3i.down();
    }

    public static Vec3i down(@Nonnull Vec3i vec3i, int n) {
        return vec3i.down(n);
    }

    public static Vec3i offset(@Nonnull Vec3i vec3i, Direction facing, int n) {
        return vec3i.offset(facing, n);
    }

    public static Vec3i crossProduct(@Nonnull Vec3i vec3i, Vec3i vec) {
        return vec3i.crossProduct(vec);
    }

    public static boolean withinDistance(@Nonnull Vec3i vec3i, Vec3i p_218141_1_, double distance) {
        return vec3i.withinDistance(p_218141_1_, distance);
    }

    public static boolean withinDistance(@Nonnull Vec3i vec3i, IPosition p_218137_1_, double distance) {
        return vec3i.withinDistance(p_218137_1_, distance);
    }

    public static double distanceSq(@Nonnull Vec3i vec3i, Vec3i to) {
        return vec3i.distanceSq(to);
    }

    public static double distanceSq(@Nonnull Vec3i vec3i, IPosition p_218138_1_, boolean useCenter) {
        return vec3i.distanceSq(p_218138_1_, useCenter);
    }

    public static double distanceSq(@Nonnull Vec3i vec3i, double p_218140_1_, double p_218140_3_, double p_218140_5_, boolean useCenter) {
        return vec3i.distanceSq(p_218140_1_, p_218140_3_, p_218140_5_, useCenter);
    }

    public static int manhattanDistance(@Nonnull Vec3i vec3i, Vec3i p_218139_1_) {
        return vec3i.manhattanDistance(p_218139_1_);
    }

    @OnlyIn(Dist.CLIENT)
    public static String func_229422_x_(@Nonnull Vec3i vec3i) {
        return vec3i.func_229422_x_();
    }

    public static Vec3d subtractReverse(@Nonnull Vec3d vec, Vec3d otherVec) {
        return vec.subtractReverse(otherVec);
    }

    public static Vec3d normalize(@Nonnull Vec3d vec) {
        return vec.normalize();
    }

    public static double dotProduct(@Nonnull Vec3d vec, Vec3d otherVec) {
        return vec.dotProduct(otherVec);
    }

    public static Vec3d crossProduct(@Nonnull Vec3d vec, Vec3d otherVec) {
        return vec.crossProduct(otherVec);
    }

    public static Vec3d subtract(@Nonnull Vec3d vec, Vec3d otherVec) {
        return vec.subtract(otherVec);
    }

    public static Vec3d subtract(@Nonnull Vec3d vec, double x, double y, double z) {
        return vec.subtract(x, y, z);
    }

    public static Vec3d add(@Nonnull Vec3d vec, Vec3d otherVec) {
        return vec.add(otherVec);
    }

    public static Vec3d add(@Nonnull Vec3d vec, double x, double y, double z) {
        return vec.add(x, y, z);
    }

    public static double distanceTo(@Nonnull Vec3d vec, Vec3d otherVec) {
        return vec.distanceTo(otherVec);
    }

    public static double squareDistanceTo(@Nonnull Vec3d vec, Vec3d otherVec) {
        return vec.squareDistanceTo(otherVec);
    }

    public static double squareDistanceTo(@Nonnull Vec3d vec, double xIn, double yIn, double zIn) {
        return vec.squareDistanceTo(xIn, yIn, zIn);
    }

    public static Vec3d scale(@Nonnull Vec3d vec, double factor) {
        return vec.scale(factor);
    }

    public static Vec3d inverse(@Nonnull Vec3d vec) {
        return vec.inverse();
    }

    public static Vec3d mul(@Nonnull Vec3d vec, Vec3d p_216369_1_) {
        return vec.mul(p_216369_1_);
    }

    public static Vec3d mul(@Nonnull Vec3d vec, double factorX, double factorY, double factorZ) {
        return vec.mul(factorX, factorY, factorZ);
    }

    public static double length(@Nonnull Vec3d vec) {
        return vec.length();
    }

    public static double lengthSquared(@Nonnull Vec3d vec) {
        return vec.lengthSquared();
    }

    public static Vec3d rotatePitch(@Nonnull Vec3d vec, float pitch) {
        return vec.rotatePitch(pitch);
    }

    public static Vec3d rotateYaw(@Nonnull Vec3d vec, float yaw) {
        return vec.rotateYaw(yaw);
    }

    public static Vec3d fromPitchYaw(Vec2f p_189984_0_) {
        return Vec3d.fromPitchYaw(p_189984_0_);
    }

    public static Vec3d fromPitchYaw(float pitch, float yaw) {
        return Vec3d.fromPitchYaw(pitch, yaw);
    }

    public static Vec3d align(@Nonnull Vec3d vec, EnumSet<Direction.Axis> axes) {
        return vec.align(axes);
    }

    public static double getCoordinate(@Nonnull Vec3d vec, Direction.Axis axis) {
        return vec.getCoordinate(axis);
    }

    public static double getX(@Nonnull Vec3d vec) {
        return vec.getX();
    }

    public static double getY(@Nonnull Vec3d vec) {
        return vec.getY();
    }

    public static double getZ(@Nonnull Vec3d vec) {
        return vec.getZ();
    }

}
