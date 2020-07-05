package tetraits_api;

import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.util.Either;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.PushReaction;
import net.minecraft.command.CommandSource;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.inventory.EnderChestInventory;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MerchantOffers;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.Stat;
import net.minecraft.tags.Tag;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.*;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityDispatcher;
import net.minecraftforge.common.capabilities.CapabilityProvider;
import net.minecraftforge.common.util.ITeleporter;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Stream;

/**
 * Wrap all common Entity, LivingEntity and PlayerEntity methods using mapped names.
 *
 * There are repeated entries because I'm lazy.
 */
@SuppressWarnings("unused")
public class EntityHelper {

    public static boolean blockActionRestricted(@Nonnull PlayerEntity playerEntity, World worldIn, BlockPos pos, GameType gameMode) {
        return playerEntity.blockActionRestricted(worldIn, pos, gameMode);
    }

    public static void tick(@Nonnull PlayerEntity playerEntity) {
        playerEntity.tick();
    }

    public static boolean isSecondaryUseActive(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.isSecondaryUseActive();
    }

    public static int getMaxInPortalTime(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getMaxInPortalTime();
    }

    public static int getPortalCooldown(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getPortalCooldown();
    }

    public static void playSound(@Nonnull PlayerEntity playerEntity, SoundEvent soundIn, float volume, float pitch) {
        playerEntity.playSound(soundIn, volume, pitch);
    }

    public static void playSound(@Nonnull PlayerEntity playerEntity, SoundEvent p_213823_1_, SoundCategory p_213823_2_, float p_213823_3_, float p_213823_4_) {
        playerEntity.playSound(p_213823_1_, p_213823_2_, p_213823_3_, p_213823_4_);
    }

    public static SoundCategory getSoundCategory(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getSoundCategory();
    }

    @OnlyIn(Dist.CLIENT)
    public static void handleStatusUpdate(@Nonnull PlayerEntity playerEntity, byte id) {
        playerEntity.handleStatusUpdate(id);
    }

    public static void closeScreen(@Nonnull PlayerEntity playerEntity) {
        playerEntity.closeScreen();
    }

    public static void updateRidden(@Nonnull PlayerEntity playerEntity) {
        playerEntity.updateRidden();
    }

    @OnlyIn(Dist.CLIENT)
    public static void preparePlayerToSpawn(@Nonnull PlayerEntity playerEntity) {
        playerEntity.preparePlayerToSpawn();
    }

    public static void livingTick(@Nonnull PlayerEntity playerEntity) {
        playerEntity.livingTick();
    }

    public static int getScore(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getScore();
    }

    public static void setScore(@Nonnull PlayerEntity playerEntity, int scoreIn) {
        playerEntity.setScore(scoreIn);
    }

    public static void addScore(@Nonnull PlayerEntity playerEntity, int scoreIn) {
        playerEntity.addScore(scoreIn);
    }

    public static void onDeath(@Nonnull PlayerEntity playerEntity, DamageSource cause) {
        playerEntity.onDeath(cause);
    }

    public static boolean drop(@Nonnull PlayerEntity playerEntity, boolean p_225609_1_) {
        return playerEntity.drop(p_225609_1_);
    }

    @Nullable
    public static ItemEntity dropItem(@Nonnull PlayerEntity playerEntity, ItemStack itemStackIn, boolean unused) {
        return playerEntity.dropItem(itemStackIn, unused);
    }

    @Nullable
    public static ItemEntity dropItem(@Nonnull PlayerEntity playerEntity, ItemStack droppedItem, boolean dropAround, boolean traceItem) {
        return playerEntity.dropItem(droppedItem, dropAround, traceItem);
    }

    @Deprecated
    public static float getDigSpeed(@Nonnull PlayerEntity playerEntity, BlockState state) {
        return playerEntity.getDigSpeed(state);
    }

    public static float getDigSpeed(@Nonnull PlayerEntity playerEntity, BlockState state, @Nullable BlockPos pos) {
        return playerEntity.getDigSpeed(state, pos);
    }

    public static boolean canHarvestBlock(@Nonnull PlayerEntity playerEntity, BlockState state) {
        return playerEntity.canHarvestBlock(state);
    }

    public static void readAdditional(@Nonnull PlayerEntity playerEntity, CompoundNBT compound) {
        playerEntity.readAdditional(compound);
    }

    public static void writeAdditional(@Nonnull PlayerEntity playerEntity, CompoundNBT compound) {
        playerEntity.writeAdditional(compound);
    }

    public static boolean isInvulnerableTo(@Nonnull PlayerEntity playerEntity, DamageSource source) {
        return playerEntity.isInvulnerableTo(source);
    }

    public static boolean attackEntityFrom(@Nonnull PlayerEntity playerEntity, DamageSource source, float amount) {
        return playerEntity.attackEntityFrom(source, amount);
    }

    public static boolean canAttackPlayer(@Nonnull PlayerEntity playerEntity, PlayerEntity other) {
        return playerEntity.canAttackPlayer(other);
    }

    public static void openSignEditor(@Nonnull PlayerEntity playerEntity, SignTileEntity signTile) {
        playerEntity.openSignEditor(signTile);
    }

    public static void openMinecartCommandBlock(@Nonnull PlayerEntity playerEntity, CommandBlockLogic commandBlock) {
        playerEntity.openMinecartCommandBlock(commandBlock);
    }

    public static void openCommandBlock(@Nonnull PlayerEntity playerEntity, CommandBlockTileEntity commandBlock) {
        playerEntity.openCommandBlock(commandBlock);
    }

    public static void openStructureBlock(@Nonnull PlayerEntity playerEntity, StructureBlockTileEntity structure) {
        playerEntity.openStructureBlock(structure);
    }

    public static void openJigsaw(@Nonnull PlayerEntity playerEntity, JigsawTileEntity p_213826_1_) {
        playerEntity.openJigsaw(p_213826_1_);
    }

    public static void openHorseInventory(@Nonnull PlayerEntity playerEntity, AbstractHorseEntity horse, IInventory inventoryIn) {
        playerEntity.openHorseInventory(horse, inventoryIn);
    }

    public static OptionalInt openContainer(@Nonnull PlayerEntity playerEntity, @Nullable INamedContainerProvider p_213829_1_) {
        return playerEntity.openContainer(p_213829_1_);
    }

    public static void openMerchantContainer(@Nonnull PlayerEntity playerEntity, int containerId, MerchantOffers offers, int level, int xp, boolean p_213818_5_, boolean p_213818_6_) {
        playerEntity.openMerchantContainer(containerId, offers, level, xp, p_213818_5_, p_213818_6_);
    }

    public static void openBook(@Nonnull PlayerEntity playerEntity, ItemStack stack, Hand hand) {
        playerEntity.openBook(stack, hand);
    }

    public static ActionResultType interactOn(@Nonnull PlayerEntity playerEntity, Entity entityToInteractOn, Hand hand) {
        return playerEntity.interactOn(entityToInteractOn, hand);
    }

    public static double getYOffset(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getYOffset();
    }

    public static void stopRiding(@Nonnull PlayerEntity playerEntity) {
        playerEntity.stopRiding();
    }

    public static void attackTargetEntityWithCurrentItem(@Nonnull PlayerEntity playerEntity, Entity targetEntity) {
        playerEntity.attackTargetEntityWithCurrentItem(targetEntity);
    }

    public static void disableShield(@Nonnull PlayerEntity playerEntity, boolean p_190777_1_) {
        playerEntity.disableShield(p_190777_1_);
    }

    public static void onCriticalHit(@Nonnull PlayerEntity playerEntity, Entity entityHit) {
        playerEntity.onCriticalHit(entityHit);
    }

    public static void onEnchantmentCritical(@Nonnull PlayerEntity playerEntity, Entity entityHit) {
        playerEntity.onEnchantmentCritical(entityHit);
    }

    public static void spawnSweepParticles(@Nonnull PlayerEntity playerEntity) {
        playerEntity.spawnSweepParticles();
    }

    @OnlyIn(Dist.CLIENT)
    public static void respawnPlayer(@Nonnull PlayerEntity playerEntity) {
        playerEntity.respawnPlayer();
    }

    public static void remove(@Nonnull PlayerEntity playerEntity, boolean keepData) {
        playerEntity.remove(keepData);
    }

    public static boolean isUser(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.isUser();
    }

    public static GameProfile getGameProfile(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getGameProfile();
    }

    public static Either<PlayerEntity.SleepResult, Unit> trySleep(@Nonnull PlayerEntity playerEntity, BlockPos at) {
        return playerEntity.trySleep(at);
    }

    public static void startSleeping(@Nonnull PlayerEntity playerEntity, BlockPos pos) {
        playerEntity.startSleeping(pos);
    }

    public static void stopSleepInBed(@Nonnull PlayerEntity playerEntity, boolean p_225652_1_, boolean p_225652_2_) {
        playerEntity.stopSleepInBed(p_225652_1_, p_225652_2_);
    }

    public static void wakeUp(@Nonnull PlayerEntity playerEntity) {
        playerEntity.wakeUp();
    }

    public static Optional<Vec3d> checkBedValidRespawnPosition(IWorldReader p_213822_0_, BlockPos p_213822_1_, boolean p_213822_2_) {
        return PlayerEntity.checkBedValidRespawnPosition(p_213822_0_, p_213822_1_, p_213822_2_);
    }

    public static boolean isPlayerFullyAsleep(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.isPlayerFullyAsleep();
    }

    public static int getSleepTimer(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getSleepTimer();
    }

    public static void sendStatusMessage(@Nonnull PlayerEntity playerEntity, ITextComponent chatComponent, boolean actionBar) {
        playerEntity.sendStatusMessage(chatComponent, actionBar);
    }

    @Deprecated
    public static BlockPos getBedLocation(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getBedLocation();
    }

    public static BlockPos getBedLocation(@Nonnull PlayerEntity playerEntity, DimensionType dim) {
        return playerEntity.getBedLocation(dim);
    }

    @Deprecated
    public static boolean isSpawnForced(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.isSpawnForced();
    }

    public static boolean isSpawnForced(@Nonnull PlayerEntity playerEntity, DimensionType dim) {
        return playerEntity.isSpawnForced(dim);
    }

    @Deprecated
    public static void setRespawnPosition(@Nonnull PlayerEntity playerEntity, BlockPos p_226560_1_, boolean p_226560_2_, boolean p_226560_3_) {
        playerEntity.setRespawnPosition(p_226560_1_, p_226560_2_, p_226560_3_);
    }

    public static void setSpawnPoint(@Nonnull PlayerEntity playerEntity, @Nullable BlockPos p_226560_1_, boolean p_226560_2_, boolean p_226560_3_, DimensionType dim) {
        playerEntity.setSpawnPoint(p_226560_1_, p_226560_2_, p_226560_3_, dim);
    }

    public static void addStat(@Nonnull PlayerEntity playerEntity, ResourceLocation stat) {
        playerEntity.addStat(stat);
    }

    public static void addStat(@Nonnull PlayerEntity playerEntity, ResourceLocation p_195067_1_, int p_195067_2_) {
        playerEntity.addStat(p_195067_1_, p_195067_2_);
    }

    public static void addStat(@Nonnull PlayerEntity playerEntity, Stat<?> stat) {
        playerEntity.addStat(stat);
    }

    public static void addStat(@Nonnull PlayerEntity playerEntity, Stat<?> stat, int amount) {
        playerEntity.addStat(stat, amount);
    }

    public static void takeStat(@Nonnull PlayerEntity playerEntity, Stat<?> stat) {
        playerEntity.takeStat(stat);
    }

    public static int unlockRecipes(@Nonnull PlayerEntity playerEntity, Collection<IRecipe<?>> p_195065_1_) {
        return playerEntity.unlockRecipes(p_195065_1_);
    }

    public static void unlockRecipes(@Nonnull PlayerEntity playerEntity, ResourceLocation[] p_193102_1_) {
        playerEntity.unlockRecipes(p_193102_1_);
    }

    public static int resetRecipes(@Nonnull PlayerEntity playerEntity, Collection<IRecipe<?>> p_195069_1_) {
        return playerEntity.resetRecipes(p_195069_1_);
    }

    public static void jump(@Nonnull PlayerEntity playerEntity) {
        playerEntity.jump();
    }

    public static void travel(@Nonnull PlayerEntity playerEntity, Vec3d p_213352_1_) {
        playerEntity.travel(p_213352_1_);
    }

    public static void updateSwimming(@Nonnull PlayerEntity playerEntity) {
        playerEntity.updateSwimming();
    }

    public static float getAIMoveSpeed(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getAIMoveSpeed();
    }

    public static void addMovementStat(@Nonnull PlayerEntity playerEntity, double p_71000_1_, double p_71000_3_, double p_71000_5_) {
        playerEntity.addMovementStat(p_71000_1_, p_71000_3_, p_71000_5_);
    }

    public static boolean onLivingFall(@Nonnull PlayerEntity playerEntity, float distance, float damageMultiplier) {
        return playerEntity.onLivingFall(distance, damageMultiplier);
    }

    public static boolean tryToStartFallFlying(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.tryToStartFallFlying();
    }

    public static void startFallFlying(@Nonnull PlayerEntity playerEntity) {
        playerEntity.startFallFlying();
    }

    public static void stopFallFlying(@Nonnull PlayerEntity playerEntity) {
        playerEntity.stopFallFlying();
    }

    public static void onKillEntity(@Nonnull PlayerEntity playerEntity, LivingEntity entityLivingIn) {
        playerEntity.onKillEntity(entityLivingIn);
    }

    public static void setMotionMultiplier(@Nonnull PlayerEntity playerEntity, BlockState state, Vec3d motionMultiplierIn) {
        playerEntity.setMotionMultiplier(state, motionMultiplierIn);
    }

    public static void giveExperiencePoints(@Nonnull PlayerEntity playerEntity, int p_195068_1_) {
        playerEntity.giveExperiencePoints(p_195068_1_);
    }

    public static int getXPSeed(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getXPSeed();
    }

    public static void onEnchant(@Nonnull PlayerEntity playerEntity, ItemStack enchantedItem, int cost) {
        playerEntity.onEnchant(enchantedItem, cost);
    }

    public static void addExperienceLevel(@Nonnull PlayerEntity playerEntity, int levels) {
        playerEntity.addExperienceLevel(levels);
    }

    public static int xpBarCap(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.xpBarCap();
    }

    public static void addExhaustion(@Nonnull PlayerEntity playerEntity, float exhaustion) {
        playerEntity.addExhaustion(exhaustion);
    }

    public static FoodStats getFoodStats(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getFoodStats();
    }

    public static boolean canEat(@Nonnull PlayerEntity playerEntity, boolean ignoreHunger) {
        return playerEntity.canEat(ignoreHunger);
    }

    public static boolean shouldHeal(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.shouldHeal();
    }

    public static boolean isAllowEdit(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.isAllowEdit();
    }

    public static boolean canPlayerEdit(@Nonnull PlayerEntity playerEntity, BlockPos pos, Direction facing, ItemStack stack) {
        return playerEntity.canPlayerEdit(pos, facing, stack);
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean getAlwaysRenderNameTagForRender(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getAlwaysRenderNameTagForRender();
    }

    public static void sendPlayerAbilities(@Nonnull PlayerEntity playerEntity) {
        playerEntity.sendPlayerAbilities();
    }

    public static void setGameType(@Nonnull PlayerEntity playerEntity, GameType gameType) {
        playerEntity.setGameType(gameType);
    }

    public static ITextComponent getName(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getName();
    }

    public static EnderChestInventory getInventoryEnderChest(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getInventoryEnderChest();
    }

    public static ItemStack getItemStackFromSlot(@Nonnull PlayerEntity playerEntity, EquipmentSlotType slotIn) {
        return playerEntity.getItemStackFromSlot(slotIn);
    }

    public static void setItemStackToSlot(@Nonnull PlayerEntity playerEntity, EquipmentSlotType slotIn, ItemStack stack) {
        playerEntity.setItemStackToSlot(slotIn, stack);
    }

    public static boolean addItemStackToInventory(@Nonnull PlayerEntity playerEntity, ItemStack p_191521_1_) {
        return playerEntity.addItemStackToInventory(p_191521_1_);
    }

    public static Iterable<ItemStack> getHeldEquipment(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getHeldEquipment();
    }

    public static Iterable<ItemStack> getArmorInventoryList(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getArmorInventoryList();
    }

    public static boolean addShoulderEntity(@Nonnull PlayerEntity playerEntity, CompoundNBT p_192027_1_) {
        return playerEntity.addShoulderEntity(p_192027_1_);
    }

    public static boolean isSpectator(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.isSpectator();
    }

    public static boolean isSwimming(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.isSwimming();
    }

    public static boolean isCreative(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.isCreative();
    }

    public static boolean isPushedByWater(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.isPushedByWater();
    }

    public static Scoreboard getWorldScoreboard(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getWorldScoreboard();
    }

    public static ITextComponent getDisplayName(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getDisplayName();
    }

    public static ITextComponent getDisplayNameAndUUID(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getDisplayNameAndUUID();
    }

    public static String getScoreboardName(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getScoreboardName();
    }

    public static float getStandingEyeHeight(@Nonnull PlayerEntity playerEntity, Pose poseIn, EntitySize sizeIn) {
        return playerEntity.getStandingEyeHeight(poseIn, sizeIn);
    }

    public static void setAbsorptionAmount(@Nonnull PlayerEntity playerEntity, float amount) {
        playerEntity.setAbsorptionAmount(amount);
    }

    public static float getAbsorptionAmount(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getAbsorptionAmount();
    }

    public static UUID getUUID(GameProfile profile) {
        return PlayerEntity.getUUID(profile);
    }

    public static UUID getOfflineUUID(String username) {
        return PlayerEntity.getOfflineUUID(username);
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean isWearing(@Nonnull PlayerEntity playerEntity, PlayerModelPart part) {
        return playerEntity.isWearing(part);
    }

    public static boolean replaceItemInInventory(@Nonnull PlayerEntity playerEntity, int inventorySlot, ItemStack itemStackIn) {
        return playerEntity.replaceItemInInventory(inventorySlot, itemStackIn);
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean hasReducedDebug(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.hasReducedDebug();
    }

    @OnlyIn(Dist.CLIENT)
    public static void setReducedDebug(@Nonnull PlayerEntity playerEntity, boolean reducedDebug) {
        playerEntity.setReducedDebug(reducedDebug);
    }

    public static HandSide getPrimaryHand(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getPrimaryHand();
    }

    public static void setPrimaryHand(@Nonnull PlayerEntity playerEntity, HandSide hand) {
        playerEntity.setPrimaryHand(hand);
    }

    public static CompoundNBT getLeftShoulderEntity(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getLeftShoulderEntity();
    }

    public static CompoundNBT getRightShoulderEntity(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getRightShoulderEntity();
    }

    public static float getCooldownPeriod(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getCooldownPeriod();
    }

    public static float getCooledAttackStrength(@Nonnull PlayerEntity playerEntity, float adjustTicks) {
        return playerEntity.getCooledAttackStrength(adjustTicks);
    }

    public static void resetCooldown(@Nonnull PlayerEntity playerEntity) {
        playerEntity.resetCooldown();
    }

    public static CooldownTracker getCooldownTracker(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getCooldownTracker();
    }

    public static float getLuck(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getLuck();
    }

    public static boolean canUseCommandBlock(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.canUseCommandBlock();
    }

    public static boolean canPickUpItem(@Nonnull PlayerEntity playerEntity, ItemStack itemstackIn) {
        return playerEntity.canPickUpItem(itemstackIn);
    }

    public static EntitySize getSize(@Nonnull PlayerEntity playerEntity, Pose poseIn) {
        return playerEntity.getSize(poseIn);
    }

    public static ItemStack findAmmo(@Nonnull PlayerEntity playerEntity, ItemStack shootable) {
        return playerEntity.findAmmo(shootable);
    }

    public static ItemStack onFoodEaten(@Nonnull PlayerEntity playerEntity, World p_213357_1_, ItemStack p_213357_2_) {
        return playerEntity.onFoodEaten(p_213357_1_, p_213357_2_);
    }

    public static DimensionType getSpawnDimension(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getSpawnDimension();
    }

    public static void setSpawnDimenion(@Nonnull PlayerEntity playerEntity, DimensionType dim) {
        playerEntity.setSpawnDimenion(dim);
    }

    public static Collection<ITextComponent> getPrefixes(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getPrefixes();
    }

    public static Collection<ITextComponent> getSuffixes(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getSuffixes();
    }

    public static <T> LazyOptional<T> getCapability(@Nonnull PlayerEntity playerEntity, Capability<T> capability, @Nullable Direction facing) {
        return playerEntity.getCapability(capability, facing);
    }

    public static Brain<?> getBrain(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getBrain();
    }

    public static void onKillCommand(@Nonnull PlayerEntity playerEntity) {
        playerEntity.onKillCommand();
    }

    public static boolean canAttack(@Nonnull PlayerEntity playerEntity, EntityType<?> typeIn) {
        return playerEntity.canAttack(typeIn);
    }

    public static boolean canBreatheUnderwater(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.canBreatheUnderwater();
    }

    @OnlyIn(Dist.CLIENT)
    public static float getSwimAnimation(@Nonnull PlayerEntity playerEntity, float partialTicks) {
        return playerEntity.getSwimAnimation(partialTicks);
    }

    public static void baseTick(@Nonnull PlayerEntity playerEntity) {
        playerEntity.baseTick();
    }

    public static boolean isChild(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.isChild();
    }

    public static float getRenderScale(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getRenderScale();
    }

    public static boolean canBeRiddenInWater(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.canBeRiddenInWater();
    }

    public static Random getRNG(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getRNG();
    }

    @Nullable
    public static LivingEntity getRevengeTarget(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getRevengeTarget();
    }

    public static int getRevengeTimer(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getRevengeTimer();
    }

    public static void setRevengeTarget(@Nonnull PlayerEntity playerEntity, @Nullable LivingEntity livingBase) {
        playerEntity.setRevengeTarget(livingBase);
    }

    @Nullable
    public static LivingEntity getLastAttackedEntity(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getLastAttackedEntity();
    }

    public static int getLastAttackedEntityTime(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getLastAttackedEntityTime();
    }

    public static void setLastAttackedEntity(@Nonnull PlayerEntity playerEntity, Entity entityIn) {
        playerEntity.setLastAttackedEntity(entityIn);
    }

    public static int getIdleTime(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getIdleTime();
    }

    public static void setIdleTime(@Nonnull PlayerEntity playerEntity, int idleTimeIn) {
        playerEntity.setIdleTime(idleTimeIn);
    }

    public static double getVisibilityMultiplier(@Nonnull PlayerEntity playerEntity, @Nullable Entity lookingEntity) {
        return playerEntity.getVisibilityMultiplier(lookingEntity);
    }

    public static boolean canAttack(@Nonnull PlayerEntity playerEntity, LivingEntity target) {
        return playerEntity.canAttack(target);
    }

    public static boolean canAttack(@Nonnull PlayerEntity playerEntity, LivingEntity livingentityIn, EntityPredicate predicateIn) {
        return playerEntity.canAttack(livingentityIn, predicateIn);
    }

    public static boolean clearActivePotions(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.clearActivePotions();
    }

    public static Collection<EffectInstance> getActivePotionEffects(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getActivePotionEffects();
    }

    public static Map<Effect, EffectInstance> getActivePotionMap(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getActivePotionMap();
    }

    public static boolean isPotionActive(@Nonnull PlayerEntity playerEntity, Effect potionIn) {
        return playerEntity.isPotionActive(potionIn);
    }

    @Nullable
    public static EffectInstance getActivePotionEffect(@Nonnull PlayerEntity playerEntity, Effect potionIn) {
        return playerEntity.getActivePotionEffect(potionIn);
    }

    public static boolean addPotionEffect(@Nonnull PlayerEntity playerEntity, EffectInstance effectInstanceIn) {
        return playerEntity.addPotionEffect(effectInstanceIn);
    }

    public static boolean isPotionApplicable(@Nonnull PlayerEntity playerEntity, EffectInstance potioneffectIn) {
        return playerEntity.isPotionApplicable(potioneffectIn);
    }

    public static boolean isEntityUndead(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.isEntityUndead();
    }

    @Nullable
    public static EffectInstance removeActivePotionEffect(@Nonnull PlayerEntity playerEntity, @Nullable Effect potioneffectin) {
        return playerEntity.removeActivePotionEffect(potioneffectin);
    }

    public static boolean removePotionEffect(@Nonnull PlayerEntity playerEntity, Effect effectIn) {
        return playerEntity.removePotionEffect(effectIn);
    }

    public static void heal(@Nonnull PlayerEntity playerEntity, float healAmount) {
        playerEntity.heal(healAmount);
    }

    public static float getHealth(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getHealth();
    }

    public static void setHealth(@Nonnull PlayerEntity playerEntity, float health) {
        playerEntity.setHealth(health);
    }

    @Nullable
    public static DamageSource getLastDamageSource(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getLastDamageSource();
    }

    public static ResourceLocation getLootTableResourceLocation(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getLootTableResourceLocation();
    }

    public static void knockBack(@Nonnull PlayerEntity playerEntity, Entity entityIn, float strength, double xRatio, double zRatio) {
        playerEntity.knockBack(entityIn, strength, xRatio, zRatio);
    }

    public static SoundEvent getEatSound(@Nonnull PlayerEntity playerEntity, ItemStack itemStackIn) {
        return playerEntity.getEatSound(itemStackIn);
    }

    public static boolean isOnLadder(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.isOnLadder();
    }

    public static BlockState getBlockState(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getBlockState();
    }

    public static boolean isAlive(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.isAlive();
    }

    @OnlyIn(Dist.CLIENT)
    public static void performHurtAnimation(@Nonnull PlayerEntity playerEntity) {
        playerEntity.performHurtAnimation();
    }

    public static int getTotalArmorValue(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getTotalArmorValue();
    }

    public static CombatTracker getCombatTracker(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getCombatTracker();
    }

    @Nullable
    public static LivingEntity getAttackingEntity(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getAttackingEntity();
    }

    public static float getMaxHealth(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getMaxHealth();
    }

    public static int getArrowCountInEntity(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getArrowCountInEntity();
    }

    public static void setArrowCountInEntity(@Nonnull PlayerEntity playerEntity, int count) {
        playerEntity.setArrowCountInEntity(count);
    }

    public static int getBeeStingCount(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getBeeStingCount();
    }

    public static void setBeeStingCount(@Nonnull PlayerEntity playerEntity, int p_226300_1_) {
        playerEntity.setBeeStingCount(p_226300_1_);
    }

    public static void swingArm(@Nonnull PlayerEntity playerEntity, Hand hand) {
        playerEntity.swingArm(hand);
    }

    public static void swing(@Nonnull PlayerEntity playerEntity, Hand handIn, boolean p_226292_2_) {
        playerEntity.swing(handIn, p_226292_2_);
    }

    public static IAttributeInstance getAttribute(@Nonnull PlayerEntity playerEntity, IAttribute attribute) {
        return playerEntity.getAttribute(attribute);
    }

    public static AbstractAttributeMap getAttributes(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getAttributes();
    }

    public static CreatureAttribute getCreatureAttribute(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getCreatureAttribute();
    }

    public static ItemStack getHeldItemMainhand(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getHeldItemMainhand();
    }

    public static ItemStack getHeldItemOffhand(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getHeldItemOffhand();
    }

    public static ItemStack getHeldItem(@Nonnull PlayerEntity playerEntity, Hand hand) {
        return playerEntity.getHeldItem(hand);
    }

    public static void setHeldItem(@Nonnull PlayerEntity playerEntity, Hand hand, ItemStack stack) {
        playerEntity.setHeldItem(hand, stack);
    }

    public static boolean hasItemInSlot(@Nonnull PlayerEntity playerEntity, EquipmentSlotType slotIn) {
        return playerEntity.hasItemInSlot(slotIn);
    }

    public static float getArmorCoverPercentage(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getArmorCoverPercentage();
    }

    public static void setSprinting(@Nonnull PlayerEntity playerEntity, boolean sprinting) {
        playerEntity.setSprinting(sprinting);
    }

    public static void applyEntityCollision(@Nonnull PlayerEntity playerEntity, Entity entityIn) {
        playerEntity.applyEntityCollision(entityIn);
    }

    public static void setAIMoveSpeed(@Nonnull PlayerEntity playerEntity, float speedIn) {
        playerEntity.setAIMoveSpeed(speedIn);
    }

    public static boolean attackEntityAsMob(@Nonnull PlayerEntity playerEntity, Entity entityIn) {
        return playerEntity.attackEntityAsMob(entityIn);
    }

    public static void startSpinAttack(@Nonnull PlayerEntity playerEntity, int p_204803_1_) {
        playerEntity.startSpinAttack(p_204803_1_);
    }

    public static boolean isSpinAttacking(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.isSpinAttacking();
    }

    @OnlyIn(Dist.CLIENT)
    public static void setPositionAndRotationDirect(@Nonnull PlayerEntity playerEntity, double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
        playerEntity.setPositionAndRotationDirect(x, y, z, yaw, pitch, posRotationIncrements, teleport);
    }

    @OnlyIn(Dist.CLIENT)
    public static void setHeadRotation(@Nonnull PlayerEntity playerEntity, float yaw, int pitch) {
        playerEntity.setHeadRotation(yaw, pitch);
    }

    public static void setJumping(@Nonnull PlayerEntity playerEntity, boolean jumping) {
        playerEntity.setJumping(jumping);
    }

    public static void onItemPickup(@Nonnull PlayerEntity playerEntity, Entity entityIn, int quantity) {
        playerEntity.onItemPickup(entityIn, quantity);
    }

    public static boolean canEntityBeSeen(@Nonnull PlayerEntity playerEntity, Entity entityIn) {
        return playerEntity.canEntityBeSeen(entityIn);
    }

    public static float getYaw(@Nonnull PlayerEntity playerEntity, float partialTicks) {
        return playerEntity.getYaw(partialTicks);
    }

    @OnlyIn(Dist.CLIENT)
    public static float getSwingProgress(@Nonnull PlayerEntity playerEntity, float partialTickTime) {
        return playerEntity.getSwingProgress(partialTickTime);
    }

    public static boolean isServerWorld(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.isServerWorld();
    }

    public static boolean canBeCollidedWith(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.canBeCollidedWith();
    }

    public static boolean canBePushed(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.canBePushed();
    }

    public static float getRotationYawHead(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getRotationYawHead();
    }

    public static void setRotationYawHead(@Nonnull PlayerEntity playerEntity, float rotation) {
        playerEntity.setRotationYawHead(rotation);
    }

    public static void setRenderYawOffset(@Nonnull PlayerEntity playerEntity, float offset) {
        playerEntity.setRenderYawOffset(offset);
    }

    public static void sendEnterCombat(@Nonnull PlayerEntity playerEntity) {
        playerEntity.sendEnterCombat();
    }

    public static void sendEndCombat(@Nonnull PlayerEntity playerEntity) {
        playerEntity.sendEndCombat();
    }

    public static boolean isHandActive(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.isHandActive();
    }

    public static Hand getActiveHand(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getActiveHand();
    }

    public static void setActiveHand(@Nonnull PlayerEntity playerEntity, Hand hand) {
        playerEntity.setActiveHand(hand);
    }

    public static void notifyDataManagerChange(@Nonnull PlayerEntity playerEntity, DataParameter<?> key) {
        playerEntity.notifyDataManagerChange(key);
    }

    public static void lookAt(@Nonnull PlayerEntity playerEntity, EntityAnchorArgument.Type p_200602_1_, Vec3d p_200602_2_) {
        playerEntity.lookAt(p_200602_1_, p_200602_2_);
    }

    public static ItemStack getActiveItemStack(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getActiveItemStack();
    }

    public static int getItemInUseCount(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getItemInUseCount();
    }

    public static int getItemInUseMaxCount(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getItemInUseMaxCount();
    }

    public static void stopActiveHand(@Nonnull PlayerEntity playerEntity) {
        playerEntity.stopActiveHand();
    }

    public static void resetActiveHand(@Nonnull PlayerEntity playerEntity) {
        playerEntity.resetActiveHand();
    }

    public static boolean isActiveItemStackBlocking(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.isActiveItemStackBlocking();
    }

    public static boolean isSuppressingSlidingDownLadder(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.isSuppressingSlidingDownLadder();
    }

    public static boolean isElytraFlying(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.isElytraFlying();
    }

    public static boolean isActualySwimming(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.isActualySwimming();
    }

    @OnlyIn(Dist.CLIENT)
    public static int getTicksElytraFlying(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getTicksElytraFlying();
    }

    public static boolean attemptTeleport(@Nonnull PlayerEntity playerEntity, double p_213373_1_, double p_213373_3_, double p_213373_5_, boolean p_213373_7_) {
        return playerEntity.attemptTeleport(p_213373_1_, p_213373_3_, p_213373_5_, p_213373_7_);
    }

    public static boolean canBeHitWithPotion(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.canBeHitWithPotion();
    }

    public static boolean attackable(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.attackable();
    }

    @OnlyIn(Dist.CLIENT)
    public static void setPartying(@Nonnull PlayerEntity playerEntity, BlockPos pos, boolean isPartying) {
        playerEntity.setPartying(pos, isPartying);
    }

    public static IPacket<?> createSpawnPacket(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.createSpawnPacket();
    }

    public static Optional<BlockPos> getBedPosition(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getBedPosition();
    }

    public static void setBedPosition(@Nonnull PlayerEntity playerEntity, BlockPos p_213369_1_) {
        playerEntity.setBedPosition(p_213369_1_);
    }

    public static void clearBedPosition(@Nonnull PlayerEntity playerEntity) {
        playerEntity.clearBedPosition();
    }

    public static boolean isSleeping(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.isSleeping();
    }

    @OnlyIn(Dist.CLIENT)
    @Nullable
    public static Direction getBedDirection(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getBedDirection();
    }

    public static boolean isEntityInsideOpaqueBlock(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.isEntityInsideOpaqueBlock();
    }

    public static void sendBreakAnimation(@Nonnull PlayerEntity playerEntity, EquipmentSlotType p_213361_1_) {
        playerEntity.sendBreakAnimation(p_213361_1_);
    }

    public static void sendBreakAnimation(@Nonnull PlayerEntity playerEntity, Hand p_213334_1_) {
        playerEntity.sendBreakAnimation(p_213334_1_);
    }

    public static boolean curePotionEffects(@Nonnull PlayerEntity playerEntity, ItemStack curativeItem) {
        return playerEntity.curePotionEffects(curativeItem);
    }

    public static boolean shouldRiderFaceForward(@Nonnull PlayerEntity playerEntity, PlayerEntity player) {
        return playerEntity.shouldRiderFaceForward(player);
    }

    @OnlyIn(Dist.CLIENT)
    public static int getTeamColor(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getTeamColor();
    }

    public static void detach(@Nonnull PlayerEntity playerEntity) {
        playerEntity.detach();
    }

    public static void setPacketCoordinates(@Nonnull PlayerEntity playerEntity, double p_213312_1_, double p_213312_3_, double p_213312_5_) {
        playerEntity.setPacketCoordinates(p_213312_1_, p_213312_3_, p_213312_5_);
    }

    public static EntityType<?> getType(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getType();
    }

    public static int getEntityId(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getEntityId();
    }

    public static void setEntityId(@Nonnull PlayerEntity playerEntity, int id) {
        playerEntity.setEntityId(id);
    }

    public static Set<String> getTags(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getTags();
    }

    public static boolean addTag(@Nonnull PlayerEntity playerEntity, String tag) {
        return playerEntity.addTag(tag);
    }

    public static boolean removeTag(@Nonnull PlayerEntity playerEntity, String tag) {
        return playerEntity.removeTag(tag);
    }

    public static EntityDataManager getDataManager(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getDataManager();
    }

    public static void remove(@Nonnull PlayerEntity playerEntity) {
        playerEntity.remove();
    }

    public static Pose getPose(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getPose();
    }

    public static void setPosition(@Nonnull PlayerEntity playerEntity, double x, double y, double z) {
        playerEntity.setPosition(x, y, z);
    }

    @OnlyIn(Dist.CLIENT)
    public static void rotateTowards(@Nonnull PlayerEntity playerEntity, double yaw, double pitch) {
        playerEntity.rotateTowards(yaw, pitch);
    }

    public static void setFire(@Nonnull PlayerEntity playerEntity, int seconds) {
        playerEntity.setFire(seconds);
    }

    public static void setFireTimer(@Nonnull PlayerEntity playerEntity, int p_223308_1_) {
        playerEntity.setFireTimer(p_223308_1_);
    }

    public static int getFireTimer(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getFireTimer();
    }

    public static void extinguish(@Nonnull PlayerEntity playerEntity) {
        playerEntity.extinguish();
    }

    public static boolean isOffsetPositionInLiquid(@Nonnull PlayerEntity playerEntity, double x, double y, double z) {
        return playerEntity.isOffsetPositionInLiquid(x, y, z);
    }

    public static void move(@Nonnull PlayerEntity playerEntity, MoverType typeIn, Vec3d pos) {
        playerEntity.move(typeIn, pos);
    }

    public static void resetPositionToBB(@Nonnull PlayerEntity playerEntity) {
        playerEntity.resetPositionToBB();
    }

    public static boolean isSilent(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.isSilent();
    }

    public static void setSilent(@Nonnull PlayerEntity playerEntity, boolean isSilent) {
        playerEntity.setSilent(isSilent);
    }

    public static boolean hasNoGravity(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.hasNoGravity();
    }

    public static void setNoGravity(@Nonnull PlayerEntity playerEntity, boolean noGravity) {
        playerEntity.setNoGravity(noGravity);
    }

    @Nullable
    public static AxisAlignedBB getCollisionBoundingBox(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getCollisionBoundingBox();
    }

    public static boolean isImmuneToFire(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.isImmuneToFire();
    }

    public static boolean isInWater(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.isInWater();
    }

    public static boolean isWet(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.isWet();
    }

    public static boolean isInWaterRainOrBubbleColumn(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.isInWaterRainOrBubbleColumn();
    }

    public static boolean isInWaterOrBubbleColumn(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.isInWaterOrBubbleColumn();
    }

    public static boolean canSwim(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.canSwim();
    }

    public static boolean handleWaterMovement(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.handleWaterMovement();
    }

    public static void spawnRunningParticles(@Nonnull PlayerEntity playerEntity) {
        playerEntity.spawnRunningParticles();
    }

    public static boolean areEyesInFluid(@Nonnull PlayerEntity playerEntity, Tag<Fluid> tagIn) {
        return playerEntity.areEyesInFluid(tagIn);
    }

    public static boolean areEyesInFluid(@Nonnull PlayerEntity playerEntity, Tag<Fluid> p_213290_1_, boolean checkChunkLoaded) {
        return playerEntity.areEyesInFluid(p_213290_1_, checkChunkLoaded);
    }

    public static void setInLava(@Nonnull PlayerEntity playerEntity) {
        playerEntity.setInLava();
    }

    public static boolean isInLava(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.isInLava();
    }

    public static void moveRelative(@Nonnull PlayerEntity playerEntity, float p_213309_1_, Vec3d relative) {
        playerEntity.moveRelative(p_213309_1_, relative);
    }

    public static float getBrightness(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getBrightness();
    }

    public static void setWorld(@Nonnull PlayerEntity playerEntity, World worldIn) {
        playerEntity.setWorld(worldIn);
    }

    public static void setPositionAndRotation(@Nonnull PlayerEntity playerEntity, double x, double y, double z, float yaw, float pitch) {
        playerEntity.setPositionAndRotation(x, y, z, yaw, pitch);
    }

    public static void moveToBlockPosAndAngles(@Nonnull PlayerEntity playerEntity, BlockPos pos, float rotationYawIn, float rotationPitchIn) {
        playerEntity.moveToBlockPosAndAngles(pos, rotationYawIn, rotationPitchIn);
    }

    public static void setLocationAndAngles(@Nonnull PlayerEntity playerEntity, double x, double y, double z, float yaw, float pitch) {
        playerEntity.setLocationAndAngles(x, y, z, yaw, pitch);
    }

    public static void forceSetPosition(@Nonnull PlayerEntity playerEntity, double x, double y, double z) {
        playerEntity.forceSetPosition(x, y, z);
    }

    public static float getDistance(@Nonnull PlayerEntity playerEntity, Entity entityIn) {
        return playerEntity.getDistance(entityIn);
    }

    public static double getDistanceSq(@Nonnull PlayerEntity playerEntity, double x, double y, double z) {
        return playerEntity.getDistanceSq(x, y, z);
    }

    public static double getDistanceSq(@Nonnull PlayerEntity playerEntity, Entity entityIn) {
        return playerEntity.getDistanceSq(entityIn);
    }

    public static double getDistanceSq(@Nonnull PlayerEntity playerEntity, Vec3d p_195048_1_) {
        return playerEntity.getDistanceSq(p_195048_1_);
    }

    public static void onCollideWithPlayer(@Nonnull PlayerEntity playerEntity, PlayerEntity entityIn) {
        playerEntity.onCollideWithPlayer(entityIn);
    }

    public static void addVelocity(@Nonnull PlayerEntity playerEntity, double x, double y, double z) {
        playerEntity.addVelocity(x, y, z);
    }

    public static Vec3d getLook(@Nonnull PlayerEntity playerEntity, float partialTicks) {
        return playerEntity.getLook(partialTicks);
    }

    public static float getPitch(@Nonnull PlayerEntity playerEntity, float partialTicks) {
        return playerEntity.getPitch(partialTicks);
    }

    public static Vec3d getUpVector(@Nonnull PlayerEntity playerEntity, float partialTicks) {
        return playerEntity.getUpVector(partialTicks);
    }

    public static Vec3d getEyePosition(@Nonnull PlayerEntity playerEntity, float partialTicks) {
        return playerEntity.getEyePosition(partialTicks);
    }

    public static RayTraceResult pick(@Nonnull PlayerEntity playerEntity, double p_213324_1_, float p_213324_3_, boolean p_213324_4_) {
        return playerEntity.pick(p_213324_1_, p_213324_3_, p_213324_4_);
    }

    public static void awardKillScore(@Nonnull PlayerEntity playerEntity, Entity p_191956_1_, int p_191956_2_, DamageSource p_191956_3_) {
        playerEntity.awardKillScore(p_191956_1_, p_191956_2_, p_191956_3_);
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean isInRangeToRender3d(@Nonnull PlayerEntity playerEntity, double x, double y, double z) {
        return playerEntity.isInRangeToRender3d(x, y, z);
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean isInRangeToRenderDist(@Nonnull PlayerEntity playerEntity, double distance) {
        return playerEntity.isInRangeToRenderDist(distance);
    }

    public static boolean writeUnlessRemoved(@Nonnull PlayerEntity playerEntity, CompoundNBT compound) {
        return playerEntity.writeUnlessRemoved(compound);
    }

    public static boolean writeUnlessPassenger(@Nonnull PlayerEntity playerEntity, CompoundNBT compound) {
        return playerEntity.writeUnlessPassenger(compound);
    }

    public static CompoundNBT writeWithoutTypeId(@Nonnull PlayerEntity playerEntity, CompoundNBT compound) {
        return playerEntity.writeWithoutTypeId(compound);
    }

    public static void read(@Nonnull PlayerEntity playerEntity, CompoundNBT compound) {
        playerEntity.read(compound);
    }

    @Nullable
    public static String getEntityString(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getEntityString();
    }

    @Nullable
    public static ItemEntity entityDropItem(@Nonnull PlayerEntity playerEntity, IItemProvider itemIn) {
        return playerEntity.entityDropItem(itemIn);
    }

    @Nullable
    public static ItemEntity entityDropItem(@Nonnull PlayerEntity playerEntity, IItemProvider itemIn, int offset) {
        return playerEntity.entityDropItem(itemIn, offset);
    }

    @Nullable
    public static ItemEntity entityDropItem(@Nonnull PlayerEntity playerEntity, ItemStack stack) {
        return playerEntity.entityDropItem(stack);
    }

    @Nullable
    public static ItemEntity entityDropItem(@Nonnull PlayerEntity playerEntity, ItemStack stack, float offsetY) {
        return playerEntity.entityDropItem(stack, offsetY);
    }

    public static boolean processInitialInteract(@Nonnull PlayerEntity playerEntity, PlayerEntity player, Hand hand) {
        return playerEntity.processInitialInteract(player, hand);
    }

    @Nullable
    public static AxisAlignedBB getCollisionBox(@Nonnull PlayerEntity playerEntity, Entity entityIn) {
        return playerEntity.getCollisionBox(entityIn);
    }

    public static void updatePassenger(@Nonnull PlayerEntity playerEntity, Entity passenger) {
        playerEntity.updatePassenger(passenger);
    }

    public static void positionRider(@Nonnull PlayerEntity playerEntity, Entity p_226266_1_, Entity.IMoveCallback p_226266_2_) {
        playerEntity.positionRider(p_226266_1_, p_226266_2_);
    }

    @OnlyIn(Dist.CLIENT)
    public static void applyOrientationToEntity(@Nonnull PlayerEntity playerEntity, Entity entityToUpdate) {
        playerEntity.applyOrientationToEntity(entityToUpdate);
    }

    public static double getMountedYOffset(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getMountedYOffset();
    }

    public static boolean startRiding(@Nonnull PlayerEntity playerEntity, Entity entityIn) {
        return playerEntity.startRiding(entityIn);
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean isLiving(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.isLiving();
    }

    public static boolean startRiding(@Nonnull PlayerEntity playerEntity, Entity entityIn, boolean force) {
        return playerEntity.startRiding(entityIn, force);
    }

    public static void removePassengers(@Nonnull PlayerEntity playerEntity) {
        playerEntity.removePassengers();
    }

    public static float getCollisionBorderSize(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getCollisionBorderSize();
    }

    public static Vec3d getLookVec(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getLookVec();
    }

    public static Vec2f getPitchYaw(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getPitchYaw();
    }

    @OnlyIn(Dist.CLIENT)
    public static Vec3d getForward(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getForward();
    }

    public static void setPortal(@Nonnull PlayerEntity playerEntity, BlockPos pos) {
        playerEntity.setPortal(pos);
    }

    @OnlyIn(Dist.CLIENT)
    public static void setVelocity(@Nonnull PlayerEntity playerEntity, double x, double y, double z) {
        playerEntity.setVelocity(x, y, z);
    }

    public static Iterable<ItemStack> getEquipmentAndArmor(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getEquipmentAndArmor();
    }

    public static boolean isBurning(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.isBurning();
    }

    public static boolean isPassenger(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.isPassenger();
    }

    public static boolean isBeingRidden(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.isBeingRidden();
    }

    public static void setSneaking(@Nonnull PlayerEntity playerEntity, boolean keyDownIn) {
        playerEntity.setSneaking(keyDownIn);
    }

    public static boolean isSneaking(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.isSneaking();
    }

    public static boolean isSteppingCarefully(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.isSteppingCarefully();
    }

    public static boolean isSuppressingBounce(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.isSuppressingBounce();
    }

    public static boolean isDiscrete(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.isDiscrete();
    }

    public static boolean isDescending(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.isDescending();
    }

    public static boolean isCrouching(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.isCrouching();
    }

    public static boolean isSprinting(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.isSprinting();
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean isVisuallySwimming(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.isVisuallySwimming();
    }

    public static void setSwimming(@Nonnull PlayerEntity playerEntity, boolean p_204711_1_) {
        playerEntity.setSwimming(p_204711_1_);
    }

    public static boolean isGlowing(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.isGlowing();
    }

    public static void setGlowing(@Nonnull PlayerEntity playerEntity, boolean glowingIn) {
        playerEntity.setGlowing(glowingIn);
    }

    public static boolean isInvisible(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.isInvisible();
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean isInvisibleToPlayer(@Nonnull PlayerEntity playerEntity, PlayerEntity player) {
        return playerEntity.isInvisibleToPlayer(player);
    }

    @Nullable
    public static Team getTeam(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getTeam();
    }

    public static boolean isOnSameTeam(@Nonnull PlayerEntity playerEntity, Entity entityIn) {
        return playerEntity.isOnSameTeam(entityIn);
    }

    public static boolean isOnScoreboardTeam(@Nonnull PlayerEntity playerEntity, Team teamIn) {
        return playerEntity.isOnScoreboardTeam(teamIn);
    }

    public static void setInvisible(@Nonnull PlayerEntity playerEntity, boolean invisible) {
        playerEntity.setInvisible(invisible);
    }

    public static int getMaxAir(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getMaxAir();
    }

    public static int getAir(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getAir();
    }

    public static void setAir(@Nonnull PlayerEntity playerEntity, int air) {
        playerEntity.setAir(air);
    }

    public static void onStruckByLightning(@Nonnull PlayerEntity playerEntity, LightningBoltEntity lightningBolt) {
        playerEntity.onStruckByLightning(lightningBolt);
    }

    public static void onEnterBubbleColumnWithAirAbove(@Nonnull PlayerEntity playerEntity, boolean downwards) {
        playerEntity.onEnterBubbleColumnWithAirAbove(downwards);
    }

    public static void onEnterBubbleColumn(@Nonnull PlayerEntity playerEntity, boolean downwards) {
        playerEntity.onEnterBubbleColumn(downwards);
    }

    public static boolean isEntityEqual(@Nonnull PlayerEntity playerEntity, Entity entityIn) {
        return playerEntity.isEntityEqual(entityIn);
    }

    public static boolean canBeAttackedWithItem(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.canBeAttackedWithItem();
    }

    public static boolean hitByEntity(@Nonnull PlayerEntity playerEntity, Entity entityIn) {
        return playerEntity.hitByEntity(entityIn);
    }

    public static boolean isInvulnerable(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.isInvulnerable();
    }

    public static void setInvulnerable(@Nonnull PlayerEntity playerEntity, boolean isInvulnerable) {
        playerEntity.setInvulnerable(isInvulnerable);
    }

    public static void copyLocationAndAnglesFrom(@Nonnull PlayerEntity playerEntity, Entity entityIn) {
        playerEntity.copyLocationAndAnglesFrom(entityIn);
    }

    public static void copyDataFromOld(@Nonnull PlayerEntity playerEntity, Entity entityIn) {
        playerEntity.copyDataFromOld(entityIn);
    }

    @Nullable
    public static Entity changeDimension(@Nonnull PlayerEntity playerEntity, DimensionType destination) {
        return playerEntity.changeDimension(destination);
    }

    @Nullable
    public static Entity changeDimension(@Nonnull PlayerEntity playerEntity, DimensionType destination, ITeleporter teleporter) {
        return playerEntity.changeDimension(destination, teleporter);
    }

    public static boolean isNonBoss(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.isNonBoss();
    }

    public static float getExplosionResistance(@Nonnull PlayerEntity playerEntity, Explosion explosionIn, IBlockReader worldIn, BlockPos pos, BlockState blockStateIn, IFluidState p_180428_5_, float p_180428_6_) {
        return playerEntity.getExplosionResistance(explosionIn, worldIn, pos, blockStateIn, p_180428_5_, p_180428_6_);
    }

    public static boolean canExplosionDestroyBlock(@Nonnull PlayerEntity playerEntity, Explosion explosionIn, IBlockReader worldIn, BlockPos pos, BlockState blockStateIn, float p_174816_5_) {
        return playerEntity.canExplosionDestroyBlock(explosionIn, worldIn, pos, blockStateIn, p_174816_5_);
    }

    public static int getMaxFallHeight(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getMaxFallHeight();
    }

    public static Vec3d getLastPortalVec(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getLastPortalVec();
    }

    public static Direction getTeleportDirection(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getTeleportDirection();
    }

    public static boolean doesEntityNotTriggerPressurePlate(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.doesEntityNotTriggerPressurePlate();
    }

    public static void fillCrashReport(@Nonnull PlayerEntity playerEntity, CrashReportCategory category) {
        playerEntity.fillCrashReport(category);
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean canRenderOnFire(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.canRenderOnFire();
    }

    public static void setUniqueId(@Nonnull PlayerEntity playerEntity, UUID uniqueIdIn) {
        playerEntity.setUniqueId(uniqueIdIn);
    }

    public static UUID getUniqueID(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getUniqueID();
    }

    public static String getCachedUniqueIdString(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getCachedUniqueIdString();
    }

    public static void setCustomName(@Nonnull PlayerEntity playerEntity, @Nullable ITextComponent name) {
        playerEntity.setCustomName(name);
    }

    @Nullable
    public static ITextComponent getCustomName(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getCustomName();
    }

    public static boolean hasCustomName(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.hasCustomName();
    }

    public static void setCustomNameVisible(@Nonnull PlayerEntity playerEntity, boolean alwaysRenderNameTag) {
        playerEntity.setCustomNameVisible(alwaysRenderNameTag);
    }

    public static boolean isCustomNameVisible(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.isCustomNameVisible();
    }

    public static void teleportKeepLoaded(@Nonnull PlayerEntity playerEntity, double p_223102_1_, double p_223102_3_, double p_223102_5_) {
        playerEntity.teleportKeepLoaded(p_223102_1_, p_223102_3_, p_223102_5_);
    }

    public static void setPositionAndUpdate(@Nonnull PlayerEntity playerEntity, double x, double y, double z) {
        playerEntity.setPositionAndUpdate(x, y, z);
    }

    public static void recalculateSize(@Nonnull PlayerEntity playerEntity) {
        playerEntity.recalculateSize();
    }

    public static Direction getHorizontalFacing(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getHorizontalFacing();
    }

    public static Direction getAdjustedHorizontalFacing(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getAdjustedHorizontalFacing();
    }

    public static boolean isSpectatedByPlayer(@Nonnull PlayerEntity playerEntity, ServerPlayerEntity player) {
        return playerEntity.isSpectatedByPlayer(player);
    }

    public static AxisAlignedBB getBoundingBox(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getBoundingBox();
    }

    @OnlyIn(Dist.CLIENT)
    public static AxisAlignedBB getRenderBoundingBox(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getRenderBoundingBox();
    }

    public static void setBoundingBox(@Nonnull PlayerEntity playerEntity, AxisAlignedBB bb) {
        playerEntity.setBoundingBox(bb);
    }

    @OnlyIn(Dist.CLIENT)
    public static float getEyeHeight(@Nonnull PlayerEntity playerEntity, Pose p_213307_1_) {
        return playerEntity.getEyeHeight(p_213307_1_);
    }

    public static float getEyeHeight(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getEyeHeight();
    }

    public static void sendMessage(@Nonnull PlayerEntity playerEntity, ITextComponent component) {
        playerEntity.sendMessage(component);
    }

    public static BlockPos getPosition(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getPosition();
    }

    public static Vec3d getPositionVector(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getPositionVector();
    }

    public static World getEntityWorld(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getEntityWorld();
    }

    @Nullable
    public static MinecraftServer getServer(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getServer();
    }

    public static ActionResultType applyPlayerInteraction(@Nonnull PlayerEntity playerEntity, PlayerEntity player, Vec3d vec, Hand hand) {
        return playerEntity.applyPlayerInteraction(player, vec, hand);
    }

    public static boolean isImmuneToExplosions(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.isImmuneToExplosions();
    }

    public static void addTrackingPlayer(@Nonnull PlayerEntity playerEntity, ServerPlayerEntity player) {
        playerEntity.addTrackingPlayer(player);
    }

    public static void removeTrackingPlayer(@Nonnull PlayerEntity playerEntity, ServerPlayerEntity player) {
        playerEntity.removeTrackingPlayer(player);
    }

    public static float getRotatedYaw(@Nonnull PlayerEntity playerEntity, Rotation transformRotation) {
        return playerEntity.getRotatedYaw(transformRotation);
    }

    public static float getMirroredYaw(@Nonnull PlayerEntity playerEntity, Mirror transformMirror) {
        return playerEntity.getMirroredYaw(transformMirror);
    }

    public static boolean ignoreItemEntityData(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.ignoreItemEntityData();
    }

    public static boolean setPositionNonDirty(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.setPositionNonDirty();
    }

    @Nullable
    public static Entity getControllingPassenger(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getControllingPassenger();
    }

    public static List<Entity> getPassengers(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getPassengers();
    }

    public static boolean isPassenger(@Nonnull PlayerEntity playerEntity, Class<? extends Entity> p_205708_1_) {
        return playerEntity.isPassenger(p_205708_1_);
    }

    public static Collection<Entity> getRecursivePassengers(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getRecursivePassengers();
    }

    public static Stream<Entity> getSelfAndPassengers(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getSelfAndPassengers();
    }

    public static boolean isOnePlayerRiding(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.isOnePlayerRiding();
    }

    public static Entity getLowestRidingEntity(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getLowestRidingEntity();
    }

    public static boolean isRidingSameEntity(@Nonnull PlayerEntity playerEntity, Entity entityIn) {
        return playerEntity.isRidingSameEntity(entityIn);
    }

    public static boolean isRidingOrBeingRiddenBy(@Nonnull PlayerEntity playerEntity, Entity entityIn) {
        return playerEntity.isRidingOrBeingRiddenBy(entityIn);
    }

    public static void repositionDirectPassengers(@Nonnull PlayerEntity playerEntity, Entity.IMoveCallback p_226265_1_) {
        playerEntity.repositionDirectPassengers(p_226265_1_);
    }

    public static boolean canPassengerSteer(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.canPassengerSteer();
    }

    @Nullable
    public static Entity getRidingEntity(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getRidingEntity();
    }

    public static PushReaction getPushReaction(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getPushReaction();
    }

    public static CommandSource getCommandSource(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getCommandSource();
    }

    public static boolean hasPermissionLevel(@Nonnull PlayerEntity playerEntity, int p_211513_1_) {
        return playerEntity.hasPermissionLevel(p_211513_1_);
    }

    public static boolean shouldReceiveFeedback(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.shouldReceiveFeedback();
    }

    public static boolean shouldReceiveErrors(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.shouldReceiveErrors();
    }

    public static boolean allowLogging(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.allowLogging();
    }

    public static boolean handleFluidAcceleration(@Nonnull PlayerEntity playerEntity, Tag<Fluid> p_210500_1_) {
        return playerEntity.handleFluidAcceleration(p_210500_1_);
    }

    public static double getSubmergedHeight(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getSubmergedHeight();
    }

    public static float getWidth(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getWidth();
    }

    public static float getHeight(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getHeight();
    }

    public static Vec3d getPositionVec(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getPositionVec();
    }

    public static Vec3d getMotion(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getMotion();
    }

    public static void setMotion(@Nonnull PlayerEntity playerEntity, Vec3d motionIn) {
        playerEntity.setMotion(motionIn);
    }

    public static void setMotion(@Nonnull PlayerEntity playerEntity, double x, double y, double z) {
        playerEntity.setMotion(x, y, z);
    }

    public static double getPosX(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getPosX();
    }

    public static double getPosXWidth(@Nonnull PlayerEntity playerEntity, double p_226275_1_) {
        return playerEntity.getPosXWidth(p_226275_1_);
    }

    public static double getPosXRandom(@Nonnull PlayerEntity playerEntity, double p_226282_1_) {
        return playerEntity.getPosXRandom(p_226282_1_);
    }

    public static double getPosY(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getPosY();
    }

    public static double getPosYHeight(@Nonnull PlayerEntity playerEntity, double p_226283_1_) {
        return playerEntity.getPosYHeight(p_226283_1_);
    }

    public static double getPosYRandom(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getPosYRandom();
    }

    public static double getPosYEye(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getPosYEye();
    }

    public static double getPosZ(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getPosZ();
    }

    public static double getPosZWidth(@Nonnull PlayerEntity playerEntity, double p_226285_1_) {
        return playerEntity.getPosZWidth(p_226285_1_);
    }

    public static double getPosZRandom(@Nonnull PlayerEntity playerEntity, double p_226287_1_) {
        return playerEntity.getPosZRandom(p_226287_1_);
    }

    public static void setRawPosition(@Nonnull PlayerEntity playerEntity, double x, double y, double z) {
        playerEntity.setRawPosition(x, y, z);
    }

    public static void checkDespawn(@Nonnull PlayerEntity playerEntity) {
        playerEntity.checkDespawn();
    }

    public static void moveForced(@Nonnull PlayerEntity playerEntity, double p_225653_1_, double p_225653_3_, double p_225653_5_) {
        playerEntity.moveForced(p_225653_1_, p_225653_3_, p_225653_5_);
    }

    public static void canUpdate(@Nonnull PlayerEntity playerEntity, boolean value) {
        playerEntity.canUpdate(value);
    }

    public static boolean canUpdate(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.canUpdate();
    }

    public static Collection<ItemEntity> captureDrops(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.captureDrops();
    }

    public static Collection<ItemEntity> captureDrops(@Nonnull PlayerEntity playerEntity, Collection<ItemEntity> value) {
        return playerEntity.captureDrops(value);
    }

    public static CompoundNBT getPersistentData(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getPersistentData();
    }

    public static boolean canTrample(@Nonnull PlayerEntity playerEntity, BlockState state, BlockPos pos, float fallDistance) {
        return playerEntity.canTrample(state, pos, fallDistance);
    }

    public static boolean isAddedToWorld(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.isAddedToWorld();
    }

    public static void onAddedToWorld(@Nonnull PlayerEntity playerEntity) {
        playerEntity.onAddedToWorld();
    }

    public static void onRemovedFromWorld(@Nonnull PlayerEntity playerEntity) {
        playerEntity.onRemovedFromWorld();
    }

    public static void revive(@Nonnull PlayerEntity playerEntity) {
        playerEntity.revive();
    }

    public static boolean areCapsCompatible(@Nonnull PlayerEntity playerEntity, CapabilityProvider<Entity> other) {
        return playerEntity.areCapsCompatible(other);
    }

    public static boolean areCapsCompatible(@Nonnull PlayerEntity playerEntity, @Nullable CapabilityDispatcher other) {
        return playerEntity.areCapsCompatible(other);
    }

    @Nonnull
    public static <T> LazyOptional<T> getCapability(@Nonnull PlayerEntity playerEntity, @Nonnull Capability<T> cap) {
        return playerEntity.getCapability(cap);
    }

    public static Entity getEntity(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.getEntity();
    }

    public static void deserializeNBT(@Nonnull PlayerEntity playerEntity, CompoundNBT nbt) {
        playerEntity.deserializeNBT(nbt);
    }

    public static CompoundNBT serializeNBT(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.serializeNBT();
    }

    public static boolean shouldRiderSit(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.shouldRiderSit();
    }

    public static ItemStack getPickedResult(@Nonnull PlayerEntity playerEntity, RayTraceResult target) {
        return playerEntity.getPickedResult(target);
    }

    public static boolean canRiderInteract(@Nonnull PlayerEntity playerEntity) {
        return playerEntity.canRiderInteract();
    }

    public static EntityClassification getClassification(@Nonnull PlayerEntity playerEntity, boolean forSpawnCount) {
        return playerEntity.getClassification(forSpawnCount);
    }

    public static Brain<?> getBrain(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getBrain();
    }

    public static void onKillCommand(@Nonnull LivingEntity livingEntity) {
        livingEntity.onKillCommand();
    }

    public static boolean canAttack(@Nonnull LivingEntity livingEntity, EntityType<?> typeIn) {
        return livingEntity.canAttack(typeIn);
    }

    public static boolean canBreatheUnderwater(@Nonnull LivingEntity livingEntity) {
        return livingEntity.canBreatheUnderwater();
    }

    @OnlyIn(Dist.CLIENT)
    public static float getSwimAnimation(@Nonnull LivingEntity livingEntity, float partialTicks) {
        return livingEntity.getSwimAnimation(partialTicks);
    }

    public static void baseTick(@Nonnull LivingEntity livingEntity) {
        livingEntity.baseTick();
    }

    public static boolean isChild(@Nonnull LivingEntity livingEntity) {
        return livingEntity.isChild();
    }

    public static float getRenderScale(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getRenderScale();
    }

    public static boolean canBeRiddenInWater(@Nonnull LivingEntity livingEntity) {
        return livingEntity.canBeRiddenInWater();
    }

    public static Random getRNG(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getRNG();
    }

    @Nullable
    public static LivingEntity getRevengeTarget(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getRevengeTarget();
    }

    public static int getRevengeTimer(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getRevengeTimer();
    }

    public static void setRevengeTarget(@Nonnull LivingEntity livingEntity, @Nullable LivingEntity livingBase) {
        livingEntity.setRevengeTarget(livingBase);
    }

    @Nullable
    public static LivingEntity getLastAttackedEntity(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getLastAttackedEntity();
    }

    public static int getLastAttackedEntityTime(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getLastAttackedEntityTime();
    }

    public static void setLastAttackedEntity(@Nonnull LivingEntity livingEntity, Entity entityIn) {
        livingEntity.setLastAttackedEntity(entityIn);
    }

    public static int getIdleTime(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getIdleTime();
    }

    public static void setIdleTime(@Nonnull LivingEntity livingEntity, int idleTimeIn) {
        livingEntity.setIdleTime(idleTimeIn);
    }

    public static void writeAdditional(@Nonnull LivingEntity livingEntity, CompoundNBT compound) {
        livingEntity.writeAdditional(compound);
    }

    public static void readAdditional(@Nonnull LivingEntity livingEntity, CompoundNBT compound) {
        livingEntity.readAdditional(compound);
    }

    public static double getVisibilityMultiplier(@Nonnull LivingEntity livingEntity, @Nullable Entity lookingEntity) {
        return livingEntity.getVisibilityMultiplier(lookingEntity);
    }

    public static boolean canAttack(@Nonnull LivingEntity livingEntity, LivingEntity target) {
        return livingEntity.canAttack(target);
    }

    public static boolean canAttack(@Nonnull LivingEntity livingEntity, LivingEntity livingentityIn, EntityPredicate predicateIn) {
        return livingEntity.canAttack(livingentityIn, predicateIn);
    }

    public static boolean areAllPotionsAmbient(Collection<EffectInstance> potionEffects) {
        return LivingEntity.areAllPotionsAmbient(potionEffects);
    }

    public static boolean clearActivePotions(@Nonnull LivingEntity livingEntity) {
        return livingEntity.clearActivePotions();
    }

    public static Collection<EffectInstance> getActivePotionEffects(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getActivePotionEffects();
    }

    public static Map<Effect, EffectInstance> getActivePotionMap(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getActivePotionMap();
    }

    public static boolean isPotionActive(@Nonnull LivingEntity livingEntity, Effect potionIn) {
        return livingEntity.isPotionActive(potionIn);
    }

    @Nullable
    public static EffectInstance getActivePotionEffect(@Nonnull LivingEntity livingEntity, Effect potionIn) {
        return livingEntity.getActivePotionEffect(potionIn);
    }

    public static boolean addPotionEffect(@Nonnull LivingEntity livingEntity, EffectInstance effectInstanceIn) {
        return livingEntity.addPotionEffect(effectInstanceIn);
    }

    public static boolean isPotionApplicable(@Nonnull LivingEntity livingEntity, EffectInstance potioneffectIn) {
        return livingEntity.isPotionApplicable(potioneffectIn);
    }

    public static boolean isEntityUndead(@Nonnull LivingEntity livingEntity) {
        return livingEntity.isEntityUndead();
    }

    @Nullable
    public static EffectInstance removeActivePotionEffect(@Nonnull LivingEntity livingEntity, @Nullable Effect potioneffectin) {
        return livingEntity.removeActivePotionEffect(potioneffectin);
    }

    public static boolean removePotionEffect(@Nonnull LivingEntity livingEntity, Effect effectIn) {
        return livingEntity.removePotionEffect(effectIn);
    }

    public static void heal(@Nonnull LivingEntity livingEntity, float healAmount) {
        livingEntity.heal(healAmount);
    }

    public static float getHealth(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getHealth();
    }

    public static void setHealth(@Nonnull LivingEntity livingEntity, float health) {
        livingEntity.setHealth(health);
    }

    public static boolean attackEntityFrom(@Nonnull LivingEntity livingEntity, DamageSource source, float amount) {
        return livingEntity.attackEntityFrom(source, amount);
    }

    @Nullable
    public static DamageSource getLastDamageSource(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getLastDamageSource();
    }

    public static void onDeath(@Nonnull LivingEntity livingEntity, DamageSource cause) {
        livingEntity.onDeath(cause);
    }

    public static ResourceLocation getLootTableResourceLocation(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getLootTableResourceLocation();
    }

    public static void knockBack(@Nonnull LivingEntity livingEntity, Entity entityIn, float strength, double xRatio, double zRatio) {
        livingEntity.knockBack(entityIn, strength, xRatio, zRatio);
    }

    public static SoundEvent getEatSound(@Nonnull LivingEntity livingEntity, ItemStack itemStackIn) {
        return livingEntity.getEatSound(itemStackIn);
    }

    public static boolean isOnLadder(@Nonnull LivingEntity livingEntity) {
        return livingEntity.isOnLadder();
    }

    public static BlockState getBlockState(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getBlockState();
    }

    public static boolean isAlive(@Nonnull LivingEntity livingEntity) {
        return livingEntity.isAlive();
    }

    public static boolean onLivingFall(@Nonnull LivingEntity livingEntity, float distance, float damageMultiplier) {
        return livingEntity.onLivingFall(distance, damageMultiplier);
    }

    @OnlyIn(Dist.CLIENT)
    public static void performHurtAnimation(@Nonnull LivingEntity livingEntity) {
        livingEntity.performHurtAnimation();
    }

    public static int getTotalArmorValue(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getTotalArmorValue();
    }

    public static CombatTracker getCombatTracker(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getCombatTracker();
    }

    @Nullable
    public static LivingEntity getAttackingEntity(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getAttackingEntity();
    }

    public static float getMaxHealth(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getMaxHealth();
    }

    public static int getArrowCountInEntity(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getArrowCountInEntity();
    }

    public static void setArrowCountInEntity(@Nonnull LivingEntity livingEntity, int count) {
        livingEntity.setArrowCountInEntity(count);
    }

    public static int getBeeStingCount(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getBeeStingCount();
    }

    public static void setBeeStingCount(@Nonnull LivingEntity livingEntity, int p_226300_1_) {
        livingEntity.setBeeStingCount(p_226300_1_);
    }

    public static void swingArm(@Nonnull LivingEntity livingEntity, Hand hand) {
        livingEntity.swingArm(hand);
    }

    public static void swing(@Nonnull LivingEntity livingEntity, Hand handIn, boolean p_226292_2_) {
        livingEntity.swing(handIn, p_226292_2_);
    }

    @OnlyIn(Dist.CLIENT)
    public static void handleStatusUpdate(@Nonnull LivingEntity livingEntity, byte id) {
        livingEntity.handleStatusUpdate(id);
    }

    public static IAttributeInstance getAttribute(@Nonnull LivingEntity livingEntity, IAttribute attribute) {
        return livingEntity.getAttribute(attribute);
    }

    public static AbstractAttributeMap getAttributes(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getAttributes();
    }

    public static CreatureAttribute getCreatureAttribute(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getCreatureAttribute();
    }

    public static ItemStack getHeldItemMainhand(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getHeldItemMainhand();
    }

    public static ItemStack getHeldItemOffhand(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getHeldItemOffhand();
    }

    public static ItemStack getHeldItem(@Nonnull LivingEntity livingEntity, Hand hand) {
        return livingEntity.getHeldItem(hand);
    }

    public static void setHeldItem(@Nonnull LivingEntity livingEntity, Hand hand, ItemStack stack) {
        livingEntity.setHeldItem(hand, stack);
    }

    public static boolean hasItemInSlot(@Nonnull LivingEntity livingEntity, EquipmentSlotType slotIn) {
        return livingEntity.hasItemInSlot(slotIn);
    }

    public static Iterable<ItemStack> getArmorInventoryList(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getArmorInventoryList();
    }

    public static ItemStack getItemStackFromSlot(@Nonnull LivingEntity livingEntity, EquipmentSlotType slotIn) {
        return livingEntity.getItemStackFromSlot(slotIn);
    }

    public static void setItemStackToSlot(@Nonnull LivingEntity livingEntity, EquipmentSlotType slotIn, ItemStack stack) {
        livingEntity.setItemStackToSlot(slotIn, stack);
    }

    public static float getArmorCoverPercentage(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getArmorCoverPercentage();
    }

    public static void setSprinting(@Nonnull LivingEntity livingEntity, boolean sprinting) {
        livingEntity.setSprinting(sprinting);
    }

    public static void applyEntityCollision(@Nonnull LivingEntity livingEntity, Entity entityIn) {
        livingEntity.applyEntityCollision(entityIn);
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean getAlwaysRenderNameTagForRender(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getAlwaysRenderNameTagForRender();
    }

    public static void travel(@Nonnull LivingEntity livingEntity, Vec3d p_213352_1_) {
        livingEntity.travel(p_213352_1_);
    }

    public static float getAIMoveSpeed(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getAIMoveSpeed();
    }

    public static void setAIMoveSpeed(@Nonnull LivingEntity livingEntity, float speedIn) {
        livingEntity.setAIMoveSpeed(speedIn);
    }

    public static boolean attackEntityAsMob(@Nonnull LivingEntity livingEntity, Entity entityIn) {
        return livingEntity.attackEntityAsMob(entityIn);
    }

    public static void tick(@Nonnull LivingEntity livingEntity) {
        livingEntity.tick();
    }

    public static void livingTick(@Nonnull LivingEntity livingEntity) {
        livingEntity.livingTick();
    }

    public static void startSpinAttack(@Nonnull LivingEntity livingEntity, int p_204803_1_) {
        livingEntity.startSpinAttack(p_204803_1_);
    }

    public static boolean isSpinAttacking(@Nonnull LivingEntity livingEntity) {
        return livingEntity.isSpinAttacking();
    }

    public static void stopRiding(@Nonnull LivingEntity livingEntity) {
        livingEntity.stopRiding();
    }

    public static void updateRidden(@Nonnull LivingEntity livingEntity) {
        livingEntity.updateRidden();
    }

    @OnlyIn(Dist.CLIENT)
    public static void setPositionAndRotationDirect(@Nonnull LivingEntity livingEntity, double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
        livingEntity.setPositionAndRotationDirect(x, y, z, yaw, pitch, posRotationIncrements, teleport);
    }

    @OnlyIn(Dist.CLIENT)
    public static void setHeadRotation(@Nonnull LivingEntity livingEntity, float yaw, int pitch) {
        livingEntity.setHeadRotation(yaw, pitch);
    }

    public static void setJumping(@Nonnull LivingEntity livingEntity, boolean jumping) {
        livingEntity.setJumping(jumping);
    }

    public static void onItemPickup(@Nonnull LivingEntity livingEntity, Entity entityIn, int quantity) {
        livingEntity.onItemPickup(entityIn, quantity);
    }

    public static boolean canEntityBeSeen(@Nonnull LivingEntity livingEntity, Entity entityIn) {
        return livingEntity.canEntityBeSeen(entityIn);
    }

    public static float getYaw(@Nonnull LivingEntity livingEntity, float partialTicks) {
        return livingEntity.getYaw(partialTicks);
    }

    @OnlyIn(Dist.CLIENT)
    public static float getSwingProgress(@Nonnull LivingEntity livingEntity, float partialTickTime) {
        return livingEntity.getSwingProgress(partialTickTime);
    }

    public static boolean isServerWorld(@Nonnull LivingEntity livingEntity) {
        return livingEntity.isServerWorld();
    }

    public static boolean canBeCollidedWith(@Nonnull LivingEntity livingEntity) {
        return livingEntity.canBeCollidedWith();
    }

    public static boolean canBePushed(@Nonnull LivingEntity livingEntity) {
        return livingEntity.canBePushed();
    }

    public static float getRotationYawHead(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getRotationYawHead();
    }

    public static void setRotationYawHead(@Nonnull LivingEntity livingEntity, float rotation) {
        livingEntity.setRotationYawHead(rotation);
    }

    public static void setRenderYawOffset(@Nonnull LivingEntity livingEntity, float offset) {
        livingEntity.setRenderYawOffset(offset);
    }

    public static float getAbsorptionAmount(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getAbsorptionAmount();
    }

    public static void setAbsorptionAmount(@Nonnull LivingEntity livingEntity, float amount) {
        livingEntity.setAbsorptionAmount(amount);
    }

    public static void sendEnterCombat(@Nonnull LivingEntity livingEntity) {
        livingEntity.sendEnterCombat();
    }

    public static void sendEndCombat(@Nonnull LivingEntity livingEntity) {
        livingEntity.sendEndCombat();
    }

    public static HandSide getPrimaryHand(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getPrimaryHand();
    }

    public static boolean isHandActive(@Nonnull LivingEntity livingEntity) {
        return livingEntity.isHandActive();
    }

    public static Hand getActiveHand(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getActiveHand();
    }

    public static void setActiveHand(@Nonnull LivingEntity livingEntity, Hand hand) {
        livingEntity.setActiveHand(hand);
    }

    public static void notifyDataManagerChange(@Nonnull LivingEntity livingEntity, DataParameter<?> key) {
        livingEntity.notifyDataManagerChange(key);
    }

    public static void lookAt(@Nonnull LivingEntity livingEntity, EntityAnchorArgument.Type p_200602_1_, Vec3d p_200602_2_) {
        livingEntity.lookAt(p_200602_1_, p_200602_2_);
    }

    public static ItemStack getActiveItemStack(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getActiveItemStack();
    }

    public static int getItemInUseCount(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getItemInUseCount();
    }

    public static int getItemInUseMaxCount(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getItemInUseMaxCount();
    }

    public static void stopActiveHand(@Nonnull LivingEntity livingEntity) {
        livingEntity.stopActiveHand();
    }

    public static void resetActiveHand(@Nonnull LivingEntity livingEntity) {
        livingEntity.resetActiveHand();
    }

    public static boolean isActiveItemStackBlocking(@Nonnull LivingEntity livingEntity) {
        return livingEntity.isActiveItemStackBlocking();
    }

    public static boolean isSuppressingSlidingDownLadder(@Nonnull LivingEntity livingEntity) {
        return livingEntity.isSuppressingSlidingDownLadder();
    }

    public static boolean isElytraFlying(@Nonnull LivingEntity livingEntity) {
        return livingEntity.isElytraFlying();
    }

    public static boolean isActualySwimming(@Nonnull LivingEntity livingEntity) {
        return livingEntity.isActualySwimming();
    }

    @OnlyIn(Dist.CLIENT)
    public static int getTicksElytraFlying(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getTicksElytraFlying();
    }

    public static boolean attemptTeleport(@Nonnull LivingEntity livingEntity, double p_213373_1_, double p_213373_3_, double p_213373_5_, boolean p_213373_7_) {
        return livingEntity.attemptTeleport(p_213373_1_, p_213373_3_, p_213373_5_, p_213373_7_);
    }

    public static boolean canBeHitWithPotion(@Nonnull LivingEntity livingEntity) {
        return livingEntity.canBeHitWithPotion();
    }

    public static boolean attackable(@Nonnull LivingEntity livingEntity) {
        return livingEntity.attackable();
    }

    @OnlyIn(Dist.CLIENT)
    public static void setPartying(@Nonnull LivingEntity livingEntity, BlockPos pos, boolean isPartying) {
        livingEntity.setPartying(pos, isPartying);
    }

    public static boolean canPickUpItem(@Nonnull LivingEntity livingEntity, ItemStack itemstackIn) {
        return livingEntity.canPickUpItem(itemstackIn);
    }

    public static IPacket<?> createSpawnPacket(@Nonnull LivingEntity livingEntity) {
        return livingEntity.createSpawnPacket();
    }

    public static EntitySize getSize(@Nonnull LivingEntity livingEntity, Pose poseIn) {
        return livingEntity.getSize(poseIn);
    }

    public static Optional<BlockPos> getBedPosition(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getBedPosition();
    }

    public static void setBedPosition(@Nonnull LivingEntity livingEntity, BlockPos p_213369_1_) {
        livingEntity.setBedPosition(p_213369_1_);
    }

    public static void clearBedPosition(@Nonnull LivingEntity livingEntity) {
        livingEntity.clearBedPosition();
    }

    public static boolean isSleeping(@Nonnull LivingEntity livingEntity) {
        return livingEntity.isSleeping();
    }

    public static void startSleeping(@Nonnull LivingEntity livingEntity, BlockPos pos) {
        livingEntity.startSleeping(pos);
    }

    public static void wakeUp(@Nonnull LivingEntity livingEntity) {
        livingEntity.wakeUp();
    }

    @OnlyIn(Dist.CLIENT)
    @Nullable
    public static Direction getBedDirection(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getBedDirection();
    }

    public static boolean isEntityInsideOpaqueBlock(@Nonnull LivingEntity livingEntity) {
        return livingEntity.isEntityInsideOpaqueBlock();
    }

    public static ItemStack findAmmo(@Nonnull LivingEntity livingEntity, ItemStack shootable) {
        return livingEntity.findAmmo(shootable);
    }

    public static ItemStack onFoodEaten(@Nonnull LivingEntity livingEntity, World p_213357_1_, ItemStack p_213357_2_) {
        return livingEntity.onFoodEaten(p_213357_1_, p_213357_2_);
    }

    public static void sendBreakAnimation(@Nonnull LivingEntity livingEntity, EquipmentSlotType p_213361_1_) {
        livingEntity.sendBreakAnimation(p_213361_1_);
    }

    public static void sendBreakAnimation(@Nonnull LivingEntity livingEntity, Hand p_213334_1_) {
        livingEntity.sendBreakAnimation(p_213334_1_);
    }

    public static boolean curePotionEffects(@Nonnull LivingEntity livingEntity, ItemStack curativeItem) {
        return livingEntity.curePotionEffects(curativeItem);
    }

    public static boolean shouldRiderFaceForward(@Nonnull LivingEntity livingEntity, PlayerEntity player) {
        return livingEntity.shouldRiderFaceForward(player);
    }

    public static <T> LazyOptional<T> getCapability(@Nonnull LivingEntity livingEntity, Capability<T> capability, @Nullable Direction facing) {
        return livingEntity.getCapability(capability, facing);
    }

    public static void remove(@Nonnull LivingEntity livingEntity, boolean keepData) {
        livingEntity.remove(keepData);
    }

    @OnlyIn(Dist.CLIENT)
    public static int getTeamColor(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getTeamColor();
    }

    public static boolean isSpectator(@Nonnull LivingEntity livingEntity) {
        return livingEntity.isSpectator();
    }

    public static void detach(@Nonnull LivingEntity livingEntity) {
        livingEntity.detach();
    }

    public static void setPacketCoordinates(@Nonnull LivingEntity livingEntity, double p_213312_1_, double p_213312_3_, double p_213312_5_) {
        livingEntity.setPacketCoordinates(p_213312_1_, p_213312_3_, p_213312_5_);
    }

    public static EntityType<?> getType(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getType();
    }

    public static int getEntityId(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getEntityId();
    }

    public static void setEntityId(@Nonnull LivingEntity livingEntity, int id) {
        livingEntity.setEntityId(id);
    }

    public static Set<String> getTags(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getTags();
    }

    public static boolean addTag(@Nonnull LivingEntity livingEntity, String tag) {
        return livingEntity.addTag(tag);
    }

    public static boolean removeTag(@Nonnull LivingEntity livingEntity, String tag) {
        return livingEntity.removeTag(tag);
    }

    public static EntityDataManager getDataManager(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getDataManager();
    }

    public static void remove(@Nonnull LivingEntity livingEntity) {
        livingEntity.remove();
    }

    public static Pose getPose(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getPose();
    }

    public static void setPosition(@Nonnull LivingEntity livingEntity, double x, double y, double z) {
        livingEntity.setPosition(x, y, z);
    }

    @OnlyIn(Dist.CLIENT)
    public static void rotateTowards(@Nonnull LivingEntity livingEntity, double yaw, double pitch) {
        livingEntity.rotateTowards(yaw, pitch);
    }

    public static int getMaxInPortalTime(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getMaxInPortalTime();
    }

    public static void setFire(@Nonnull LivingEntity livingEntity, int seconds) {
        livingEntity.setFire(seconds);
    }

    public static void setFireTimer(@Nonnull LivingEntity livingEntity, int p_223308_1_) {
        livingEntity.setFireTimer(p_223308_1_);
    }

    public static int getFireTimer(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getFireTimer();
    }

    public static void extinguish(@Nonnull LivingEntity livingEntity) {
        livingEntity.extinguish();
    }

    public static boolean isOffsetPositionInLiquid(@Nonnull LivingEntity livingEntity, double x, double y, double z) {
        return livingEntity.isOffsetPositionInLiquid(x, y, z);
    }

    public static void move(@Nonnull LivingEntity livingEntity, MoverType typeIn, Vec3d pos) {
        livingEntity.move(typeIn, pos);
    }

    public static void resetPositionToBB(@Nonnull LivingEntity livingEntity) {
        livingEntity.resetPositionToBB();
    }

    public static void playSound(@Nonnull LivingEntity livingEntity, SoundEvent soundIn, float volume, float pitch) {
        livingEntity.playSound(soundIn, volume, pitch);
    }

    public static boolean isSilent(@Nonnull LivingEntity livingEntity) {
        return livingEntity.isSilent();
    }

    public static void setSilent(@Nonnull LivingEntity livingEntity, boolean isSilent) {
        livingEntity.setSilent(isSilent);
    }

    public static boolean hasNoGravity(@Nonnull LivingEntity livingEntity) {
        return livingEntity.hasNoGravity();
    }

    public static void setNoGravity(@Nonnull LivingEntity livingEntity, boolean noGravity) {
        livingEntity.setNoGravity(noGravity);
    }

    @Nullable
    public static AxisAlignedBB getCollisionBoundingBox(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getCollisionBoundingBox();
    }

    public static boolean isImmuneToFire(@Nonnull LivingEntity livingEntity) {
        return livingEntity.isImmuneToFire();
    }

    public static boolean isInWater(@Nonnull LivingEntity livingEntity) {
        return livingEntity.isInWater();
    }

    public static boolean isWet(@Nonnull LivingEntity livingEntity) {
        return livingEntity.isWet();
    }

    public static boolean isInWaterRainOrBubbleColumn(@Nonnull LivingEntity livingEntity) {
        return livingEntity.isInWaterRainOrBubbleColumn();
    }

    public static boolean isInWaterOrBubbleColumn(@Nonnull LivingEntity livingEntity) {
        return livingEntity.isInWaterOrBubbleColumn();
    }

    public static boolean canSwim(@Nonnull LivingEntity livingEntity) {
        return livingEntity.canSwim();
    }

    public static void updateSwimming(@Nonnull LivingEntity livingEntity) {
        livingEntity.updateSwimming();
    }

    public static boolean handleWaterMovement(@Nonnull LivingEntity livingEntity) {
        return livingEntity.handleWaterMovement();
    }

    public static void spawnRunningParticles(@Nonnull LivingEntity livingEntity) {
        livingEntity.spawnRunningParticles();
    }

    public static boolean areEyesInFluid(@Nonnull LivingEntity livingEntity, Tag<Fluid> tagIn) {
        return livingEntity.areEyesInFluid(tagIn);
    }

    public static boolean areEyesInFluid(@Nonnull LivingEntity livingEntity, Tag<Fluid> p_213290_1_, boolean checkChunkLoaded) {
        return livingEntity.areEyesInFluid(p_213290_1_, checkChunkLoaded);
    }

    public static void setInLava(@Nonnull LivingEntity livingEntity) {
        livingEntity.setInLava();
    }

    public static boolean isInLava(@Nonnull LivingEntity livingEntity) {
        return livingEntity.isInLava();
    }

    public static void moveRelative(@Nonnull LivingEntity livingEntity, float p_213309_1_, Vec3d relative) {
        livingEntity.moveRelative(p_213309_1_, relative);
    }

    public static float getBrightness(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getBrightness();
    }

    public static void setWorld(@Nonnull LivingEntity livingEntity, World worldIn) {
        livingEntity.setWorld(worldIn);
    }

    public static void setPositionAndRotation(@Nonnull LivingEntity livingEntity, double x, double y, double z, float yaw, float pitch) {
        livingEntity.setPositionAndRotation(x, y, z, yaw, pitch);
    }

    public static void moveToBlockPosAndAngles(@Nonnull LivingEntity livingEntity, BlockPos pos, float rotationYawIn, float rotationPitchIn) {
        livingEntity.moveToBlockPosAndAngles(pos, rotationYawIn, rotationPitchIn);
    }

    public static void setLocationAndAngles(@Nonnull LivingEntity livingEntity, double x, double y, double z, float yaw, float pitch) {
        livingEntity.setLocationAndAngles(x, y, z, yaw, pitch);
    }

    public static void forceSetPosition(@Nonnull LivingEntity livingEntity, double x, double y, double z) {
        livingEntity.forceSetPosition(x, y, z);
    }

    public static float getDistance(@Nonnull LivingEntity livingEntity, Entity entityIn) {
        return livingEntity.getDistance(entityIn);
    }

    public static double getDistanceSq(@Nonnull LivingEntity livingEntity, double x, double y, double z) {
        return livingEntity.getDistanceSq(x, y, z);
    }

    public static double getDistanceSq(@Nonnull LivingEntity livingEntity, Entity entityIn) {
        return livingEntity.getDistanceSq(entityIn);
    }

    public static double getDistanceSq(@Nonnull LivingEntity livingEntity, Vec3d p_195048_1_) {
        return livingEntity.getDistanceSq(p_195048_1_);
    }

    public static void onCollideWithPlayer(@Nonnull LivingEntity livingEntity, PlayerEntity entityIn) {
        livingEntity.onCollideWithPlayer(entityIn);
    }

    public static void addVelocity(@Nonnull LivingEntity livingEntity, double x, double y, double z) {
        livingEntity.addVelocity(x, y, z);
    }

    public static Vec3d getLook(@Nonnull LivingEntity livingEntity, float partialTicks) {
        return livingEntity.getLook(partialTicks);
    }

    public static float getPitch(@Nonnull LivingEntity livingEntity, float partialTicks) {
        return livingEntity.getPitch(partialTicks);
    }

    public static Vec3d getUpVector(@Nonnull LivingEntity livingEntity, float partialTicks) {
        return livingEntity.getUpVector(partialTicks);
    }

    public static Vec3d getEyePosition(@Nonnull LivingEntity livingEntity, float partialTicks) {
        return livingEntity.getEyePosition(partialTicks);
    }

    public static RayTraceResult pick(@Nonnull LivingEntity livingEntity, double p_213324_1_, float p_213324_3_, boolean p_213324_4_) {
        return livingEntity.pick(p_213324_1_, p_213324_3_, p_213324_4_);
    }

    public static void awardKillScore(@Nonnull LivingEntity livingEntity, Entity p_191956_1_, int p_191956_2_, DamageSource p_191956_3_) {
        livingEntity.awardKillScore(p_191956_1_, p_191956_2_, p_191956_3_);
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean isInRangeToRender3d(@Nonnull LivingEntity livingEntity, double x, double y, double z) {
        return livingEntity.isInRangeToRender3d(x, y, z);
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean isInRangeToRenderDist(@Nonnull LivingEntity livingEntity, double distance) {
        return livingEntity.isInRangeToRenderDist(distance);
    }

    public static boolean writeUnlessRemoved(@Nonnull LivingEntity livingEntity, CompoundNBT compound) {
        return livingEntity.writeUnlessRemoved(compound);
    }

    public static boolean writeUnlessPassenger(@Nonnull LivingEntity livingEntity, CompoundNBT compound) {
        return livingEntity.writeUnlessPassenger(compound);
    }

    public static CompoundNBT writeWithoutTypeId(@Nonnull LivingEntity livingEntity, CompoundNBT compound) {
        return livingEntity.writeWithoutTypeId(compound);
    }

    public static void read(@Nonnull LivingEntity livingEntity, CompoundNBT compound) {
        livingEntity.read(compound);
    }

    @Nullable
    public static String getEntityString(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getEntityString();
    }

    @Nullable
    public static ItemEntity entityDropItem(@Nonnull LivingEntity livingEntity, IItemProvider itemIn) {
        return livingEntity.entityDropItem(itemIn);
    }

    @Nullable
    public static ItemEntity entityDropItem(@Nonnull LivingEntity livingEntity, IItemProvider itemIn, int offset) {
        return livingEntity.entityDropItem(itemIn, offset);
    }

    @Nullable
    public static ItemEntity entityDropItem(@Nonnull LivingEntity livingEntity, ItemStack stack) {
        return livingEntity.entityDropItem(stack);
    }

    @Nullable
    public static ItemEntity entityDropItem(@Nonnull LivingEntity livingEntity, ItemStack stack, float offsetY) {
        return livingEntity.entityDropItem(stack, offsetY);
    }

    public static boolean processInitialInteract(@Nonnull LivingEntity livingEntity, PlayerEntity player, Hand hand) {
        return livingEntity.processInitialInteract(player, hand);
    }

    @Nullable
    public static AxisAlignedBB getCollisionBox(@Nonnull LivingEntity livingEntity, Entity entityIn) {
        return livingEntity.getCollisionBox(entityIn);
    }

    public static void updatePassenger(@Nonnull LivingEntity livingEntity, Entity passenger) {
        livingEntity.updatePassenger(passenger);
    }

    public static void positionRider(@Nonnull LivingEntity livingEntity, Entity p_226266_1_, Entity.IMoveCallback p_226266_2_) {
        livingEntity.positionRider(p_226266_1_, p_226266_2_);
    }

    @OnlyIn(Dist.CLIENT)
    public static void applyOrientationToEntity(@Nonnull LivingEntity livingEntity, Entity entityToUpdate) {
        livingEntity.applyOrientationToEntity(entityToUpdate);
    }

    public static double getYOffset(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getYOffset();
    }

    public static double getMountedYOffset(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getMountedYOffset();
    }

    public static boolean startRiding(@Nonnull LivingEntity livingEntity, Entity entityIn) {
        return livingEntity.startRiding(entityIn);
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean isLiving(@Nonnull LivingEntity livingEntity) {
        return livingEntity.isLiving();
    }

    public static boolean startRiding(@Nonnull LivingEntity livingEntity, Entity entityIn, boolean force) {
        return livingEntity.startRiding(entityIn, force);
    }

    public static void removePassengers(@Nonnull LivingEntity livingEntity) {
        livingEntity.removePassengers();
    }

    public static float getCollisionBorderSize(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getCollisionBorderSize();
    }

    public static Vec3d getLookVec(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getLookVec();
    }

    public static Vec2f getPitchYaw(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getPitchYaw();
    }

    @OnlyIn(Dist.CLIENT)
    public static Vec3d getForward(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getForward();
    }

    public static void setPortal(@Nonnull LivingEntity livingEntity, BlockPos pos) {
        livingEntity.setPortal(pos);
    }

    public static int getPortalCooldown(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getPortalCooldown();
    }

    @OnlyIn(Dist.CLIENT)
    public static void setVelocity(@Nonnull LivingEntity livingEntity, double x, double y, double z) {
        livingEntity.setVelocity(x, y, z);
    }

    public static Iterable<ItemStack> getHeldEquipment(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getHeldEquipment();
    }

    public static Iterable<ItemStack> getEquipmentAndArmor(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getEquipmentAndArmor();
    }

    public static boolean isBurning(@Nonnull LivingEntity livingEntity) {
        return livingEntity.isBurning();
    }

    public static boolean isPassenger(@Nonnull LivingEntity livingEntity) {
        return livingEntity.isPassenger();
    }

    public static boolean isBeingRidden(@Nonnull LivingEntity livingEntity) {
        return livingEntity.isBeingRidden();
    }

    public static void setSneaking(@Nonnull LivingEntity livingEntity, boolean keyDownIn) {
        livingEntity.setSneaking(keyDownIn);
    }

    public static boolean isSneaking(@Nonnull LivingEntity livingEntity) {
        return livingEntity.isSneaking();
    }

    public static boolean isSteppingCarefully(@Nonnull LivingEntity livingEntity) {
        return livingEntity.isSteppingCarefully();
    }

    public static boolean isSuppressingBounce(@Nonnull LivingEntity livingEntity) {
        return livingEntity.isSuppressingBounce();
    }

    public static boolean isDiscrete(@Nonnull LivingEntity livingEntity) {
        return livingEntity.isDiscrete();
    }

    public static boolean isDescending(@Nonnull LivingEntity livingEntity) {
        return livingEntity.isDescending();
    }

    public static boolean isCrouching(@Nonnull LivingEntity livingEntity) {
        return livingEntity.isCrouching();
    }

    public static boolean isSprinting(@Nonnull LivingEntity livingEntity) {
        return livingEntity.isSprinting();
    }

    public static boolean isSwimming(@Nonnull LivingEntity livingEntity) {
        return livingEntity.isSwimming();
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean isVisuallySwimming(@Nonnull LivingEntity livingEntity) {
        return livingEntity.isVisuallySwimming();
    }

    public static void setSwimming(@Nonnull LivingEntity livingEntity, boolean p_204711_1_) {
        livingEntity.setSwimming(p_204711_1_);
    }

    public static boolean isGlowing(@Nonnull LivingEntity livingEntity) {
        return livingEntity.isGlowing();
    }

    public static void setGlowing(@Nonnull LivingEntity livingEntity, boolean glowingIn) {
        livingEntity.setGlowing(glowingIn);
    }

    public static boolean isInvisible(@Nonnull LivingEntity livingEntity) {
        return livingEntity.isInvisible();
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean isInvisibleToPlayer(@Nonnull LivingEntity livingEntity, PlayerEntity player) {
        return livingEntity.isInvisibleToPlayer(player);
    }

    @Nullable
    public static Team getTeam(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getTeam();
    }

    public static boolean isOnSameTeam(@Nonnull LivingEntity livingEntity, Entity entityIn) {
        return livingEntity.isOnSameTeam(entityIn);
    }

    public static boolean isOnScoreboardTeam(@Nonnull LivingEntity livingEntity, Team teamIn) {
        return livingEntity.isOnScoreboardTeam(teamIn);
    }

    public static void setInvisible(@Nonnull LivingEntity livingEntity, boolean invisible) {
        livingEntity.setInvisible(invisible);
    }

    public static int getMaxAir(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getMaxAir();
    }

    public static int getAir(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getAir();
    }

    public static void setAir(@Nonnull LivingEntity livingEntity, int air) {
        livingEntity.setAir(air);
    }

    public static void onStruckByLightning(@Nonnull LivingEntity livingEntity, LightningBoltEntity lightningBolt) {
        livingEntity.onStruckByLightning(lightningBolt);
    }

    public static void onEnterBubbleColumnWithAirAbove(@Nonnull LivingEntity livingEntity, boolean downwards) {
        livingEntity.onEnterBubbleColumnWithAirAbove(downwards);
    }

    public static void onEnterBubbleColumn(@Nonnull LivingEntity livingEntity, boolean downwards) {
        livingEntity.onEnterBubbleColumn(downwards);
    }

    public static void onKillEntity(@Nonnull LivingEntity livingEntity, LivingEntity entityLivingIn) {
        livingEntity.onKillEntity(entityLivingIn);
    }

    public static void setMotionMultiplier(@Nonnull LivingEntity livingEntity, BlockState state, Vec3d motionMultiplierIn) {
        livingEntity.setMotionMultiplier(state, motionMultiplierIn);
    }

    public static ITextComponent getName(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getName();
    }

    public static boolean isEntityEqual(@Nonnull LivingEntity livingEntity, Entity entityIn) {
        return livingEntity.isEntityEqual(entityIn);
    }

    public static boolean canBeAttackedWithItem(@Nonnull LivingEntity livingEntity) {
        return livingEntity.canBeAttackedWithItem();
    }

    public static boolean hitByEntity(@Nonnull LivingEntity livingEntity, Entity entityIn) {
        return livingEntity.hitByEntity(entityIn);
    }

    public static boolean isInvulnerableTo(@Nonnull LivingEntity livingEntity, DamageSource source) {
        return livingEntity.isInvulnerableTo(source);
    }

    public static boolean isInvulnerable(@Nonnull LivingEntity livingEntity) {
        return livingEntity.isInvulnerable();
    }

    public static void setInvulnerable(@Nonnull LivingEntity livingEntity, boolean isInvulnerable) {
        livingEntity.setInvulnerable(isInvulnerable);
    }

    public static void copyLocationAndAnglesFrom(@Nonnull LivingEntity livingEntity, Entity entityIn) {
        livingEntity.copyLocationAndAnglesFrom(entityIn);
    }

    public static void copyDataFromOld(@Nonnull LivingEntity livingEntity, Entity entityIn) {
        livingEntity.copyDataFromOld(entityIn);
    }

    @Nullable
    public static Entity changeDimension(@Nonnull LivingEntity livingEntity, DimensionType destination) {
        return livingEntity.changeDimension(destination);
    }

    @Nullable
    public static Entity changeDimension(@Nonnull LivingEntity livingEntity, DimensionType destination, ITeleporter teleporter) {
        return livingEntity.changeDimension(destination, teleporter);
    }

    public static boolean isNonBoss(@Nonnull LivingEntity livingEntity) {
        return livingEntity.isNonBoss();
    }

    public static float getExplosionResistance(@Nonnull LivingEntity livingEntity, Explosion explosionIn, IBlockReader worldIn, BlockPos pos, BlockState blockStateIn, IFluidState p_180428_5_, float p_180428_6_) {
        return livingEntity.getExplosionResistance(explosionIn, worldIn, pos, blockStateIn, p_180428_5_, p_180428_6_);
    }

    public static boolean canExplosionDestroyBlock(@Nonnull LivingEntity livingEntity, Explosion explosionIn, IBlockReader worldIn, BlockPos pos, BlockState blockStateIn, float p_174816_5_) {
        return livingEntity.canExplosionDestroyBlock(explosionIn, worldIn, pos, blockStateIn, p_174816_5_);
    }

    public static int getMaxFallHeight(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getMaxFallHeight();
    }

    public static Vec3d getLastPortalVec(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getLastPortalVec();
    }

    public static Direction getTeleportDirection(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getTeleportDirection();
    }

    public static boolean doesEntityNotTriggerPressurePlate(@Nonnull LivingEntity livingEntity) {
        return livingEntity.doesEntityNotTriggerPressurePlate();
    }

    public static void fillCrashReport(@Nonnull LivingEntity livingEntity, CrashReportCategory category) {
        livingEntity.fillCrashReport(category);
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean canRenderOnFire(@Nonnull LivingEntity livingEntity) {
        return livingEntity.canRenderOnFire();
    }

    public static void setUniqueId(@Nonnull LivingEntity livingEntity, UUID uniqueIdIn) {
        livingEntity.setUniqueId(uniqueIdIn);
    }

    public static UUID getUniqueID(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getUniqueID();
    }

    public static String getCachedUniqueIdString(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getCachedUniqueIdString();
    }

    public static String getScoreboardName(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getScoreboardName();
    }

    public static boolean isPushedByWater(@Nonnull LivingEntity livingEntity) {
        return livingEntity.isPushedByWater();
    }

    public static ITextComponent getDisplayName(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getDisplayName();
    }

    public static void setCustomName(@Nonnull LivingEntity livingEntity, @Nullable ITextComponent name) {
        livingEntity.setCustomName(name);
    }

    @Nullable
    public static ITextComponent getCustomName(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getCustomName();
    }

    public static boolean hasCustomName(@Nonnull LivingEntity livingEntity) {
        return livingEntity.hasCustomName();
    }

    public static void setCustomNameVisible(@Nonnull LivingEntity livingEntity, boolean alwaysRenderNameTag) {
        livingEntity.setCustomNameVisible(alwaysRenderNameTag);
    }

    public static boolean isCustomNameVisible(@Nonnull LivingEntity livingEntity) {
        return livingEntity.isCustomNameVisible();
    }

    public static void teleportKeepLoaded(@Nonnull LivingEntity livingEntity, double p_223102_1_, double p_223102_3_, double p_223102_5_) {
        livingEntity.teleportKeepLoaded(p_223102_1_, p_223102_3_, p_223102_5_);
    }

    public static void setPositionAndUpdate(@Nonnull LivingEntity livingEntity, double x, double y, double z) {
        livingEntity.setPositionAndUpdate(x, y, z);
    }

    public static void recalculateSize(@Nonnull LivingEntity livingEntity) {
        livingEntity.recalculateSize();
    }

    public static Direction getHorizontalFacing(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getHorizontalFacing();
    }

    public static Direction getAdjustedHorizontalFacing(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getAdjustedHorizontalFacing();
    }

    public static boolean isSpectatedByPlayer(@Nonnull LivingEntity livingEntity, ServerPlayerEntity player) {
        return livingEntity.isSpectatedByPlayer(player);
    }

    public static AxisAlignedBB getBoundingBox(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getBoundingBox();
    }

    @OnlyIn(Dist.CLIENT)
    public static AxisAlignedBB getRenderBoundingBox(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getRenderBoundingBox();
    }

    public static void setBoundingBox(@Nonnull LivingEntity livingEntity, AxisAlignedBB bb) {
        livingEntity.setBoundingBox(bb);
    }

    @OnlyIn(Dist.CLIENT)
    public static float getEyeHeight(@Nonnull LivingEntity livingEntity, Pose p_213307_1_) {
        return livingEntity.getEyeHeight(p_213307_1_);
    }

    public static float getEyeHeight(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getEyeHeight();
    }

    public static boolean replaceItemInInventory(@Nonnull LivingEntity livingEntity, int inventorySlot, ItemStack itemStackIn) {
        return livingEntity.replaceItemInInventory(inventorySlot, itemStackIn);
    }

    public static void sendMessage(@Nonnull LivingEntity livingEntity, ITextComponent component) {
        livingEntity.sendMessage(component);
    }

    public static BlockPos getPosition(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getPosition();
    }

    public static Vec3d getPositionVector(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getPositionVector();
    }

    public static World getEntityWorld(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getEntityWorld();
    }

    @Nullable
    public static MinecraftServer getServer(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getServer();
    }

    public static ActionResultType applyPlayerInteraction(@Nonnull LivingEntity livingEntity, PlayerEntity player, Vec3d vec, Hand hand) {
        return livingEntity.applyPlayerInteraction(player, vec, hand);
    }

    public static boolean isImmuneToExplosions(@Nonnull LivingEntity livingEntity) {
        return livingEntity.isImmuneToExplosions();
    }

    public static void addTrackingPlayer(@Nonnull LivingEntity livingEntity, ServerPlayerEntity player) {
        livingEntity.addTrackingPlayer(player);
    }

    public static void removeTrackingPlayer(@Nonnull LivingEntity livingEntity, ServerPlayerEntity player) {
        livingEntity.removeTrackingPlayer(player);
    }

    public static float getRotatedYaw(@Nonnull LivingEntity livingEntity, Rotation transformRotation) {
        return livingEntity.getRotatedYaw(transformRotation);
    }

    public static float getMirroredYaw(@Nonnull LivingEntity livingEntity, Mirror transformMirror) {
        return livingEntity.getMirroredYaw(transformMirror);
    }

    public static boolean ignoreItemEntityData(@Nonnull LivingEntity livingEntity) {
        return livingEntity.ignoreItemEntityData();
    }

    public static boolean setPositionNonDirty(@Nonnull LivingEntity livingEntity) {
        return livingEntity.setPositionNonDirty();
    }

    @Nullable
    public static Entity getControllingPassenger(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getControllingPassenger();
    }

    public static List<Entity> getPassengers(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getPassengers();
    }

    public static boolean isPassenger(@Nonnull LivingEntity livingEntity, Class<? extends Entity> p_205708_1_) {
        return livingEntity.isPassenger(p_205708_1_);
    }

    public static Collection<Entity> getRecursivePassengers(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getRecursivePassengers();
    }

    public static Stream<Entity> getSelfAndPassengers(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getSelfAndPassengers();
    }

    public static boolean isOnePlayerRiding(@Nonnull LivingEntity livingEntity) {
        return livingEntity.isOnePlayerRiding();
    }

    public static Entity getLowestRidingEntity(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getLowestRidingEntity();
    }

    public static boolean isRidingSameEntity(@Nonnull LivingEntity livingEntity, Entity entityIn) {
        return livingEntity.isRidingSameEntity(entityIn);
    }

    public static boolean isRidingOrBeingRiddenBy(@Nonnull LivingEntity livingEntity, Entity entityIn) {
        return livingEntity.isRidingOrBeingRiddenBy(entityIn);
    }

    public static void repositionDirectPassengers(@Nonnull LivingEntity livingEntity, Entity.IMoveCallback p_226265_1_) {
        livingEntity.repositionDirectPassengers(p_226265_1_);
    }

    public static boolean canPassengerSteer(@Nonnull LivingEntity livingEntity) {
        return livingEntity.canPassengerSteer();
    }

    @Nullable
    public static Entity getRidingEntity(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getRidingEntity();
    }

    public static PushReaction getPushReaction(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getPushReaction();
    }

    public static SoundCategory getSoundCategory(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getSoundCategory();
    }

    public static CommandSource getCommandSource(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getCommandSource();
    }

    public static boolean hasPermissionLevel(@Nonnull LivingEntity livingEntity, int p_211513_1_) {
        return livingEntity.hasPermissionLevel(p_211513_1_);
    }

    public static boolean shouldReceiveFeedback(@Nonnull LivingEntity livingEntity) {
        return livingEntity.shouldReceiveFeedback();
    }

    public static boolean shouldReceiveErrors(@Nonnull LivingEntity livingEntity) {
        return livingEntity.shouldReceiveErrors();
    }

    public static boolean allowLogging(@Nonnull LivingEntity livingEntity) {
        return livingEntity.allowLogging();
    }

    public static boolean handleFluidAcceleration(@Nonnull LivingEntity livingEntity, Tag<Fluid> p_210500_1_) {
        return livingEntity.handleFluidAcceleration(p_210500_1_);
    }

    public static double getSubmergedHeight(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getSubmergedHeight();
    }

    public static float getWidth(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getWidth();
    }

    public static float getHeight(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getHeight();
    }

    public static Vec3d getPositionVec(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getPositionVec();
    }

    public static Vec3d getMotion(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getMotion();
    }

    public static void setMotion(@Nonnull LivingEntity livingEntity, Vec3d motionIn) {
        livingEntity.setMotion(motionIn);
    }

    public static void setMotion(@Nonnull LivingEntity livingEntity, double x, double y, double z) {
        livingEntity.setMotion(x, y, z);
    }

    public static double getPosX(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getPosX();
    }

    public static double getPosXWidth(@Nonnull LivingEntity livingEntity, double p_226275_1_) {
        return livingEntity.getPosXWidth(p_226275_1_);
    }

    public static double getPosXRandom(@Nonnull LivingEntity livingEntity, double p_226282_1_) {
        return livingEntity.getPosXRandom(p_226282_1_);
    }

    public static double getPosY(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getPosY();
    }

    public static double getPosYHeight(@Nonnull LivingEntity livingEntity, double p_226283_1_) {
        return livingEntity.getPosYHeight(p_226283_1_);
    }

    public static double getPosYRandom(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getPosYRandom();
    }

    public static double getPosYEye(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getPosYEye();
    }

    public static double getPosZ(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getPosZ();
    }

    public static double getPosZWidth(@Nonnull LivingEntity livingEntity, double p_226285_1_) {
        return livingEntity.getPosZWidth(p_226285_1_);
    }

    public static double getPosZRandom(@Nonnull LivingEntity livingEntity, double p_226287_1_) {
        return livingEntity.getPosZRandom(p_226287_1_);
    }

    public static void setRawPosition(@Nonnull LivingEntity livingEntity, double x, double y, double z) {
        livingEntity.setRawPosition(x, y, z);
    }

    public static void checkDespawn(@Nonnull LivingEntity livingEntity) {
        livingEntity.checkDespawn();
    }

    public static void moveForced(@Nonnull LivingEntity livingEntity, double p_225653_1_, double p_225653_3_, double p_225653_5_) {
        livingEntity.moveForced(p_225653_1_, p_225653_3_, p_225653_5_);
    }

    public static void canUpdate(@Nonnull LivingEntity livingEntity, boolean value) {
        livingEntity.canUpdate(value);
    }

    public static boolean canUpdate(@Nonnull LivingEntity livingEntity) {
        return livingEntity.canUpdate();
    }

    public static Collection<ItemEntity> captureDrops(@Nonnull LivingEntity livingEntity) {
        return livingEntity.captureDrops();
    }

    public static Collection<ItemEntity> captureDrops(@Nonnull LivingEntity livingEntity, Collection<ItemEntity> value) {
        return livingEntity.captureDrops(value);
    }

    public static CompoundNBT getPersistentData(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getPersistentData();
    }

    public static boolean canTrample(@Nonnull LivingEntity livingEntity, BlockState state, BlockPos pos, float fallDistance) {
        return livingEntity.canTrample(state, pos, fallDistance);
    }

    public static boolean isAddedToWorld(@Nonnull LivingEntity livingEntity) {
        return livingEntity.isAddedToWorld();
    }

    public static void onAddedToWorld(@Nonnull LivingEntity livingEntity) {
        livingEntity.onAddedToWorld();
    }

    public static void onRemovedFromWorld(@Nonnull LivingEntity livingEntity) {
        livingEntity.onRemovedFromWorld();
    }

    public static void revive(@Nonnull LivingEntity livingEntity) {
        livingEntity.revive();
    }

    public static boolean areCapsCompatible(@Nonnull LivingEntity livingEntity, CapabilityProvider<Entity> other) {
        return livingEntity.areCapsCompatible(other);
    }

    public static boolean areCapsCompatible(@Nonnull LivingEntity livingEntity, @Nullable CapabilityDispatcher other) {
        return livingEntity.areCapsCompatible(other);
    }

    @Nonnull
    public static <T> LazyOptional<T> getCapability(@Nonnull LivingEntity livingEntity, @Nonnull Capability<T> cap) {
        return livingEntity.getCapability(cap);
    }

    public static Entity getEntity(@Nonnull LivingEntity livingEntity) {
        return livingEntity.getEntity();
    }

    public static void deserializeNBT(@Nonnull LivingEntity livingEntity, CompoundNBT nbt) {
        livingEntity.deserializeNBT(nbt);
    }

    public static CompoundNBT serializeNBT(@Nonnull LivingEntity livingEntity) {
        return livingEntity.serializeNBT();
    }

    public static boolean shouldRiderSit(@Nonnull LivingEntity livingEntity) {
        return livingEntity.shouldRiderSit();
    }

    public static ItemStack getPickedResult(@Nonnull LivingEntity livingEntity, RayTraceResult target) {
        return livingEntity.getPickedResult(target);
    }

    public static boolean canRiderInteract(@Nonnull LivingEntity livingEntity) {
        return livingEntity.canRiderInteract();
    }

    public static EntityClassification getClassification(@Nonnull LivingEntity livingEntity, boolean forSpawnCount) {
        return livingEntity.getClassification(forSpawnCount);
    }

    @OnlyIn(Dist.CLIENT)
    public static int getTeamColor(@Nonnull Entity entity) {
        return entity.getTeamColor();
    }

    public static boolean isSpectator(@Nonnull Entity entity) {
        return entity.isSpectator();
    }

    public static void detach(@Nonnull Entity entity) {
        entity.detach();
    }

    public static void setPacketCoordinates(@Nonnull Entity entity, double p_213312_1_, double p_213312_3_, double p_213312_5_) {
        entity.setPacketCoordinates(p_213312_1_, p_213312_3_, p_213312_5_);
    }

    public static EntityType<?> getType(@Nonnull Entity entity) {
        return entity.getType();
    }

    public static int getEntityId(@Nonnull Entity entity) {
        return entity.getEntityId();
    }

    public static void setEntityId(@Nonnull Entity entity, int id) {
        entity.setEntityId(id);
    }

    public static Set<String> getTags(@Nonnull Entity entity) {
        return entity.getTags();
    }

    public static boolean addTag(@Nonnull Entity entity, String tag) {
        return entity.addTag(tag);
    }

    public static boolean removeTag(@Nonnull Entity entity, String tag) {
        return entity.removeTag(tag);
    }

    public static void onKillCommand(@Nonnull Entity entity) {
        entity.onKillCommand();
    }

    public static EntityDataManager getDataManager(@Nonnull Entity entity) {
        return entity.getDataManager();
    }

    public static void remove(@Nonnull Entity entity) {
        entity.remove();
    }

    public static void remove(@Nonnull Entity entity, boolean keepData) {
        entity.remove(keepData);
    }

    public static Pose getPose(@Nonnull Entity entity) {
        return entity.getPose();
    }

    public static void setPosition(@Nonnull Entity entity, double x, double y, double z) {
        entity.setPosition(x, y, z);
    }

    @OnlyIn(Dist.CLIENT)
    public static void rotateTowards(@Nonnull Entity entity, double yaw, double pitch) {
        entity.rotateTowards(yaw, pitch);
    }

    public static void tick(@Nonnull Entity entity) {
        entity.tick();
    }

    public static void baseTick(@Nonnull Entity entity) {
        entity.baseTick();
    }

    public static int getMaxInPortalTime(@Nonnull Entity entity) {
        return entity.getMaxInPortalTime();
    }

    public static void setFire(@Nonnull Entity entity, int seconds) {
        entity.setFire(seconds);
    }

    public static void setFireTimer(@Nonnull Entity entity, int p_223308_1_) {
        entity.setFireTimer(p_223308_1_);
    }

    public static int getFireTimer(@Nonnull Entity entity) {
        return entity.getFireTimer();
    }

    public static void extinguish(@Nonnull Entity entity) {
        entity.extinguish();
    }

    public static boolean isOffsetPositionInLiquid(@Nonnull Entity entity, double x, double y, double z) {
        return entity.isOffsetPositionInLiquid(x, y, z);
    }

    public static void move(@Nonnull Entity entity, MoverType typeIn, Vec3d pos) {
        entity.move(typeIn, pos);
    }

    public static double horizontalMag(Vec3d vec) {
        return Entity.horizontalMag(vec);
    }

    public static Vec3d collideBoundingBoxHeuristically(@Nullable Entity p_223307_0_, Vec3d p_223307_1_, AxisAlignedBB p_223307_2_, World p_223307_3_, ISelectionContext p_223307_4_, ReuseableStream<VoxelShape> p_223307_5_) {
        return Entity.collideBoundingBoxHeuristically(p_223307_0_, p_223307_1_, p_223307_2_, p_223307_3_, p_223307_4_, p_223307_5_);
    }

    public static Vec3d collideBoundingBox(Vec3d p_223310_0_, AxisAlignedBB p_223310_1_, ReuseableStream<VoxelShape> p_223310_2_) {
        return Entity.collideBoundingBox(p_223310_0_, p_223310_1_, p_223310_2_);
    }

    public static Vec3d getAllowedMovement(Vec3d vec, AxisAlignedBB collisionBox, IWorldReader worldIn, ISelectionContext selectionContext, ReuseableStream<VoxelShape> potentialHits) {
        return Entity.getAllowedMovement(vec, collisionBox, worldIn, selectionContext, potentialHits);
    }

    public static void resetPositionToBB(@Nonnull Entity entity) {
        entity.resetPositionToBB();
    }

    public static void playSound(@Nonnull Entity entity, SoundEvent soundIn, float volume, float pitch) {
        entity.playSound(soundIn, volume, pitch);
    }

    public static boolean isSilent(@Nonnull Entity entity) {
        return entity.isSilent();
    }

    public static void setSilent(@Nonnull Entity entity, boolean isSilent) {
        entity.setSilent(isSilent);
    }

    public static boolean hasNoGravity(@Nonnull Entity entity) {
        return entity.hasNoGravity();
    }

    public static void setNoGravity(@Nonnull Entity entity, boolean noGravity) {
        entity.setNoGravity(noGravity);
    }

    @Nullable
    public static AxisAlignedBB getCollisionBoundingBox(@Nonnull Entity entity) {
        return entity.getCollisionBoundingBox();
    }

    public static boolean isImmuneToFire(@Nonnull Entity entity) {
        return entity.isImmuneToFire();
    }

    public static boolean onLivingFall(@Nonnull Entity entity, float distance, float damageMultiplier) {
        return entity.onLivingFall(distance, damageMultiplier);
    }

    public static boolean isInWater(@Nonnull Entity entity) {
        return entity.isInWater();
    }

    public static boolean isWet(@Nonnull Entity entity) {
        return entity.isWet();
    }

    public static boolean isInWaterRainOrBubbleColumn(@Nonnull Entity entity) {
        return entity.isInWaterRainOrBubbleColumn();
    }

    public static boolean isInWaterOrBubbleColumn(@Nonnull Entity entity) {
        return entity.isInWaterOrBubbleColumn();
    }

    public static boolean canSwim(@Nonnull Entity entity) {
        return entity.canSwim();
    }

    public static void updateSwimming(@Nonnull Entity entity) {
        entity.updateSwimming();
    }

    public static boolean handleWaterMovement(@Nonnull Entity entity) {
        return entity.handleWaterMovement();
    }

    public static void spawnRunningParticles(@Nonnull Entity entity) {
        entity.spawnRunningParticles();
    }

    public static boolean areEyesInFluid(@Nonnull Entity entity, Tag<Fluid> tagIn) {
        return entity.areEyesInFluid(tagIn);
    }

    public static boolean areEyesInFluid(@Nonnull Entity entity, Tag<Fluid> p_213290_1_, boolean checkChunkLoaded) {
        return entity.areEyesInFluid(p_213290_1_, checkChunkLoaded);
    }

    public static void setInLava(@Nonnull Entity entity) {
        entity.setInLava();
    }

    public static boolean isInLava(@Nonnull Entity entity) {
        return entity.isInLava();
    }

    public static void moveRelative(@Nonnull Entity entity, float p_213309_1_, Vec3d relative) {
        entity.moveRelative(p_213309_1_, relative);
    }

    public static float getBrightness(@Nonnull Entity entity) {
        return entity.getBrightness();
    }

    public static void setWorld(@Nonnull Entity entity, World worldIn) {
        entity.setWorld(worldIn);
    }

    public static void setPositionAndRotation(@Nonnull Entity entity, double x, double y, double z, float yaw, float pitch) {
        entity.setPositionAndRotation(x, y, z, yaw, pitch);
    }

    public static void moveToBlockPosAndAngles(@Nonnull Entity entity, BlockPos pos, float rotationYawIn, float rotationPitchIn) {
        entity.moveToBlockPosAndAngles(pos, rotationYawIn, rotationPitchIn);
    }

    public static void setLocationAndAngles(@Nonnull Entity entity, double x, double y, double z, float yaw, float pitch) {
        entity.setLocationAndAngles(x, y, z, yaw, pitch);
    }

    public static void forceSetPosition(@Nonnull Entity entity, double x, double y, double z) {
        entity.forceSetPosition(x, y, z);
    }

    public static float getDistance(@Nonnull Entity entity, Entity entityIn) {
        return entity.getDistance(entityIn);
    }

    public static double getDistanceSq(@Nonnull Entity entity, double x, double y, double z) {
        return entity.getDistanceSq(x, y, z);
    }

    public static double getDistanceSq(@Nonnull Entity entity, Entity entityIn) {
        return entity.getDistanceSq(entityIn);
    }

    public static double getDistanceSq(@Nonnull Entity entity, Vec3d p_195048_1_) {
        return entity.getDistanceSq(p_195048_1_);
    }

    public static void onCollideWithPlayer(@Nonnull Entity entity, PlayerEntity entityIn) {
        entity.onCollideWithPlayer(entityIn);
    }

    public static void applyEntityCollision(@Nonnull Entity entity, Entity entityIn) {
        entity.applyEntityCollision(entityIn);
    }

    public static void addVelocity(@Nonnull Entity entity, double x, double y, double z) {
        entity.addVelocity(x, y, z);
    }

    public static boolean attackEntityFrom(@Nonnull Entity entity, DamageSource source, float amount) {
        return entity.attackEntityFrom(source, amount);
    }

    public static Vec3d getLook(@Nonnull Entity entity, float partialTicks) {
        return entity.getLook(partialTicks);
    }

    public static float getPitch(@Nonnull Entity entity, float partialTicks) {
        return entity.getPitch(partialTicks);
    }

    public static float getYaw(@Nonnull Entity entity, float partialTicks) {
        return entity.getYaw(partialTicks);
    }

    public static Vec3d getUpVector(@Nonnull Entity entity, float partialTicks) {
        return entity.getUpVector(partialTicks);
    }

    public static Vec3d getEyePosition(@Nonnull Entity entity, float partialTicks) {
        return entity.getEyePosition(partialTicks);
    }

    public static RayTraceResult pick(@Nonnull Entity entity, double p_213324_1_, float p_213324_3_, boolean p_213324_4_) {
        return entity.pick(p_213324_1_, p_213324_3_, p_213324_4_);
    }

    public static boolean canBeCollidedWith(@Nonnull Entity entity) {
        return entity.canBeCollidedWith();
    }

    public static boolean canBePushed(@Nonnull Entity entity) {
        return entity.canBePushed();
    }

    public static void awardKillScore(@Nonnull Entity entity, Entity p_191956_1_, int p_191956_2_, DamageSource p_191956_3_) {
        entity.awardKillScore(p_191956_1_, p_191956_2_, p_191956_3_);
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean isInRangeToRender3d(@Nonnull Entity entity, double x, double y, double z) {
        return entity.isInRangeToRender3d(x, y, z);
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean isInRangeToRenderDist(@Nonnull Entity entity, double distance) {
        return entity.isInRangeToRenderDist(distance);
    }

    public static boolean writeUnlessRemoved(@Nonnull Entity entity, CompoundNBT compound) {
        return entity.writeUnlessRemoved(compound);
    }

    public static boolean writeUnlessPassenger(@Nonnull Entity entity, CompoundNBT compound) {
        return entity.writeUnlessPassenger(compound);
    }

    public static CompoundNBT writeWithoutTypeId(@Nonnull Entity entity, CompoundNBT compound) {
        return entity.writeWithoutTypeId(compound);
    }

    public static void read(@Nonnull Entity entity, CompoundNBT compound) {
        entity.read(compound);
    }

    @Nullable
    public static String getEntityString(@Nonnull Entity entity) {
        return entity.getEntityString();
    }

    @Nullable
    public static ItemEntity entityDropItem(@Nonnull Entity entity, IItemProvider itemIn) {
        return entity.entityDropItem(itemIn);
    }

    @Nullable
    public static ItemEntity entityDropItem(@Nonnull Entity entity, IItemProvider itemIn, int offset) {
        return entity.entityDropItem(itemIn, offset);
    }

    @Nullable
    public static ItemEntity entityDropItem(@Nonnull Entity entity, ItemStack stack) {
        return entity.entityDropItem(stack);
    }

    @Nullable
    public static ItemEntity entityDropItem(@Nonnull Entity entity, ItemStack stack, float offsetY) {
        return entity.entityDropItem(stack, offsetY);
    }

    public static boolean isAlive(@Nonnull Entity entity) {
        return entity.isAlive();
    }

    public static boolean isEntityInsideOpaqueBlock(@Nonnull Entity entity) {
        return entity.isEntityInsideOpaqueBlock();
    }

    public static boolean processInitialInteract(@Nonnull Entity entity, PlayerEntity player, Hand hand) {
        return entity.processInitialInteract(player, hand);
    }

    @Nullable
    public static AxisAlignedBB getCollisionBox(@Nonnull Entity entity, Entity entityIn) {
        return entity.getCollisionBox(entityIn);
    }

    public static void updateRidden(@Nonnull Entity entity) {
        entity.updateRidden();
    }

    public static void updatePassenger(@Nonnull Entity entity, Entity passenger) {
        entity.updatePassenger(passenger);
    }

    public static void positionRider(@Nonnull Entity entity, Entity p_226266_1_, Entity.IMoveCallback p_226266_2_) {
        entity.positionRider(p_226266_1_, p_226266_2_);
    }

    @OnlyIn(Dist.CLIENT)
    public static void applyOrientationToEntity(@Nonnull Entity entity, Entity entityToUpdate) {
        entity.applyOrientationToEntity(entityToUpdate);
    }

    public static double getYOffset(@Nonnull Entity entity) {
        return entity.getYOffset();
    }

    public static double getMountedYOffset(@Nonnull Entity entity) {
        return entity.getMountedYOffset();
    }

    public static boolean startRiding(@Nonnull Entity entity, Entity entityIn) {
        return entity.startRiding(entityIn);
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean isLiving(@Nonnull Entity entity) {
        return entity.isLiving();
    }

    public static boolean startRiding(@Nonnull Entity entity, Entity entityIn, boolean force) {
        return entity.startRiding(entityIn, force);
    }

    public static void removePassengers(@Nonnull Entity entity) {
        entity.removePassengers();
    }

    public static void stopRiding(@Nonnull Entity entity) {
        entity.stopRiding();
    }

    @OnlyIn(Dist.CLIENT)
    public static void setPositionAndRotationDirect(@Nonnull Entity entity, double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
        entity.setPositionAndRotationDirect(x, y, z, yaw, pitch, posRotationIncrements, teleport);
    }

    @OnlyIn(Dist.CLIENT)
    public static void setHeadRotation(@Nonnull Entity entity, float yaw, int pitch) {
        entity.setHeadRotation(yaw, pitch);
    }

    public static float getCollisionBorderSize(@Nonnull Entity entity) {
        return entity.getCollisionBorderSize();
    }

    public static Vec3d getLookVec(@Nonnull Entity entity) {
        return entity.getLookVec();
    }

    public static Vec2f getPitchYaw(@Nonnull Entity entity) {
        return entity.getPitchYaw();
    }

    @OnlyIn(Dist.CLIENT)
    public static Vec3d getForward(@Nonnull Entity entity) {
        return entity.getForward();
    }

    public static void setPortal(@Nonnull Entity entity, BlockPos pos) {
        entity.setPortal(pos);
    }

    public static int getPortalCooldown(@Nonnull Entity entity) {
        return entity.getPortalCooldown();
    }

    @OnlyIn(Dist.CLIENT)
    public static void setVelocity(@Nonnull Entity entity, double x, double y, double z) {
        entity.setVelocity(x, y, z);
    }

    @OnlyIn(Dist.CLIENT)
    public static void handleStatusUpdate(@Nonnull Entity entity, byte id) {
        entity.handleStatusUpdate(id);
    }

    @OnlyIn(Dist.CLIENT)
    public static void performHurtAnimation(@Nonnull Entity entity) {
        entity.performHurtAnimation();
    }

    public static Iterable<ItemStack> getHeldEquipment(@Nonnull Entity entity) {
        return entity.getHeldEquipment();
    }

    public static Iterable<ItemStack> getArmorInventoryList(@Nonnull Entity entity) {
        return entity.getArmorInventoryList();
    }

    public static Iterable<ItemStack> getEquipmentAndArmor(@Nonnull Entity entity) {
        return entity.getEquipmentAndArmor();
    }

    public static void setItemStackToSlot(@Nonnull Entity entity, EquipmentSlotType slotIn, ItemStack stack) {
        entity.setItemStackToSlot(slotIn, stack);
    }

    public static boolean isBurning(@Nonnull Entity entity) {
        return entity.isBurning();
    }

    public static boolean isPassenger(@Nonnull Entity entity) {
        return entity.isPassenger();
    }

    public static boolean isBeingRidden(@Nonnull Entity entity) {
        return entity.isBeingRidden();
    }

    @Deprecated
    public static boolean canBeRiddenInWater(@Nonnull Entity entity) {
        return entity.canBeRiddenInWater();
    }

    public static void setSneaking(@Nonnull Entity entity, boolean keyDownIn) {
        entity.setSneaking(keyDownIn);
    }

    public static boolean isSneaking(@Nonnull Entity entity) {
        return entity.isSneaking();
    }

    public static boolean isSteppingCarefully(@Nonnull Entity entity) {
        return entity.isSteppingCarefully();
    }

    public static boolean isSuppressingBounce(@Nonnull Entity entity) {
        return entity.isSuppressingBounce();
    }

    public static boolean isDiscrete(@Nonnull Entity entity) {
        return entity.isDiscrete();
    }

    public static boolean isDescending(@Nonnull Entity entity) {
        return entity.isDescending();
    }

    public static boolean isCrouching(@Nonnull Entity entity) {
        return entity.isCrouching();
    }

    public static boolean isSprinting(@Nonnull Entity entity) {
        return entity.isSprinting();
    }

    public static void setSprinting(@Nonnull Entity entity, boolean sprinting) {
        entity.setSprinting(sprinting);
    }

    public static boolean isSwimming(@Nonnull Entity entity) {
        return entity.isSwimming();
    }

    public static boolean isActualySwimming(@Nonnull Entity entity) {
        return entity.isActualySwimming();
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean isVisuallySwimming(@Nonnull Entity entity) {
        return entity.isVisuallySwimming();
    }

    public static void setSwimming(@Nonnull Entity entity, boolean p_204711_1_) {
        entity.setSwimming(p_204711_1_);
    }

    public static boolean isGlowing(@Nonnull Entity entity) {
        return entity.isGlowing();
    }

    public static void setGlowing(@Nonnull Entity entity, boolean glowingIn) {
        entity.setGlowing(glowingIn);
    }

    public static boolean isInvisible(@Nonnull Entity entity) {
        return entity.isInvisible();
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean isInvisibleToPlayer(@Nonnull Entity entity, PlayerEntity player) {
        return entity.isInvisibleToPlayer(player);
    }

    @Nullable
    public static Team getTeam(@Nonnull Entity entity) {
        return entity.getTeam();
    }

    public static boolean isOnSameTeam(@Nonnull Entity entity, Entity entityIn) {
        return entity.isOnSameTeam(entityIn);
    }

    public static boolean isOnScoreboardTeam(@Nonnull Entity entity, Team teamIn) {
        return entity.isOnScoreboardTeam(teamIn);
    }

    public static void setInvisible(@Nonnull Entity entity, boolean invisible) {
        entity.setInvisible(invisible);
    }

    public static int getMaxAir(@Nonnull Entity entity) {
        return entity.getMaxAir();
    }

    public static int getAir(@Nonnull Entity entity) {
        return entity.getAir();
    }

    public static void setAir(@Nonnull Entity entity, int air) {
        entity.setAir(air);
    }

    public static void onStruckByLightning(@Nonnull Entity entity, LightningBoltEntity lightningBolt) {
        entity.onStruckByLightning(lightningBolt);
    }

    public static void onEnterBubbleColumnWithAirAbove(@Nonnull Entity entity, boolean downwards) {
        entity.onEnterBubbleColumnWithAirAbove(downwards);
    }

    public static void onEnterBubbleColumn(@Nonnull Entity entity, boolean downwards) {
        entity.onEnterBubbleColumn(downwards);
    }

    public static void onKillEntity(@Nonnull Entity entity, LivingEntity entityLivingIn) {
        entity.onKillEntity(entityLivingIn);
    }

    public static void setMotionMultiplier(@Nonnull Entity entity, BlockState state, Vec3d motionMultiplierIn) {
        entity.setMotionMultiplier(state, motionMultiplierIn);
    }

    public static ITextComponent getName(@Nonnull Entity entity) {
        return entity.getName();
    }

    public static boolean isEntityEqual(@Nonnull Entity entity, Entity entityIn) {
        return entity.isEntityEqual(entityIn);
    }

    public static float getRotationYawHead(@Nonnull Entity entity) {
        return entity.getRotationYawHead();
    }

    public static void setRotationYawHead(@Nonnull Entity entity, float rotation) {
        entity.setRotationYawHead(rotation);
    }

    public static void setRenderYawOffset(@Nonnull Entity entity, float offset) {
        entity.setRenderYawOffset(offset);
    }

    public static boolean canBeAttackedWithItem(@Nonnull Entity entity) {
        return entity.canBeAttackedWithItem();
    }

    public static boolean hitByEntity(@Nonnull Entity entity, Entity entityIn) {
        return entity.hitByEntity(entityIn);
    }

    public static boolean isInvulnerableTo(@Nonnull Entity entity, DamageSource source) {
        return entity.isInvulnerableTo(source);
    }

    public static boolean isInvulnerable(@Nonnull Entity entity) {
        return entity.isInvulnerable();
    }

    public static void setInvulnerable(@Nonnull Entity entity, boolean isInvulnerable) {
        entity.setInvulnerable(isInvulnerable);
    }

    public static void copyLocationAndAnglesFrom(@Nonnull Entity entity, Entity entityIn) {
        entity.copyLocationAndAnglesFrom(entityIn);
    }

    public static void copyDataFromOld(@Nonnull Entity entity, Entity entityIn) {
        entity.copyDataFromOld(entityIn);
    }

    @Nullable
    public static Entity changeDimension(@Nonnull Entity entity, DimensionType destination) {
        return entity.changeDimension(destination);
    }

    @Nullable
    public static Entity changeDimension(@Nonnull Entity entity, DimensionType destination, ITeleporter teleporter) {
        return entity.changeDimension(destination, teleporter);
    }

    public static boolean isNonBoss(@Nonnull Entity entity) {
        return entity.isNonBoss();
    }

    public static float getExplosionResistance(@Nonnull Entity entity, Explosion explosionIn, IBlockReader worldIn, BlockPos pos, BlockState blockStateIn, IFluidState p_180428_5_, float p_180428_6_) {
        return entity.getExplosionResistance(explosionIn, worldIn, pos, blockStateIn, p_180428_5_, p_180428_6_);
    }

    public static boolean canExplosionDestroyBlock(@Nonnull Entity entity, Explosion explosionIn, IBlockReader worldIn, BlockPos pos, BlockState blockStateIn, float p_174816_5_) {
        return entity.canExplosionDestroyBlock(explosionIn, worldIn, pos, blockStateIn, p_174816_5_);
    }

    public static int getMaxFallHeight(@Nonnull Entity entity) {
        return entity.getMaxFallHeight();
    }

    public static Vec3d getLastPortalVec(@Nonnull Entity entity) {
        return entity.getLastPortalVec();
    }

    public static Direction getTeleportDirection(@Nonnull Entity entity) {
        return entity.getTeleportDirection();
    }

    public static boolean doesEntityNotTriggerPressurePlate(@Nonnull Entity entity) {
        return entity.doesEntityNotTriggerPressurePlate();
    }

    public static void fillCrashReport(@Nonnull Entity entity, CrashReportCategory category) {
        entity.fillCrashReport(category);
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean canRenderOnFire(@Nonnull Entity entity) {
        return entity.canRenderOnFire();
    }

    public static void setUniqueId(@Nonnull Entity entity, UUID uniqueIdIn) {
        entity.setUniqueId(uniqueIdIn);
    }

    public static UUID getUniqueID(@Nonnull Entity entity) {
        return entity.getUniqueID();
    }

    public static String getCachedUniqueIdString(@Nonnull Entity entity) {
        return entity.getCachedUniqueIdString();
    }

    public static String getScoreboardName(@Nonnull Entity entity) {
        return entity.getScoreboardName();
    }

    public static boolean isPushedByWater(@Nonnull Entity entity) {
        return entity.isPushedByWater();
    }

    @OnlyIn(Dist.CLIENT)
    public static double getRenderDistanceWeight() {
        return Entity.getRenderDistanceWeight();
    }

    @OnlyIn(Dist.CLIENT)
    public static void setRenderDistanceWeight(double renderDistWeight) {
        Entity.setRenderDistanceWeight(renderDistWeight);
    }

    public static ITextComponent getDisplayName(@Nonnull Entity entity) {
        return entity.getDisplayName();
    }

    public static void setCustomName(@Nonnull Entity entity, @Nullable ITextComponent name) {
        entity.setCustomName(name);
    }

    @Nullable
    public static ITextComponent getCustomName(@Nonnull Entity entity) {
        return entity.getCustomName();
    }

    public static boolean hasCustomName(@Nonnull Entity entity) {
        return entity.hasCustomName();
    }

    public static void setCustomNameVisible(@Nonnull Entity entity, boolean alwaysRenderNameTag) {
        entity.setCustomNameVisible(alwaysRenderNameTag);
    }

    public static boolean isCustomNameVisible(@Nonnull Entity entity) {
        return entity.isCustomNameVisible();
    }

    public static void teleportKeepLoaded(@Nonnull Entity entity, double p_223102_1_, double p_223102_3_, double p_223102_5_) {
        entity.teleportKeepLoaded(p_223102_1_, p_223102_3_, p_223102_5_);
    }

    public static void setPositionAndUpdate(@Nonnull Entity entity, double x, double y, double z) {
        entity.setPositionAndUpdate(x, y, z);
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean getAlwaysRenderNameTagForRender(@Nonnull Entity entity) {
        return entity.getAlwaysRenderNameTagForRender();
    }

    public static void notifyDataManagerChange(@Nonnull Entity entity, DataParameter<?> key) {
        entity.notifyDataManagerChange(key);
    }

    public static void recalculateSize(@Nonnull Entity entity) {
        entity.recalculateSize();
    }

    public static Direction getHorizontalFacing(@Nonnull Entity entity) {
        return entity.getHorizontalFacing();
    }

    public static Direction getAdjustedHorizontalFacing(@Nonnull Entity entity) {
        return entity.getAdjustedHorizontalFacing();
    }

    public static boolean isSpectatedByPlayer(@Nonnull Entity entity, ServerPlayerEntity player) {
        return entity.isSpectatedByPlayer(player);
    }

    public static AxisAlignedBB getBoundingBox(@Nonnull Entity entity) {
        return entity.getBoundingBox();
    }

    @OnlyIn(Dist.CLIENT)
    public static AxisAlignedBB getRenderBoundingBox(@Nonnull Entity entity) {
        return entity.getRenderBoundingBox();
    }

    public static void setBoundingBox(@Nonnull Entity entity, AxisAlignedBB bb) {
        entity.setBoundingBox(bb);
    }

    @OnlyIn(Dist.CLIENT)
    public static float getEyeHeight(@Nonnull Entity entity, Pose p_213307_1_) {
        return entity.getEyeHeight(p_213307_1_);
    }

    public static float getEyeHeight(@Nonnull Entity entity) {
        return entity.getEyeHeight();
    }

    public static boolean replaceItemInInventory(@Nonnull Entity entity, int inventorySlot, ItemStack itemStackIn) {
        return entity.replaceItemInInventory(inventorySlot, itemStackIn);
    }

    public static void sendMessage(@Nonnull Entity entity, ITextComponent component) {
        entity.sendMessage(component);
    }

    public static BlockPos getPosition(@Nonnull Entity entity) {
        return entity.getPosition();
    }

    public static Vec3d getPositionVector(@Nonnull Entity entity) {
        return entity.getPositionVector();
    }

    public static World getEntityWorld(@Nonnull Entity entity) {
        return entity.getEntityWorld();
    }

    @Nullable
    public static MinecraftServer getServer(@Nonnull Entity entity) {
        return entity.getServer();
    }

    public static ActionResultType applyPlayerInteraction(@Nonnull Entity entity, PlayerEntity player, Vec3d vec, Hand hand) {
        return entity.applyPlayerInteraction(player, vec, hand);
    }

    public static boolean isImmuneToExplosions(@Nonnull Entity entity) {
        return entity.isImmuneToExplosions();
    }

    public static void addTrackingPlayer(@Nonnull Entity entity, ServerPlayerEntity player) {
        entity.addTrackingPlayer(player);
    }

    public static void removeTrackingPlayer(@Nonnull Entity entity, ServerPlayerEntity player) {
        entity.removeTrackingPlayer(player);
    }

    public static float getRotatedYaw(@Nonnull Entity entity, Rotation transformRotation) {
        return entity.getRotatedYaw(transformRotation);
    }

    public static float getMirroredYaw(@Nonnull Entity entity, Mirror transformMirror) {
        return entity.getMirroredYaw(transformMirror);
    }

    public static boolean ignoreItemEntityData(@Nonnull Entity entity) {
        return entity.ignoreItemEntityData();
    }

    public static boolean setPositionNonDirty(@Nonnull Entity entity) {
        return entity.setPositionNonDirty();
    }

    @Nullable
    public static Entity getControllingPassenger(@Nonnull Entity entity) {
        return entity.getControllingPassenger();
    }

    public static List<Entity> getPassengers(@Nonnull Entity entity) {
        return entity.getPassengers();
    }

    public static boolean isPassenger(@Nonnull Entity entity, Entity entityIn) {
        return entity.isPassenger(entityIn);
    }

    public static boolean isPassenger(@Nonnull Entity entity, Class<? extends Entity> p_205708_1_) {
        return entity.isPassenger(p_205708_1_);
    }

    public static Collection<Entity> getRecursivePassengers(@Nonnull Entity entity) {
        return entity.getRecursivePassengers();
    }

    public static Stream<Entity> getSelfAndPassengers(@Nonnull Entity entity) {
        return entity.getSelfAndPassengers();
    }

    public static boolean isOnePlayerRiding(@Nonnull Entity entity) {
        return entity.isOnePlayerRiding();
    }

    public static Entity getLowestRidingEntity(@Nonnull Entity entity) {
        return entity.getLowestRidingEntity();
    }

    public static boolean isRidingSameEntity(@Nonnull Entity entity, Entity entityIn) {
        return entity.isRidingSameEntity(entityIn);
    }

    public static boolean isRidingOrBeingRiddenBy(@Nonnull Entity entity, Entity entityIn) {
        return entity.isRidingOrBeingRiddenBy(entityIn);
    }

    public static void repositionDirectPassengers(@Nonnull Entity entity, Entity.IMoveCallback p_226265_1_) {
        entity.repositionDirectPassengers(p_226265_1_);
    }

    public static boolean canPassengerSteer(@Nonnull Entity entity) {
        return entity.canPassengerSteer();
    }

    @Nullable
    public static Entity getRidingEntity(@Nonnull Entity entity) {
        return entity.getRidingEntity();
    }

    public static PushReaction getPushReaction(@Nonnull Entity entity) {
        return entity.getPushReaction();
    }

    public static SoundCategory getSoundCategory(@Nonnull Entity entity) {
        return entity.getSoundCategory();
    }

    public static CommandSource getCommandSource(@Nonnull Entity entity) {
        return entity.getCommandSource();
    }

    public static boolean hasPermissionLevel(@Nonnull Entity entity, int p_211513_1_) {
        return entity.hasPermissionLevel(p_211513_1_);
    }

    public static boolean shouldReceiveFeedback(@Nonnull Entity entity) {
        return entity.shouldReceiveFeedback();
    }

    public static boolean shouldReceiveErrors(@Nonnull Entity entity) {
        return entity.shouldReceiveErrors();
    }

    public static boolean allowLogging(@Nonnull Entity entity) {
        return entity.allowLogging();
    }

    public static void lookAt(@Nonnull Entity entity, EntityAnchorArgument.Type p_200602_1_, Vec3d p_200602_2_) {
        entity.lookAt(p_200602_1_, p_200602_2_);
    }

    public static boolean handleFluidAcceleration(@Nonnull Entity entity, Tag<Fluid> p_210500_1_) {
        return entity.handleFluidAcceleration(p_210500_1_);
    }

    public static double getSubmergedHeight(@Nonnull Entity entity) {
        return entity.getSubmergedHeight();
    }

    public static float getWidth(@Nonnull Entity entity) {
        return entity.getWidth();
    }

    public static float getHeight(@Nonnull Entity entity) {
        return entity.getHeight();
    }

    public static IPacket<?> createSpawnPacket(@Nonnull Entity entity) {
        return entity.createSpawnPacket();
    }

    public static EntitySize getSize(@Nonnull Entity entity, Pose poseIn) {
        return entity.getSize(poseIn);
    }

    public static Vec3d getPositionVec(@Nonnull Entity entity) {
        return entity.getPositionVec();
    }

    public static Vec3d getMotion(@Nonnull Entity entity) {
        return entity.getMotion();
    }

    public static void setMotion(@Nonnull Entity entity, Vec3d motionIn) {
        entity.setMotion(motionIn);
    }

    public static void setMotion(@Nonnull Entity entity, double x, double y, double z) {
        entity.setMotion(x, y, z);
    }

    public static double getPosX(@Nonnull Entity entity) {
        return entity.getPosX();
    }

    public static double getPosXWidth(@Nonnull Entity entity, double p_226275_1_) {
        return entity.getPosXWidth(p_226275_1_);
    }

    public static double getPosXRandom(@Nonnull Entity entity, double p_226282_1_) {
        return entity.getPosXRandom(p_226282_1_);
    }

    public static double getPosY(@Nonnull Entity entity) {
        return entity.getPosY();
    }

    public static double getPosYHeight(@Nonnull Entity entity, double p_226283_1_) {
        return entity.getPosYHeight(p_226283_1_);
    }

    public static double getPosYRandom(@Nonnull Entity entity) {
        return entity.getPosYRandom();
    }

    public static double getPosYEye(@Nonnull Entity entity) {
        return entity.getPosYEye();
    }

    public static double getPosZ(@Nonnull Entity entity) {
        return entity.getPosZ();
    }

    public static double getPosZWidth(@Nonnull Entity entity, double p_226285_1_) {
        return entity.getPosZWidth(p_226285_1_);
    }

    public static double getPosZRandom(@Nonnull Entity entity, double p_226287_1_) {
        return entity.getPosZRandom(p_226287_1_);
    }

    public static void setRawPosition(@Nonnull Entity entity, double x, double y, double z) {
        entity.setRawPosition(x, y, z);
    }

    public static void checkDespawn(@Nonnull Entity entity) {
        entity.checkDespawn();
    }

    public static void moveForced(@Nonnull Entity entity, double p_225653_1_, double p_225653_3_, double p_225653_5_) {
        entity.moveForced(p_225653_1_, p_225653_3_, p_225653_5_);
    }

    public static void canUpdate(@Nonnull Entity entity, boolean value) {
        entity.canUpdate(value);
    }

    public static boolean canUpdate(@Nonnull Entity entity) {
        return entity.canUpdate();
    }

    public static Collection<ItemEntity> captureDrops(@Nonnull Entity entity) {
        return entity.captureDrops();
    }

    public static Collection<ItemEntity> captureDrops(@Nonnull Entity entity, Collection<ItemEntity> value) {
        return entity.captureDrops(value);
    }

    public static CompoundNBT getPersistentData(@Nonnull Entity entity) {
        return entity.getPersistentData();
    }

    public static boolean canTrample(@Nonnull Entity entity, BlockState state, BlockPos pos, float fallDistance) {
        return entity.canTrample(state, pos, fallDistance);
    }

    public static boolean isAddedToWorld(@Nonnull Entity entity) {
        return entity.isAddedToWorld();
    }

    public static void onAddedToWorld(@Nonnull Entity entity) {
        entity.onAddedToWorld();
    }

    public static void onRemovedFromWorld(@Nonnull Entity entity) {
        entity.onRemovedFromWorld();
    }

    public static void revive(@Nonnull Entity entity) {
        entity.revive();
    }

    public static boolean areCapsCompatible(@Nonnull Entity entity, CapabilityProvider<Entity> other) {
        return entity.areCapsCompatible(other);
    }

    public static boolean areCapsCompatible(@Nonnull Entity entity, @Nullable CapabilityDispatcher other) {
        return entity.areCapsCompatible(other);
    }

    @Nonnull
    public static <T> LazyOptional<T> getCapability(@Nonnull Entity entity, @Nonnull Capability<T> cap, @Nullable Direction side) {
        return entity.getCapability(cap, side);
    }

    @Nonnull
    public static <T> LazyOptional<T> getCapability(@Nonnull Entity entity, @Nonnull Capability<T> cap) {
        return entity.getCapability(cap);
    }

    public static Entity getEntity(@Nonnull Entity entity) {
        return entity.getEntity();
    }

    public static void deserializeNBT(@Nonnull Entity entity, CompoundNBT nbt) {
        entity.deserializeNBT(nbt);
    }

    public static CompoundNBT serializeNBT(@Nonnull Entity entity) {
        return entity.serializeNBT();
    }

    public static boolean shouldRiderSit(@Nonnull Entity entity) {
        return entity.shouldRiderSit();
    }

    public static ItemStack getPickedResult(@Nonnull Entity entity, RayTraceResult target) {
        return entity.getPickedResult(target);
    }

    public static boolean canRiderInteract(@Nonnull Entity entity) {
        return entity.canRiderInteract();
    }

    public static boolean canBeRiddenInWater(@Nonnull Entity entity, Entity rider) {
        return entity.canBeRiddenInWater(rider);
    }

    public static EntityClassification getClassification(@Nonnull Entity entity, boolean forSpawnCount) {
        return entity.getClassification(forSpawnCount);
    }

}
