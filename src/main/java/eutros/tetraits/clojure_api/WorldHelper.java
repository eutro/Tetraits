package eutros.tetraits.clojure_api;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.particles.IParticleData;
import net.minecraft.profiler.IProfiler;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tags.NetworkTagManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.*;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.*;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.AbstractChunkProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.lighting.WorldLightManager;
import net.minecraft.world.storage.MapData;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityDispatcher;
import net.minecraftforge.common.capabilities.CapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Wrap all World methods using mapped names.
 */
@SuppressWarnings("unused")
public class WorldHelper {

    public static boolean isRemote(@Nonnull World world) {
        return world.isRemote();
    }

    @Nullable
    public static MinecraftServer getServer(@Nonnull World world) {
        return world.getServer();
    }

    @OnlyIn(Dist.CLIENT)
    public static void setInitialSpawnLocation(@Nonnull World world) {
        world.setInitialSpawnLocation();
    }

    public static BlockState getGroundAboveSeaLevel(@Nonnull World world, BlockPos pos) {
        return world.getGroundAboveSeaLevel(pos);
    }

    public static boolean isValid(BlockPos pos) {
        return World.isValid(pos);
    }

    public static boolean isOutsideBuildHeight(BlockPos pos) {
        return World.isOutsideBuildHeight(pos);
    }

    public static boolean isYOutOfBounds(int y) {
        return World.isYOutOfBounds(y);
    }

    public static Chunk getChunkAt(@Nonnull World world, BlockPos pos) {
        return world.getChunkAt(pos);
    }

    public static Chunk getChunk(@Nonnull World world, int chunkX, int chunkZ) {
        return world.getChunk(chunkX, chunkZ);
    }

    public static IChunk getChunk(@Nonnull World world, int x, int z, ChunkStatus requiredStatus, boolean nonnull) {
        return world.getChunk(x, z, requiredStatus, nonnull);
    }

    public static boolean setBlockState(@Nonnull World world, BlockPos pos, BlockState newState, int flags) {
        return world.setBlockState(pos, newState, flags);
    }

    public static void markAndNotifyBlock(@Nonnull World world, BlockPos pos, @Nullable Chunk chunk, BlockState blockstate, BlockState newState, int flags) {
        world.markAndNotifyBlock(pos, chunk, blockstate, newState, flags);
    }

    public static void onBlockStateChange(@Nonnull World world, BlockPos pos, BlockState blockStateIn, BlockState newState) {
        world.onBlockStateChange(pos, blockStateIn, newState);
    }

    public static boolean removeBlock(@Nonnull World world, BlockPos pos, boolean isMoving) {
        return world.removeBlock(pos, isMoving);
    }

    public static boolean destroyBlock(@Nonnull World world, BlockPos p_225521_1_, boolean p_225521_2_, @Nullable Entity p_225521_3_) {
        return world.destroyBlock(p_225521_1_, p_225521_2_, p_225521_3_);
    }

    public static boolean setBlockState(@Nonnull World world, BlockPos pos, BlockState state) {
        return world.setBlockState(pos, state);
    }

    public static void notifyBlockUpdate(@Nonnull World world, BlockPos pos, BlockState oldState, BlockState newState, int flags) {
        world.notifyBlockUpdate(pos, oldState, newState, flags);
    }

    public static void notifyNeighbors(@Nonnull World world, BlockPos pos, Block blockIn) {
        world.notifyNeighbors(pos, blockIn);
    }

    public static void markBlockRangeForRenderUpdate(@Nonnull World world, BlockPos blockPosIn, BlockState oldState, BlockState newState) {
        world.markBlockRangeForRenderUpdate(blockPosIn, oldState, newState);
    }

    public static void notifyNeighborsOfStateChange(@Nonnull World world, BlockPos pos, Block blockIn) {
        world.notifyNeighborsOfStateChange(pos, blockIn);
    }

    public static void notifyNeighborsOfStateExcept(@Nonnull World world, BlockPos pos, Block blockType, Direction skipSide) {
        world.notifyNeighborsOfStateExcept(pos, blockType, skipSide);
    }

    public static void neighborChanged(@Nonnull World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
        world.neighborChanged(pos, blockIn, fromPos);
    }

    public static int getHeight(@Nonnull World world, Heightmap.Type heightmapType, int x, int z) {
        return world.getHeight(heightmapType, x, z);
    }

    public static WorldLightManager getLightManager(@Nonnull World world) {
        return world.getLightManager();
    }

    public static BlockState getBlockState(@Nonnull World world, BlockPos pos) {
        return world.getBlockState(pos);
    }

    public static IFluidState getFluidState(@Nonnull World world, BlockPos pos) {
        return world.getFluidState(pos);
    }

    public static boolean isDaytime(@Nonnull World world) {
        return world.isDaytime();
    }

    public static boolean isNightTime(@Nonnull World world) {
        return world.isNightTime();
    }

    public static void playSound(@Nonnull World world, @Nullable PlayerEntity player, BlockPos pos, SoundEvent soundIn, SoundCategory category, float volume, float pitch) {
        world.playSound(player, pos, soundIn, category, volume, pitch);
    }

    public static void playSound(@Nonnull World world, @Nullable PlayerEntity player, double x, double y, double z, SoundEvent soundIn, SoundCategory category, float volume, float pitch) {
        world.playSound(player, x, y, z, soundIn, category, volume, pitch);
    }

    public static void playMovingSound(@Nonnull World world, @Nullable PlayerEntity playerIn, Entity entityIn, SoundEvent eventIn, SoundCategory categoryIn, float volume, float pitch) {
        world.playMovingSound(playerIn, entityIn, eventIn, categoryIn, volume, pitch);
    }

    public static void playSound(@Nonnull World world, double x, double y, double z, SoundEvent soundIn, SoundCategory category, float volume, float pitch, boolean distanceDelay) {
        world.playSound(x, y, z, soundIn, category, volume, pitch, distanceDelay);
    }

    public static void addParticle(@Nonnull World world, IParticleData particleData, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        world.addParticle(particleData, x, y, z, xSpeed, ySpeed, zSpeed);
    }

    @OnlyIn(Dist.CLIENT)
    public static void addParticle(@Nonnull World world, IParticleData particleData, boolean forceAlwaysRender, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        world.addParticle(particleData, forceAlwaysRender, x, y, z, xSpeed, ySpeed, zSpeed);
    }

    public static void addOptionalParticle(@Nonnull World world, IParticleData particleData, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        world.addOptionalParticle(particleData, x, y, z, xSpeed, ySpeed, zSpeed);
    }

    public static void addOptionalParticle(@Nonnull World world, IParticleData particleData, boolean ignoreRange, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        world.addOptionalParticle(particleData, ignoreRange, x, y, z, xSpeed, ySpeed, zSpeed);
    }

    public static float getCelestialAngleRadians(@Nonnull World world, float partialTicks) {
        return world.getCelestialAngleRadians(partialTicks);
    }

    public static boolean addTileEntity(@Nonnull World world, TileEntity tile) {
        return world.addTileEntity(tile);
    }

    public static void addTileEntities(@Nonnull World world, Collection<TileEntity> tileEntityCollection) {
        world.addTileEntities(tileEntityCollection);
    }

    public static void tickBlockEntities(@Nonnull World world) {
        world.tickBlockEntities();
    }

    public static void guardEntityTick(@Nonnull World world, Consumer<Entity> consumerEntity, Entity entityIn) {
        world.guardEntityTick(consumerEntity, entityIn);
    }

    public static boolean checkBlockCollision(@Nonnull World world, AxisAlignedBB bb) {
        return world.checkBlockCollision(bb);
    }

    public static boolean isFlammableWithin(@Nonnull World world, AxisAlignedBB bb) {
        return world.isFlammableWithin(bb);
    }

    @OnlyIn(Dist.CLIENT)
    @Nullable
    public static BlockState findBlockstateInArea(@Nonnull World world, AxisAlignedBB area, Block blockIn) {
        return world.findBlockstateInArea(area, blockIn);
    }

    public static boolean isMaterialInBB(@Nonnull World world, AxisAlignedBB bb, Material materialIn) {
        return world.isMaterialInBB(bb, materialIn);
    }

    public static Explosion createExplosion(@Nonnull World world, @Nullable Entity entityIn, double xIn, double yIn, double zIn, float explosionRadius, Explosion.Mode modeIn) {
        return world.createExplosion(entityIn, xIn, yIn, zIn, explosionRadius, modeIn);
    }

    public static Explosion createExplosion(@Nonnull World world, @Nullable Entity entityIn, double xIn, double yIn, double zIn, float explosionRadius, boolean causesFire, Explosion.Mode modeIn) {
        return world.createExplosion(entityIn, xIn, yIn, zIn, explosionRadius, causesFire, modeIn);
    }

    public static Explosion createExplosion(@Nonnull World world, @Nullable Entity entityIn, @Nullable DamageSource damageSourceIn, double xIn, double yIn, double zIn, float explosionRadius, boolean causesFire, Explosion.Mode modeIn) {
        return world.createExplosion(entityIn, damageSourceIn, xIn, yIn, zIn, explosionRadius, causesFire, modeIn);
    }

    public static boolean extinguishFire(@Nonnull World world, @Nullable PlayerEntity player, BlockPos pos, Direction side) {
        return world.extinguishFire(player, pos, side);
    }

    @OnlyIn(Dist.CLIENT)
    public static String getProviderName(@Nonnull World world) {
        return world.getProviderName();
    }

    @Nullable
    public static TileEntity getTileEntity(@Nonnull World world, BlockPos pos) {
        return world.getTileEntity(pos);
    }

    public static void setTileEntity(@Nonnull World world, BlockPos pos, @Nullable TileEntity tileEntityIn) {
        world.setTileEntity(pos, tileEntityIn);
    }

    public static void removeTileEntity(@Nonnull World world, BlockPos pos) {
        world.removeTileEntity(pos);
    }

    public static boolean isBlockPresent(@Nonnull World world, BlockPos pos) {
        return world.isBlockPresent(pos);
    }

    public static boolean isTopSolid(@Nonnull World world, BlockPos pos, Entity entityIn) {
        return world.isTopSolid(pos, entityIn);
    }

    public static void calculateInitialSkylight(@Nonnull World world) {
        world.calculateInitialSkylight();
    }

    public static void setAllowedSpawnTypes(@Nonnull World world, boolean hostile, boolean peaceful) {
        world.setAllowedSpawnTypes(hostile, peaceful);
    }

    public static void calculateInitialWeatherBody(@Nonnull World world) {
        world.calculateInitialWeatherBody();
    }

    public static void close(@Nonnull World world) throws IOException {
        world.close();
    }

    @Nullable
    public static IBlockReader getBlockReader(@Nonnull World world, int chunkX, int chunkZ) {
        return world.getBlockReader(chunkX, chunkZ);
    }

    public static List<Entity> getEntitiesInAABBexcluding(@Nonnull World world, @Nullable Entity entityIn, AxisAlignedBB boundingBox, @Nullable Predicate<? super Entity> predicate) {
        return world.getEntitiesInAABBexcluding(entityIn, boundingBox, predicate);
    }

    public static <T extends Entity> List<T> getEntitiesWithinAABB(@Nonnull World world, @Nullable EntityType<T> type, AxisAlignedBB boundingBox, Predicate<? super T> predicate) {
        return world.getEntitiesWithinAABB(type, boundingBox, predicate);
    }

    public static <T extends Entity> List<T> getEntitiesWithinAABB(@Nonnull World world, Class<? extends T> clazz, AxisAlignedBB aabb, @Nullable Predicate<? super T> filter) {
        return world.getEntitiesWithinAABB(clazz, aabb, filter);
    }

    public static <T extends Entity> List<T> getLoadedEntitiesWithinAABB(@Nonnull World world, Class<? extends T> p_225316_1_, AxisAlignedBB p_225316_2_, @Nullable Predicate<? super T> p_225316_3_) {
        return world.getLoadedEntitiesWithinAABB(p_225316_1_, p_225316_2_, p_225316_3_);
    }

    @Nullable
    public static Entity getEntityByID(@Nonnull World world, int id) {
        return world.getEntityByID(id);
    }

    public static void markChunkDirty(@Nonnull World world, BlockPos pos, TileEntity unusedTileEntity) {
        world.markChunkDirty(pos, unusedTileEntity);
    }

    public static int getSeaLevel(@Nonnull World world) {
        return world.getSeaLevel();
    }

    public static World getWorld(@Nonnull World world) {
        return world.getWorld();
    }

    public static WorldType getWorldType(@Nonnull World world) {
        return world.getWorldType();
    }

    public static int getStrongPower(@Nonnull World world, BlockPos pos) {
        return world.getStrongPower(pos);
    }

    public static boolean isSidePowered(@Nonnull World world, BlockPos pos, Direction side) {
        return world.isSidePowered(pos, side);
    }

    public static int getRedstonePower(@Nonnull World world, BlockPos pos, Direction facing) {
        return world.getRedstonePower(pos, facing);
    }

    public static boolean isBlockPowered(@Nonnull World world, BlockPos pos) {
        return world.isBlockPowered(pos);
    }

    public static int getRedstonePowerFromNeighbors(@Nonnull World world, BlockPos pos) {
        return world.getRedstonePowerFromNeighbors(pos);
    }

    @OnlyIn(Dist.CLIENT)
    public static void sendQuittingDisconnectingPacket(@Nonnull World world) {
        world.sendQuittingDisconnectingPacket();
    }

    public static void setGameTime(@Nonnull World world, long worldTime) {
        world.setGameTime(worldTime);
    }

    public static long getSeed(@Nonnull World world) {
        return world.getSeed();
    }

    public static long getGameTime(@Nonnull World world) {
        return world.getGameTime();
    }

    public static long getDayTime(@Nonnull World world) {
        return world.getDayTime();
    }

    public static void setDayTime(@Nonnull World world, long time) {
        world.setDayTime(time);
    }

    public static BlockPos getSpawnPoint(@Nonnull World world) {
        return world.getSpawnPoint();
    }

    public static void setSpawnPoint(@Nonnull World world, BlockPos pos) {
        world.setSpawnPoint(pos);
    }

    public static boolean isBlockModifiable(@Nonnull World world, PlayerEntity player, BlockPos pos) {
        return world.isBlockModifiable(player, pos);
    }

    public static boolean canMineBlockBody(@Nonnull World world, PlayerEntity player, BlockPos pos) {
        return world.canMineBlockBody(player, pos);
    }

    public static void setEntityState(@Nonnull World world, Entity entityIn, byte state) {
        world.setEntityState(entityIn, state);
    }

    public static AbstractChunkProvider getChunkProvider(@Nonnull World world) {
        return world.getChunkProvider();
    }

    public static void addBlockEvent(@Nonnull World world, BlockPos pos, Block blockIn, int eventID, int eventParam) {
        world.addBlockEvent(pos, blockIn, eventID, eventParam);
    }

    public static WorldInfo getWorldInfo(@Nonnull World world) {
        return world.getWorldInfo();
    }

    public static GameRules getGameRules(@Nonnull World world) {
        return world.getGameRules();
    }

    public static float getThunderStrength(@Nonnull World world, float delta) {
        return world.getThunderStrength(delta);
    }

    @OnlyIn(Dist.CLIENT)
    public static void setThunderStrength(@Nonnull World world, float strength) {
        world.setThunderStrength(strength);
    }

    public static float getRainStrength(@Nonnull World world, float delta) {
        return world.getRainStrength(delta);
    }

    @OnlyIn(Dist.CLIENT)
    public static void setRainStrength(@Nonnull World world, float strength) {
        world.setRainStrength(strength);
    }

    public static boolean isThundering(@Nonnull World world) {
        return world.isThundering();
    }

    public static boolean isRaining(@Nonnull World world) {
        return world.isRaining();
    }

    public static boolean isRainingAt(@Nonnull World world, BlockPos position) {
        return world.isRainingAt(position);
    }

    public static boolean isBlockinHighHumidity(@Nonnull World world, BlockPos pos) {
        return world.isBlockinHighHumidity(pos);
    }

    @Nullable
    public static MapData getMapData(@Nonnull World world, String mapName) {
        return world.getMapData(mapName);
    }

    public static void registerMapData(@Nonnull World world, MapData mapDataIn) {
        world.registerMapData(mapDataIn);
    }

    public static int getNextMapId(@Nonnull World world) {
        return world.getNextMapId();
    }

    public static void playBroadcastSound(@Nonnull World world, int id, BlockPos pos, int data) {
        world.playBroadcastSound(id, pos, data);
    }

    public static int getActualHeight(@Nonnull World world) {
        return world.getActualHeight();
    }

    public static CrashReportCategory fillCrashReport(@Nonnull World world, CrashReport report) {
        return world.fillCrashReport(report);
    }

    public static void sendBlockBreakProgress(@Nonnull World world, int breakerId, BlockPos pos, int progress) {
        world.sendBlockBreakProgress(breakerId, pos, progress);
    }

    @OnlyIn(Dist.CLIENT)
    public static void makeFireworks(@Nonnull World world, double x, double y, double z, double motionX, double motionY, double motionZ, @Nullable CompoundNBT compound) {
        world.makeFireworks(x, y, z, motionX, motionY, motionZ, compound);
    }

    public static Scoreboard getScoreboard(@Nonnull World world) {
        return world.getScoreboard();
    }

    public static void updateComparatorOutputLevel(@Nonnull World world, BlockPos pos, Block blockIn) {
        world.updateComparatorOutputLevel(pos, blockIn);
    }

    public static DifficultyInstance getDifficultyForLocation(@Nonnull World world, BlockPos pos) {
        return world.getDifficultyForLocation(pos);
    }

    public static int getSkylightSubtracted(@Nonnull World world) {
        return world.getSkylightSubtracted();
    }

    public static void setTimeLightningFlash(@Nonnull World world, int timeFlashIn) {
        world.setTimeLightningFlash(timeFlashIn);
    }

    public static WorldBorder getWorldBorder(@Nonnull World world) {
        return world.getWorldBorder();
    }

    public static void sendPacketToServer(@Nonnull World world, IPacket<?> packetIn) {
        world.sendPacketToServer(packetIn);
    }

    public static Dimension getDimension(@Nonnull World world) {
        return world.getDimension();
    }

    public static Random getRandom(@Nonnull World world) {
        return world.getRandom();
    }

    public static boolean hasBlockState(@Nonnull World world, BlockPos p_217375_1_, Predicate<BlockState> p_217375_2_) {
        return world.hasBlockState(p_217375_1_, p_217375_2_);
    }

    public static RecipeManager getRecipeManager(@Nonnull World world) {
        return world.getRecipeManager();
    }

    public static NetworkTagManager getTags(@Nonnull World world) {
        return world.getTags();
    }

    public static BlockPos getBlockRandomPos(@Nonnull World world, int p_217383_1_, int p_217383_2_, int p_217383_3_, int p_217383_4_) {
        return world.getBlockRandomPos(p_217383_1_, p_217383_2_, p_217383_3_, p_217383_4_);
    }

    public static boolean isSaveDisabled(@Nonnull World world) {
        return world.isSaveDisabled();
    }

    public static IProfiler getProfiler(@Nonnull World world) {
        return world.getProfiler();
    }

    public static BiomeManager getBiomeManager(@Nonnull World world) {
        return world.getBiomeManager();
    }

    public static double getMaxEntityRadius(@Nonnull World world) {
        return world.getMaxEntityRadius();
    }

    public static double increaseMaxEntityRadius(@Nonnull World world, double value) {
        return world.increaseMaxEntityRadius(value);
    }

    public static boolean areCapsCompatible(@Nonnull World world, CapabilityProvider<World> other) {
        return world.areCapsCompatible(other);
    }

    public static boolean areCapsCompatible(@Nonnull World world, @Nullable CapabilityDispatcher other) {
        return world.areCapsCompatible(other);
    }

    @Nonnull
    public static <T> LazyOptional<T> getCapability(@Nonnull World world, @Nonnull Capability<T> cap, @Nullable Direction side) {
        return world.getCapability(cap, side);
    }

    @Nonnull
    public static <T> LazyOptional<T> getCapability(@Nonnull World world, @Nonnull Capability<T> cap) {
        return world.getCapability(cap);
    }

    public static float getCurrentMoonPhaseFactor(@Nonnull World world) {
        return world.getCurrentMoonPhaseFactor();
    }

    public static float getCelestialAngle(@Nonnull World world, float partialTicks) {
        return world.getCelestialAngle(partialTicks);
    }

    @OnlyIn(Dist.CLIENT)
    public static int getMoonPhase(@Nonnull World world) {
        return world.getMoonPhase();
    }

    public static ITickList<Block> getPendingBlockTicks(@Nonnull World world) {
        return world.getPendingBlockTicks();
    }

    public static ITickList<Fluid> getPendingFluidTicks(@Nonnull World world) {
        return world.getPendingFluidTicks();
    }

    public static Difficulty getDifficulty(@Nonnull World world) {
        return world.getDifficulty();
    }

    public static boolean chunkExists(@Nonnull World world, int chunkX, int chunkZ) {
        return world.chunkExists(chunkX, chunkZ);
    }

    public static void playEvent(@Nonnull World world, @Nullable PlayerEntity player, int type, BlockPos pos, int data) {
        world.playEvent(player, type, pos, data);
    }

    public static void playEvent(@Nonnull World world, int type, BlockPos pos, int data) {
        world.playEvent(type, pos, data);
    }

    public static Stream<VoxelShape> getEmptyCollisionShapes(@Nonnull World world, @Nullable Entity entityIn, AxisAlignedBB aabb, Set<Entity> entitiesToIgnore) {
        return world.getEmptyCollisionShapes(entityIn, aabb, entitiesToIgnore);
    }

    public static boolean checkNoEntityCollision(@Nonnull World world, @Nullable Entity entityIn, VoxelShape shape) {
        return world.checkNoEntityCollision(entityIn, shape);
    }

    public static BlockPos getHeight(@Nonnull World world, Heightmap.Type heightmapType, BlockPos pos) {
        return world.getHeight(heightmapType, pos);
    }

    public static List<? extends PlayerEntity> getPlayers(@Nonnull World world) {
        return world.getPlayers();
    }

    public static List<Entity> getEntitiesWithinAABBExcludingEntity(@Nonnull World world, @Nullable Entity entityIn, AxisAlignedBB bb) {
        return world.getEntitiesWithinAABBExcludingEntity(entityIn, bb);
    }

    public static <T extends Entity> List<T> getEntitiesWithinAABB(@Nonnull World world, Class<? extends T> p_217357_1_, AxisAlignedBB p_217357_2_) {
        return world.getEntitiesWithinAABB(p_217357_1_, p_217357_2_);
    }

    public static <T extends Entity> List<T> getLoadedEntitiesWithinAABB(@Nonnull World world, Class<? extends T> p_225317_1_, AxisAlignedBB p_225317_2_) {
        return world.getLoadedEntitiesWithinAABB(p_225317_1_, p_225317_2_);
    }

    @Nullable
    public static PlayerEntity getClosestPlayer(@Nonnull World world, double x, double y, double z, double distance, @Nullable Predicate<Entity> predicate) {
        return world.getClosestPlayer(x, y, z, distance, predicate);
    }

    @Nullable
    public static PlayerEntity getClosestPlayer(@Nonnull World world, Entity entityIn, double distance) {
        return world.getClosestPlayer(entityIn, distance);
    }

    @Nullable
    public static PlayerEntity getClosestPlayer(@Nonnull World world, double x, double y, double z, double distance, boolean creativePlayers) {
        return world.getClosestPlayer(x, y, z, distance, creativePlayers);
    }

    @Nullable
    public static PlayerEntity getClosestPlayer(@Nonnull World world, double x, double y, double z) {
        return world.getClosestPlayer(x, y, z);
    }

    public static boolean isPlayerWithin(@Nonnull World world, double x, double y, double z, double distance) {
        return world.isPlayerWithin(x, y, z, distance);
    }

    @Nullable
    public static PlayerEntity getClosestPlayer(@Nonnull World world, EntityPredicate predicate, LivingEntity target) {
        return world.getClosestPlayer(predicate, target);
    }

    @Nullable
    public static PlayerEntity getClosestPlayer(@Nonnull World world, EntityPredicate predicate, LivingEntity target, double p_217372_3_, double p_217372_5_, double p_217372_7_) {
        return world.getClosestPlayer(predicate, target, p_217372_3_, p_217372_5_, p_217372_7_);
    }

    @Nullable
    public static PlayerEntity getClosestPlayer(@Nonnull World world, EntityPredicate predicate, double x, double y, double z) {
        return world.getClosestPlayer(predicate, x, y, z);
    }

    @Nullable
    public static <T extends LivingEntity> T getClosestEntityWithinAABB(@Nonnull World world, Class<? extends T> entityClazz, EntityPredicate p_217360_2_, @Nullable LivingEntity target, double x, double y, double z, AxisAlignedBB boundingBox) {
        return world.getClosestEntityWithinAABB(entityClazz, p_217360_2_, target, x, y, z, boundingBox);
    }

    @Nullable
    public static <T extends LivingEntity> T func_225318_b(@Nonnull World world, Class<? extends T> p_225318_1_, EntityPredicate p_225318_2_, @Nullable LivingEntity p_225318_3_, double p_225318_4_, double p_225318_6_, double p_225318_8_, AxisAlignedBB p_225318_10_) {
        return world.func_225318_b(p_225318_1_, p_225318_2_, p_225318_3_, p_225318_4_, p_225318_6_, p_225318_8_, p_225318_10_);
    }

    @Nullable
    public static <T extends LivingEntity> T getClosestEntity(@Nonnull World world, List<? extends T> entities, EntityPredicate predicate, @Nullable LivingEntity target, double x, double y, double z) {
        return world.getClosestEntity(entities, predicate, target, x, y, z);
    }

    public static List<PlayerEntity> getTargettablePlayersWithinAABB(@Nonnull World world, EntityPredicate predicate, LivingEntity target, AxisAlignedBB box) {
        return world.getTargettablePlayersWithinAABB(predicate, target, box);
    }

    public static <T extends LivingEntity> List<T> getTargettableEntitiesWithinAABB(@Nonnull World world, Class<? extends T> p_217374_1_, EntityPredicate p_217374_2_, LivingEntity p_217374_3_, AxisAlignedBB p_217374_4_) {
        return world.getTargettableEntitiesWithinAABB(p_217374_1_, p_217374_2_, p_217374_3_, p_217374_4_);
    }

    @Nullable
    public static PlayerEntity getPlayerByUuid(@Nonnull World world, UUID uniqueIdIn) {
        return world.getPlayerByUuid(uniqueIdIn);
    }

    public static Biome getBiome(@Nonnull World world, BlockPos p_226691_1_) {
        return world.getBiome(p_226691_1_);
    }

    @OnlyIn(Dist.CLIENT)
    public static int getBlockColor(@Nonnull World world, BlockPos blockPosIn, ColorResolver colorResolverIn) {
        return world.getBlockColor(blockPosIn, colorResolverIn);
    }

    public static Biome getNoiseBiome(@Nonnull World world, int x, int y, int z) {
        return world.getNoiseBiome(x, y, z);
    }

    public static Biome getNoiseBiomeRaw(@Nonnull World world, int x, int y, int z) {
        return world.getNoiseBiomeRaw(x, y, z);
    }

    public static boolean isAirBlock(@Nonnull World world, BlockPos pos) {
        return world.isAirBlock(pos);
    }

    public static boolean canBlockSeeSky(@Nonnull World world, BlockPos pos) {
        return world.canBlockSeeSky(pos);
    }

    @Deprecated
    public static float getBrightness(@Nonnull World world, BlockPos pos) {
        return world.getBrightness(pos);
    }

    public static int getStrongPower(@Nonnull World world, BlockPos pos, Direction direction) {
        return world.getStrongPower(pos, direction);
    }

    public static IChunk getChunk(@Nonnull World world, BlockPos pos) {
        return world.getChunk(pos);
    }

    public static IChunk getChunk(@Nonnull World world, int chunkX, int chunkZ, ChunkStatus requiredStatus) {
        return world.getChunk(chunkX, chunkZ, requiredStatus);
    }

    public static boolean hasWater(@Nonnull World world, BlockPos pos) {
        return world.hasWater(pos);
    }

    public static boolean containsAnyLiquid(@Nonnull World world, AxisAlignedBB bb) {
        return world.containsAnyLiquid(bb);
    }

    public static int getLight(@Nonnull World world, BlockPos pos) {
        return world.getLight(pos);
    }

    public static int getNeighborAwareLightSubtracted(@Nonnull World world, BlockPos pos, int amount) {
        return world.getNeighborAwareLightSubtracted(pos, amount);
    }

    @Deprecated
    public static boolean isBlockLoaded(@Nonnull World world, BlockPos pos) {
        return world.isBlockLoaded(pos);
    }

    public static boolean isAreaLoaded(@Nonnull World world, BlockPos center, int range) {
        return world.isAreaLoaded(center, range);
    }

    @Deprecated
    public static boolean isAreaLoaded(@Nonnull World world, BlockPos from, BlockPos to) {
        return world.isAreaLoaded(from, to);
    }

    @Deprecated
    public static boolean isAreaLoaded(@Nonnull World world, int fromX, int fromY, int fromZ, int toX, int toY, int toZ) {
        return world.isAreaLoaded(fromX, fromY, fromZ, toX, toY, toZ);
    }

    public static int getLightFor(@Nonnull World world, LightType lightTypeIn, BlockPos blockPosIn) {
        return world.getLightFor(lightTypeIn, blockPosIn);
    }

    public static int getLightSubtracted(@Nonnull World world, BlockPos blockPosIn, int amount) {
        return world.getLightSubtracted(blockPosIn, amount);
    }

    public static boolean canSeeSky(@Nonnull World world, BlockPos blockPosIn) {
        return world.canSeeSky(blockPosIn);
    }

    public static int getLightValue(@Nonnull World world, BlockPos pos) {
        return world.getLightValue(pos);
    }

    public static int getMaxLightLevel(@Nonnull World world) {
        return world.getMaxLightLevel();
    }

    public static int getHeight(@Nonnull World world) {
        return world.getHeight();
    }

    public static BlockRayTraceResult rayTraceBlocks(@Nonnull World world, RayTraceContext context) {
        return world.rayTraceBlocks(context);
    }

    @Nullable
    public static BlockRayTraceResult rayTraceBlocks(@Nonnull World world, Vec3d p_217296_1_, Vec3d p_217296_2_, BlockPos p_217296_3_, VoxelShape p_217296_4_, BlockState p_217296_5_) {
        return world.rayTraceBlocks(p_217296_1_, p_217296_2_, p_217296_3_, p_217296_4_, p_217296_5_);
    }

    public static <T> T func_217300_a(RayTraceContext p_217300_0_, BiFunction<RayTraceContext, BlockPos, T> p_217300_1_, Function<RayTraceContext, T> p_217300_2_) {
        return IBlockReader.func_217300_a(p_217300_0_, p_217300_1_, p_217300_2_);
    }

    public static boolean func_226663_a_(@Nonnull World world, BlockState p_226663_1_, BlockPos p_226663_2_, ISelectionContext p_226663_3_) {
        return world.func_226663_a_(p_226663_1_, p_226663_2_, p_226663_3_);
    }

    public static boolean checkNoEntityCollision(@Nonnull World world, Entity p_226668_1_) {
        return world.checkNoEntityCollision(p_226668_1_);
    }

    public static boolean hasNoCollisions(@Nonnull World world, AxisAlignedBB p_226664_1_) {
        return world.hasNoCollisions(p_226664_1_);
    }

    public static boolean hasNoCollisions(@Nonnull World world, Entity p_226669_1_) {
        return world.hasNoCollisions(p_226669_1_);
    }

    public static boolean hasNoCollisions(@Nonnull World world, Entity p_226665_1_, AxisAlignedBB p_226665_2_) {
        return world.hasNoCollisions(p_226665_1_, p_226665_2_);
    }

    public static boolean hasNoCollisions(@Nonnull World world, @Nullable Entity p_226662_1_, AxisAlignedBB p_226662_2_, Set<Entity> p_226662_3_) {
        return world.hasNoCollisions(p_226662_1_, p_226662_2_, p_226662_3_);
    }

    public static Stream<VoxelShape> getCollisionShapes(@Nonnull World world, @Nullable Entity p_226667_1_, AxisAlignedBB p_226667_2_, Set<Entity> p_226667_3_) {
        return world.getCollisionShapes(p_226667_1_, p_226667_2_, p_226667_3_);
    }

    public static Stream<VoxelShape> getCollisionShapes(@Nonnull World world, @Nullable Entity p_226666_1_, AxisAlignedBB p_226666_2_) {
        return world.getCollisionShapes(p_226666_1_, p_226666_2_);
    }

    public static int getMaxHeight(@Nonnull World world) {
        return world.getMaxHeight();
    }

    public static boolean destroyBlock(@Nonnull World world, BlockPos pos, boolean dropBlock) {
        return world.destroyBlock(pos, dropBlock);
    }

    public static boolean addEntity(@Nonnull World world, Entity entityIn) {
        return world.addEntity(entityIn);
    }

}
