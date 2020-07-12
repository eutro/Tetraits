package eutros.tetraits.handler;

import clojure.lang.AFn;
import clojure.lang.ArraySeq;
import clojure.lang.IFn;
import eutros.tetraits.Tetraits;
import eutros.tetraits.data.DataManager;
import eutros.tetraits.data.TraitData;
import eutros.tetraits.util.TetraHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Optional;
import java.util.function.Consumer;

public class TraitHandler {

    private enum Type {
        ARROW_LOOSE,
        ARROW_NOCK,
        ATTACK,
        BREAK_BLOCK,
        BREAK_SPEED,
        CRIT,
        DAMAGE,
        ENTITY_ATTACK,
        ENTITY_INTERACT,
        ENTITY_INTERACT_SPECIFIC,
        EXPERIENCE_DROP,
        GENERIC,
        HURT,
        INVENTORY_TICK,
        JUMP,
        LEFT_CLICK_BLOCK,
        LEFT_CLICK_EMPTY,
        RIGHT_CLICK_AIR,
        RIGHT_CLICK_BLOCK,
        RIGHT_CLICK_ITEM,
        TOOLTIP
    }

    private static TraitHandler instance = new TraitHandler();

    public static void init() {
        MinecraftForge.EVENT_BUS.register(instance);
    }

    /**
     * Called on every item in the inventory every tick.
     */
    @SubscribeEvent(priority = EventPriority.LOW)
    public void tick(LivingEvent.LivingUpdateEvent evt) {
        LivingEntity living = evt.getEntityLiving();
        if(!(living instanceof PlayerEntity)) return;

        PlayerEntity player = (PlayerEntity) living;
        int bound = player.inventory.getSizeInventory();
        for(int slot = 0; slot < bound; slot++) {
            invokeSimple(Type.INVENTORY_TICK,
                    player,
                    player.inventory.getStackInSlot(slot),
                    slot,
                    player.inventory.currentItem == slot);
        }
    }

    /**
     * This includes all the events below as well.
     * <p>
     * This is just for completeness.
     */
    @SubscribeEvent(priority = EventPriority.LOW)
    public void onGenericEvent(LivingEvent evt) {
        LivingEntity entity = evt.getEntityLiving();
        if(entity == null) return;
        for(Hand hand : Hand.values()) {
            invokeTraits(Type.GENERIC, entity.getHeldItem(hand), fn -> fn.invoke(evt, hand));
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onExperienceDrop(LivingExperienceDropEvent evt) {
        invokeSimple(Type.EXPERIENCE_DROP,
                evt,
                evt.getAttackingPlayer(),
                evt.getDroppedExperience(),
                evt.getOriginalExperience());
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onLivingAttack(LivingAttackEvent evt) {
        Entity source = evt.getSource().getTrueSource();
        if(!(source instanceof LivingEntity)) return;
        LivingEntity living = (LivingEntity) source;
        invokeSimple(Type.ATTACK,
                living,
                living.getHeldItemMainhand(),
                evt.getEntityLiving(),
                evt.getSource(),
                evt.getAmount());
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onLivingHurt(LivingHurtEvent evt) {
        invokeSimple(Type.HURT,
                evt,
                evt.getSource(),
                evt.getAmount(),
                SetFunc.of(evt::setAmount));
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onLivingDamage(LivingDamageEvent evt) {
        invokeSimple(Type.DAMAGE,
                evt,
                evt.getSource(),
                evt.getAmount(),
                SetFunc.of(evt::setAmount));
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onLivingJump(LivingEvent.LivingJumpEvent evt) {
        invokeSimple(Type.JUMP, evt);
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onCriticalHit(CriticalHitEvent evt) {
        invokeSimple(Type.CRIT,
                evt,
                evt.getTarget(),
                evt.isVanillaCritical(),
                evt.getDamageModifier(),
                evt.getOldDamageModifier(),
                SetFunc.of(evt::setDamageModifier));
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock evt) {
        invokeSimple(Type.LEFT_CLICK_BLOCK,
                evt.getEntityLiving(),
                evt.getItemStack(),
                evt.getHand(),
                evt.getPos(),
                evt.getFace(),
                evt.getUseBlock(),
                evt.getUseItem());
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onLeftClickEmpty(PlayerInteractEvent.LeftClickEmpty evt) {
        invokeSimple(Type.LEFT_CLICK_EMPTY,
                evt.getEntityLiving(),
                evt.getItemStack(),
                evt.getHand(),
                evt.getPos(),
                evt.getFace());
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock evt) {
        invokeSimple(Type.RIGHT_CLICK_BLOCK,
                evt.getEntityLiving(),
                evt.getItemStack(),
                evt.getHand(),
                evt.getPos(),
                evt.getFace(),
                evt.getUseBlock(),
                evt.getUseItem());
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onRightClickEmpty(PlayerInteractEvent.RightClickEmpty evt) {
        invokeSimple(Type.RIGHT_CLICK_AIR,
                evt.getEntityLiving(),
                evt.getItemStack(),
                evt.getHand(),
                evt.getPos(),
                evt.getFace());
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onRightClickItem(PlayerInteractEvent.RightClickItem evt) {
        invokeSimple(Type.RIGHT_CLICK_ITEM,
                evt.getEntityLiving(),
                evt.getItemStack(),
                evt.getHand(),
                evt.getPos(),
                evt.getFace());
    }


    @SubscribeEvent(priority = EventPriority.LOW)
    public void onEntityInteract(PlayerInteractEvent.EntityInteract evt) {
        invokeSimple(Type.ENTITY_INTERACT,
                evt.getEntityLiving(),
                evt.getItemStack(),
                evt.getHand(),
                evt.getPos(),
                evt.getFace(),
                evt.getTarget());
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onEntityInteractSpecific(PlayerInteractEvent.EntityInteractSpecific evt) {
        invokeSimple(Type.ENTITY_INTERACT_SPECIFIC,
                evt.getEntityLiving(),
                evt.getItemStack(),
                evt.getHand(),
                evt.getPos(),
                evt.getFace(),
                evt.getTarget(),
                evt.getLocalPos());
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onArrowNock(ArrowNockEvent evt) {
        invokeSimple(Type.ARROW_NOCK,
                evt.getPlayer(),
                evt.getBow(),
                evt.getHand(),
                evt.hasAmmo(),
                evt.getAction(),
                SetFunc.of(evt::setAction));
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onArrowLoose(ArrowLooseEvent evt) {
        invokeSimple(Type.ARROW_LOOSE,
                evt.getEntityLiving(),
                evt.getBow(),
                evt.getCharge(),
                evt.hasAmmo());
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onEntityAttack(AttackEntityEvent evt) {
        invokeSimple(Type.ENTITY_ATTACK,
                evt,
                evt.getTarget());
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onBreakSpeed(PlayerEvent.BreakSpeed evt) {
        invokeSimple(Type.BREAK_SPEED,
                evt,
                evt.getPos(),
                evt.getState(),
                evt.getNewSpeed(),
                evt.getOriginalSpeed(),
                SetFunc.of(evt::setNewSpeed));
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onBreak(BlockEvent.BreakEvent evt) {
        invokeSimple(Type.BREAK_BLOCK,
                evt.getPlayer(),
                evt.getPlayer().getHeldItemMainhand(),
                evt.getPos(),
                evt.getExpToDrop(),
                SetFunc.of(evt::setExpToDrop));
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onTooltip(ItemTooltipEvent evt) {
        invokeSimple(Type.TOOLTIP,
                evt.getPlayer(),
                evt.getItemStack(),
                evt.getFlags(),
                evt.getToolTip());
    }

    private void invokeSimple(Type type, LivingEvent evt, Object... params) {
        LivingEntity entity = evt.getEntityLiving();
        if(entity == null) return;
        invokeSimple(type, entity, entity.getHeldItemMainhand(), params);
    }

    private void invokeSimple(Type type, LivingEntity entity, ItemStack stack, Object... params) {
        invokeTraits(type, stack, fn -> {
            ArraySeq seq = ArraySeq.create(
                    ArrayUtils.addAll(new Object[] {
                                    stack,
                                    entity.world,
                                    entity
                            },
                            params));
            fn.applyTo(seq);
        });
    }

    private void invokeTraits(Type type, ItemStack stack, Consumer<IFn> invoker) {
        if(stack.isEmpty()) return;
        TetraHelper.forAllFrom(stack,
                TraitData.getInstance(),
                (rl, fn, extra) -> {
                    invokeTrait(invoker, type, rl, fn, extra);
                    return null;
                });
    }

    private void invokeTrait(Consumer<IFn> invoker, Type type, ResourceLocation rl, IFn action, Object extra) {
        try {
            Optional.ofNullable((IFn) action.invoke(type.name(), extra))
                    .ifPresent(invoker);
        } catch(ClassCastException e) {
            Tetraits.LOGGER.error("Bad function: {}, didn't return a function itself.", rl.toString());
            DataManager.getInstance().traitData.data.remove(rl);
        } catch(Throwable t) {
            Tetraits.LOGGER.error("Error executing trait {}: {}", rl, t.getMessage());
        }
    }

    private static class SetFunc<T> extends AFn {

        private final Consumer<T> setter;

        private SetFunc(Consumer<T> setter) {
            this.setter = setter;
        }

        private static <T> SetFunc<T> of(Consumer<T> setter) {
            return new SetFunc<>(setter);
        }

        @SuppressWarnings("unchecked")
        @Override
        public Object invoke(Object newVal) {
            setter.accept((T) newVal);
            return newVal;
        }

    }

}
