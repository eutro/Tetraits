package TetraitsAPI;

import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.item.ItemFrameEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.tags.NetworkTagManager;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityDispatcher;
import net.minecraftforge.common.capabilities.CapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Wrap all ItemStack methods using mapped names.
 */
@SuppressWarnings("unused")
public class ItemStackHelper {

    public static final ItemStack EMPTY = ItemStack.EMPTY;

    public static ItemStack read(CompoundNBT compound) {
        return ItemStack.read(compound);
    }

    public static boolean isEmpty(@Nonnull ItemStack stack) {
        return stack.isEmpty();
    }

    public static ItemStack split(@Nonnull ItemStack stack, int amount) {
        return stack.split(amount);
    }

    public static Item getItem(@Nonnull ItemStack stack) {
        return stack.getItem();
    }

    public static ActionResultType onItemUse(@Nonnull ItemStack stack, ItemUseContext context) {
        return stack.onItemUse(context);
    }

    public static ActionResultType onItemUseFirst(@Nonnull ItemStack stack, ItemUseContext context) {
        return stack.onItemUseFirst(context);
    }

    public static float getDestroySpeed(@Nonnull ItemStack stack, BlockState blockIn) {
        return stack.getDestroySpeed(blockIn);
    }

    public static ActionResult<ItemStack> useItemRightClick(@Nonnull ItemStack stack, World worldIn, PlayerEntity playerIn, Hand hand) {
        return stack.useItemRightClick(worldIn, playerIn, hand);
    }

    public static ItemStack onItemUseFinish(@Nonnull ItemStack stack, World worldIn, LivingEntity entityLiving) {
        return stack.onItemUseFinish(worldIn, entityLiving);
    }

    public static CompoundNBT write(@Nonnull ItemStack stack, CompoundNBT nbt) {
        return stack.write(nbt);
    }

    public static int getMaxStackSize(@Nonnull ItemStack stack) {
        return stack.getMaxStackSize();
    }

    public static boolean isStackable(@Nonnull ItemStack stack) {
        return stack.isStackable();
    }

    public static boolean isDamageable(@Nonnull ItemStack stack) {
        return stack.isDamageable();
    }

    public static boolean isDamaged(@Nonnull ItemStack stack) {
        return stack.isDamaged();
    }

    public static int getDamage(@Nonnull ItemStack stack) {
        return stack.getDamage();
    }

    public static void setDamage(@Nonnull ItemStack stack, int damage) {
        stack.setDamage(damage);
    }

    public static int getMaxDamage(@Nonnull ItemStack stack) {
        return stack.getMaxDamage();
    }

    public static boolean attemptDamageItem(@Nonnull ItemStack stack, int amount, Random rand, @Nullable ServerPlayerEntity damager) {
        return stack.attemptDamageItem(amount, rand, damager);
    }

    public static <T extends LivingEntity> void damageItem(@Nonnull ItemStack stack, int amount, T entityIn, Consumer<T> onBroken) {
        stack.damageItem(amount, entityIn, onBroken);
    }

    public static void hitEntity(@Nonnull ItemStack stack, LivingEntity entityIn, PlayerEntity playerIn) {
        stack.hitEntity(entityIn, playerIn);
    }

    public static void onBlockDestroyed(@Nonnull ItemStack stack, World worldIn, BlockState blockIn, BlockPos pos, PlayerEntity playerIn) {
        stack.onBlockDestroyed(worldIn, blockIn, pos, playerIn);
    }

    public static boolean canHarvestBlock(@Nonnull ItemStack stack, BlockState blockIn) {
        return stack.canHarvestBlock(blockIn);
    }

    public static boolean interactWithEntity(@Nonnull ItemStack stack, PlayerEntity playerIn, LivingEntity entityIn, Hand hand) {
        return stack.interactWithEntity(playerIn, entityIn, hand);
    }

    public static ItemStack copy(@Nonnull ItemStack stack) {
        return stack.copy();
    }

    public static boolean areItemStackTagsEqual(ItemStack stackA, ItemStack stackB) {
        return ItemStack.areItemStackTagsEqual(stackA, stackB);
    }

    public static boolean areItemStacksEqual(ItemStack stackA, ItemStack stackB) {
        return ItemStack.areItemStacksEqual(stackA, stackB);
    }

    public static boolean areItemsEqual(ItemStack stackA, ItemStack stackB) {
        return ItemStack.areItemsEqual(stackA, stackB);
    }

    public static boolean areItemsEqualIgnoreDurability(ItemStack stackA, ItemStack stackB) {
        return ItemStack.areItemsEqualIgnoreDurability(stackA, stackB);
    }

    public static boolean isItemEqual(@Nonnull ItemStack stack, ItemStack other) {
        return stack.isItemEqual(other);
    }

    public static boolean isItemEqualIgnoreDurability(@Nonnull ItemStack stack, ItemStack stackB) {
        return stack.isItemEqualIgnoreDurability(stackB);
    }

    public static String getTranslationKey(@Nonnull ItemStack stack) {
        return stack.getTranslationKey();
    }

    public static void inventoryTick(@Nonnull ItemStack stack, World worldIn, Entity entityIn, int inventorySlot, boolean isCurrentItem) {
        stack.inventoryTick(worldIn, entityIn, inventorySlot, isCurrentItem);
    }

    public static void onCrafting(@Nonnull ItemStack stack, World worldIn, PlayerEntity playerIn, int amount) {
        stack.onCrafting(worldIn, playerIn, amount);
    }

    public static int getUseDuration(@Nonnull ItemStack stack) {
        return stack.getUseDuration();
    }

    public static UseAction getUseAction(@Nonnull ItemStack stack) {
        return stack.getUseAction();
    }

    public static void onPlayerStoppedUsing(@Nonnull ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        stack.onPlayerStoppedUsing(worldIn, entityLiving, timeLeft);
    }

    public static boolean isCrossbowStack(@Nonnull ItemStack stack) {
        return stack.isCrossbowStack();
    }

    public static boolean hasTag(@Nonnull ItemStack stack) {
        return stack.hasTag();
    }

    @Nullable
    public static CompoundNBT getTag(@Nonnull ItemStack stack) {
        return stack.getTag();
    }

    public static CompoundNBT getOrCreateTag(@Nonnull ItemStack stack) {
        return stack.getOrCreateTag();
    }

    public static CompoundNBT getOrCreateChildTag(@Nonnull ItemStack stack, String key) {
        return stack.getOrCreateChildTag(key);
    }

    @Nullable
    public static CompoundNBT getChildTag(@Nonnull ItemStack stack, String key) {
        return stack.getChildTag(key);
    }

    public static void removeChildTag(@Nonnull ItemStack stack, String p_196083_1_) {
        stack.removeChildTag(p_196083_1_);
    }

    public static ListNBT getEnchantmentTagList(@Nonnull ItemStack stack) {
        return stack.getEnchantmentTagList();
    }

    public static void setTag(@Nonnull ItemStack stack, @Nullable CompoundNBT nbt) {
        stack.setTag(nbt);
    }

    public static ITextComponent getDisplayName(@Nonnull ItemStack stack) {
        return stack.getDisplayName();
    }

    public static ItemStack setDisplayName(@Nonnull ItemStack stack, @Nullable ITextComponent name) {
        return stack.setDisplayName(name);
    }

    public static void clearCustomName(@Nonnull ItemStack stack) {
        stack.clearCustomName();
    }

    public static boolean hasDisplayName(@Nonnull ItemStack stack) {
        return stack.hasDisplayName();
    }

    @OnlyIn(Dist.CLIENT)
    public static List<ITextComponent> getTooltip(@Nonnull ItemStack stack, @Nullable PlayerEntity playerIn, ITooltipFlag advanced) {
        return stack.getTooltip(playerIn, advanced);
    }

    @OnlyIn(Dist.CLIENT)
    public static void addEnchantmentTooltips(List<ITextComponent> p_222120_0_, ListNBT p_222120_1_) {
        ItemStack.addEnchantmentTooltips(p_222120_0_, p_222120_1_);
    }

    public static boolean hasEffect(@Nonnull ItemStack stack) {
        return stack.hasEffect();
    }

    public static Rarity getRarity(@Nonnull ItemStack stack) {
        return stack.getRarity();
    }

    public static boolean isEnchantable(@Nonnull ItemStack stack) {
        return stack.isEnchantable();
    }

    public static void addEnchantment(@Nonnull ItemStack stack, Enchantment ench, int level) {
        stack.addEnchantment(ench, level);
    }

    public static boolean isEnchanted(@Nonnull ItemStack stack) {
        return stack.isEnchanted();
    }

    public static void setTagInfo(@Nonnull ItemStack stack, String key, INBT value) {
        stack.setTagInfo(key, value);
    }

    public static boolean isOnItemFrame(@Nonnull ItemStack stack) {
        return stack.isOnItemFrame();
    }

    public static void setItemFrame(@Nonnull ItemStack stack, @Nullable ItemFrameEntity frame) {
        stack.setItemFrame(frame);
    }

    @Nullable
    public static ItemFrameEntity getItemFrame(@Nonnull ItemStack stack) {
        return stack.getItemFrame();
    }

    public static int getRepairCost(@Nonnull ItemStack stack) {
        return stack.getRepairCost();
    }

    public static void setRepairCost(@Nonnull ItemStack stack, int cost) {
        stack.setRepairCost(cost);
    }

    public static Multimap<String, AttributeModifier> getAttributeModifiers(@Nonnull ItemStack stack, EquipmentSlotType equipmentSlot) {
        return stack.getAttributeModifiers(equipmentSlot);
    }

    public static void addAttributeModifier(@Nonnull ItemStack stack, String attributeName, AttributeModifier modifier, @Nullable EquipmentSlotType equipmentSlot) {
        stack.addAttributeModifier(attributeName, modifier, equipmentSlot);
    }

    public static ITextComponent getTextComponent(@Nonnull ItemStack stack) {
        return stack.getTextComponent();
    }

    public static boolean canDestroy(@Nonnull ItemStack stack, NetworkTagManager p_206848_1_, CachedBlockInfo p_206848_2_) {
        return stack.canDestroy(p_206848_1_, p_206848_2_);
    }

    public static boolean canPlaceOn(@Nonnull ItemStack stack, NetworkTagManager p_206847_1_, CachedBlockInfo p_206847_2_) {
        return stack.canPlaceOn(p_206847_1_, p_206847_2_);
    }

    public static int getAnimationsToGo(@Nonnull ItemStack stack) {
        return stack.getAnimationsToGo();
    }

    public static void setAnimationsToGo(@Nonnull ItemStack stack, int animations) {
        stack.setAnimationsToGo(animations);
    }

    public static int getCount(@Nonnull ItemStack stack) {
        return stack.getCount();
    }

    public static void setCount(@Nonnull ItemStack stack, int count) {
        stack.setCount(count);
    }

    public static void grow(@Nonnull ItemStack stack, int count) {
        stack.grow(count);
    }

    public static void shrink(@Nonnull ItemStack stack, int count) {
        stack.shrink(count);
    }

    public static void onItemUsed(@Nonnull ItemStack stack, World worldIn, LivingEntity livingEntityIn, int countIn) {
        stack.onItemUsed(worldIn, livingEntityIn, countIn);
    }

    public static boolean isFood(@Nonnull ItemStack stack) {
        return stack.isFood();
    }

    public static void deserializeNBT(@Nonnull ItemStack stack, CompoundNBT nbt) {
        stack.deserializeNBT(nbt);
    }

    public static SoundEvent getDrinkSound(@Nonnull ItemStack stack) {
        return stack.getDrinkSound();
    }

    public static SoundEvent getEatSound(@Nonnull ItemStack stack) {
        return stack.getEatSound();
    }

    public static boolean areCapsCompatible(@Nonnull ItemStack stack, CapabilityProvider<ItemStack> other) {
        return stack.areCapsCompatible(other);
    }

    public static boolean areCapsCompatible(@Nonnull ItemStack stack, @Nullable CapabilityDispatcher other) {
        return stack.areCapsCompatible(other);
    }

    @Nonnull
    public static <T> LazyOptional<T> getCapability(@Nonnull ItemStack stack, @Nonnull Capability<T> cap, @Nullable Direction side) {
        return stack.getCapability(cap, side);
    }

    @Nonnull
    public static <T> LazyOptional<T> getCapability(@Nonnull ItemStack stack, @Nonnull Capability<T> cap) {
        return stack.getCapability(cap);
    }

    public static ItemStack getStack(@Nonnull ItemStack stack) {
        return stack.getStack();
    }

    public static ItemStack getContainerItem(@Nonnull ItemStack stack) {
        return stack.getContainerItem();
    }

    public static boolean hasContainerItem(@Nonnull ItemStack stack) {
        return stack.hasContainerItem();
    }

    public static int getBurnTime(@Nonnull ItemStack stack) {
        return stack.getBurnTime();
    }

    public static int getHarvestLevel(@Nonnull ItemStack stack, ToolType tool, @Nullable PlayerEntity player, @Nullable BlockState state) {
        return stack.getHarvestLevel(tool, player, state);
    }

    public static Set<ToolType> getToolTypes(@Nonnull ItemStack stack) {
        return stack.getToolTypes();
    }

    public static CompoundNBT serializeNBT(@Nonnull ItemStack stack) {
        return stack.serializeNBT();
    }

    public static boolean onBlockStartBreak(@Nonnull ItemStack stack, BlockPos pos, PlayerEntity player) {
        return stack.onBlockStartBreak(pos, player);
    }

    public static boolean shouldCauseBlockBreakReset(@Nonnull ItemStack stack, ItemStack newStack) {
        return stack.shouldCauseBlockBreakReset(newStack);
    }

    public static boolean canApplyAtEnchantingTable(@Nonnull ItemStack stack, Enchantment enchantment) {
        return stack.canApplyAtEnchantingTable(enchantment);
    }

    public static int getItemEnchantability(@Nonnull ItemStack stack) {
        return stack.getItemEnchantability();
    }

    @Nullable
    public static EquipmentSlotType getEquipmentSlot(@Nonnull ItemStack stack) {
        return stack.getEquipmentSlot();
    }

    public static boolean canDisableShield(@Nonnull ItemStack stack, ItemStack shield, LivingEntity entity, LivingEntity attacker) {
        return stack.canDisableShield(shield, entity, attacker);
    }

    public static boolean isShield(@Nonnull ItemStack stack, @Nullable LivingEntity entity) {
        return stack.isShield(entity);
    }

    public static boolean onEntitySwing(@Nonnull ItemStack stack, LivingEntity entity) {
        return stack.onEntitySwing(entity);
    }

    public static void onUsingTick(@Nonnull ItemStack stack, LivingEntity player, int count) {
        stack.onUsingTick(player, count);
    }

    public static int getEntityLifespan(@Nonnull ItemStack stack, World world) {
        return stack.getEntityLifespan(world);
    }

    public static boolean onEntityItemUpdate(@Nonnull ItemStack stack, ItemEntity entity) {
        return stack.onEntityItemUpdate(entity);
    }

    public static float getXpRepairRatio(@Nonnull ItemStack stack) {
        return stack.getXpRepairRatio();
    }

    public static void onArmorTick(@Nonnull ItemStack stack, World world, PlayerEntity player) {
        stack.onArmorTick(world, player);
    }

    public static void onHorseArmorTick(@Nonnull ItemStack stack, World world, MobEntity horse) {
        stack.onHorseArmorTick(world, horse);
    }

    public static boolean isBeaconPayment(@Nonnull ItemStack stack) {
        return stack.isBeaconPayment();
    }

    public static boolean canEquip(@Nonnull ItemStack stack, EquipmentSlotType armorType, Entity entity) {
        return stack.canEquip(armorType, entity);
    }

    public static boolean isBookEnchantable(@Nonnull ItemStack stack, ItemStack book) {
        return stack.isBookEnchantable(book);
    }

    public static boolean onDroppedByPlayer(@Nonnull ItemStack stack, PlayerEntity player) {
        return stack.onDroppedByPlayer(player);
    }

    public static String getHighlightTip(@Nonnull ItemStack stack, String displayName) {
        return stack.getHighlightTip(displayName);
    }

    @Nullable
    public static CompoundNBT getShareTag(@Nonnull ItemStack stack) {
        return stack.getShareTag();
    }

    public static void readShareTag(@Nonnull ItemStack stack, @Nullable CompoundNBT nbt) {
        stack.readShareTag(nbt);
    }

    public static boolean doesSneakBypassUse(@Nonnull ItemStack stack, IWorldReader world, BlockPos pos, PlayerEntity player) {
        return stack.doesSneakBypassUse(world, pos, player);
    }

    public static boolean areShareTagsEqual(@Nonnull ItemStack stack, ItemStack other) {
        return stack.areShareTagsEqual(other);
    }

    public static boolean equals(@Nonnull ItemStack stack, ItemStack other, boolean limitTags) {
        return stack.equals(other, limitTags);
    }

    public static boolean isRepairable(@Nonnull ItemStack stack) {
        return stack.isRepairable();
    }

}
