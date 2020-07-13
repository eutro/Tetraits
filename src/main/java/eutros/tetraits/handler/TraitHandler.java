package eutros.tetraits.handler;

import clojure.lang.AFn;
import clojure.lang.ArraySeq;
import clojure.lang.IFn;
import eutros.tetraits.Tetraits;
import eutros.tetraits.data.DataManager;
import eutros.tetraits.data.TraitData;
import eutros.tetraits.util.TetraHelper;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.commons.lang3.ArrayUtils;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class TraitHandler {

    private enum Type {
        /**
         * @see ArrowLooseEvent
         */
        ARROW_LOOSE(
                ItemStack.class,    // stack
                World.class,        // world
                PlayerEntity.class, // player
                int.class,          // charge
                boolean.class       // hasAmmo
        ),
        /**
         * @see ArrowNockEvent
         */
        ARROW_NOCK(
                ItemStack.class,    // stack
                World.class,        // world
                PlayerEntity.class, // player
                Hand.class,         // hand
                boolean.class,      // hasAmmo
                ActionResult.class, // action (ItemStack)
                SetFunc.class       // setAction (ActionResult<ItemStack>)
        ),
        /**
         * @see LivingAttackEvent
         */
        ATTACK(
                ItemStack.class,    // stack
                World.class,        // world
                PlayerEntity.class, // player
                Entity.class        // target
        ),
        /**
         * @see BlockEvent.BreakEvent
         */
        BREAK_BLOCK(
                ItemStack.class,    // stack
                World.class,        // world
                PlayerEntity.class, // player
                BlockPos.class,     // pos
                int.class,          // expToDrop
                SetFunc.class       // setExpToDrop (int)
        ),
        /**
         * @see PlayerEvent.BreakSpeed
         */
        BREAK_SPEED(
                ItemStack.class,    // stack
                World.class,        // world
                PlayerEntity.class, // player
                BlockPos.class,     // pos
                BlockState.class,   // state
                float.class,        // newSpeed
                float.class,        // originalSpeed
                SetFunc.class       // setNewSpeed (float)
        ),
        /**
         * @see CriticalHitEvent
         */
        CRIT(
                ItemStack.class,    // stack
                World.class,        // world
                PlayerEntity.class, // player
                Entity.class,       // target
                boolean.class,      // isVanillaCritical
                float.class,        // damageModifier
                float.class,        // oldDamageModifier
                SetFunc.class       // setDamageModifier (float)
        ),
        /**
         * @see LivingDamageEvent
         */
        DAMAGE(
                ItemStack.class,    // stack
                World.class,        // world
                LivingEntity.class, // entity
                DamageSource.class, // source
                float.class,        // amount
                SetFunc.class       // setAmount (float)
        ),
        /**
         * @see AttackEntityEvent
         */
        ENTITY_ATTACK(
                ItemStack.class,    // stack
                World.class,        // world
                PlayerEntity.class, // player
                Entity.class        // target
        ),
        /**
         * @see PlayerInteractEvent.EntityInteract
         */
        ENTITY_INTERACT(
                ItemStack.class,    // stack
                World.class,        // world
                PlayerEntity.class, // player
                Hand.class,         // hand
                Entity.class        // target
        ),
        /**
         * @see PlayerInteractEvent.EntityInteractSpecific
         */
        ENTITY_INTERACT_SPECIFIC(
                ItemStack.class,    // stack
                World.class,        // world
                PlayerEntity.class, // player
                Hand.class,         // hand
                Entity.class,       // target
                Vec3d.class         // localPos
        ),
        /**
         * @see LivingExperienceDropEvent
         */
        EXPERIENCE_DROP(
                ItemStack.class,    // stack
                World.class,        // world
                PlayerEntity.class, // player
                LivingEntity.class, // entity
                int.class,          // droppedExperience
                int.class           // originalExperience
        ),
        /**
         * @see LivingEvent
         * @see #onGenericEvent(LivingEvent)
         */
        GENERIC(
                ItemStack.class,    // stack
                World.class,        // world
                LivingEntity.class  // entity
        ),
        /**
         * @see LivingHurtEvent
         */
        HURT(
                ItemStack.class,    // stack
                World.class,        // world
                LivingEntity.class, // entity
                DamageSource.class, // source
                float.class,        // amount
                SetFunc.class       // setAmount (float)
        ),
        /**
         * @see Item#inventoryTick(ItemStack, World, Entity, int, boolean)
         */
        INVENTORY_TICK(
                ItemStack.class,    // stack
                World.class,        // world
                LivingEntity.class, // entity
                int.class,          // slot
                boolean.class       // isSelected
        ),
        /**
         * @see LivingEvent.LivingJumpEvent
         */
        JUMP(
                ItemStack.class,    // stack
                World.class,        // world
                LivingEntity.class  // entity
        ),
        /**
         * @see PlayerInteractEvent.LeftClickBlock
         */
        LEFT_CLICK_BLOCK(
                ItemStack.class,    // stack
                World.class,        // world
                PlayerEntity.class, // player
                Hand.class,         // hand
                BlockPos.class,     // pos
                Direction.class,    // face
                Event.Result.class, // useBlock
                SetFunc.class,      // setUseBlock (Event.Result)
                Event.Result.class, // useItem
                SetFunc.class       // setUseItem (Event.Result)
        ),
        /**
         * @see PlayerInteractEvent.LeftClickEmpty
         */
        LEFT_CLICK_EMPTY(
                ItemStack.class,    // stack
                World.class,        // world
                PlayerEntity.class, // player
                Hand.class          // hand
        ),
        /**
         * @see PlayerInteractEvent.RightClickEmpty
         */
        RIGHT_CLICK_EMPTY(
                ItemStack.class,    // stack
                World.class,        // world
                PlayerEntity.class, // player
                Hand.class          // hand
        ),
        /**
         * @see PlayerInteractEvent.RightClickBlock
         */
        RIGHT_CLICK_BLOCK(
                ItemStack.class,    // stack
                World.class,        // world
                PlayerEntity.class, // player
                Hand.class,         // hand
                BlockPos.class,     // pos
                Direction.class,    // face
                Event.Result.class, // useBlock
                SetFunc.class,      // setUseBlock (Event.Result)
                Event.Result.class, // useItem
                SetFunc.class       // setUseItem (Event.Result)
        ),
        /**
         * @see PlayerInteractEvent.RightClickItem
         */
        RIGHT_CLICK_ITEM(
                ItemStack.class,    // stack
                World.class,        // world
                PlayerEntity.class, // player
                Hand.class,         // hand
                BlockPos.class,     // pos
                Direction.class     // face
        ),
        /**
         * @see ItemTooltipEvent
         */
        TOOLTIP(
                ItemStack.class,    // stack
                World.class,        // world
                PlayerEntity.class, // player
                ITooltipFlag.class, // flags
                List.class          // tooltip (ITextComponent)
        );

        @SuppressWarnings("unused")
        Type(Class<?>... classes) {
        }
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
     * This includes most of the events below as well.
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
                evt.getEntityLiving(),
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
                evt.getPlayer(),
                evt.getItemStack(),
                evt.getHand(),
                evt.getPos(),
                evt.getFace(),
                evt.getUseBlock(),
                SetFunc.of(evt::setUseBlock),
                evt.getUseItem(),
                SetFunc.of(evt::setUseItem));
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onLeftClickEmpty(PlayerInteractEvent.LeftClickEmpty evt) {
        invokeSimple(Type.LEFT_CLICK_EMPTY,
                evt.getPlayer(),
                evt.getItemStack(),
                evt.getHand());
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock evt) {
        invokeSimple(Type.RIGHT_CLICK_BLOCK,
                evt.getPlayer(),
                evt.getItemStack(),
                evt.getHand(),
                evt.getPos(),
                evt.getFace(),
                evt.getUseBlock(),
                SetFunc.of(evt::setUseBlock),
                evt.getUseItem(),
                SetFunc.of(evt::setUseItem));
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onRightClickEmpty(PlayerInteractEvent.RightClickEmpty evt) {
        invokeSimple(Type.RIGHT_CLICK_EMPTY,
                evt.getPlayer(),
                evt.getItemStack(),
                evt.getHand());
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onRightClickItem(PlayerInteractEvent.RightClickItem evt) {
        invokeSimple(Type.RIGHT_CLICK_ITEM,
                evt.getPlayer(),
                evt.getItemStack(),
                evt.getHand(),
                evt.getPos(),
                evt.getFace());
    }


    @SubscribeEvent(priority = EventPriority.LOW)
    public void onEntityInteract(PlayerInteractEvent.EntityInteract evt) {
        invokeSimple(Type.ENTITY_INTERACT,
                evt.getPlayer(),
                evt.getItemStack(),
                evt.getHand(),
                evt.getTarget());
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onEntityInteractSpecific(PlayerInteractEvent.EntityInteractSpecific evt) {
        invokeSimple(Type.ENTITY_INTERACT_SPECIFIC,
                evt.getPlayer(),
                evt.getItemStack(),
                evt.getHand(),
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
                evt.getPlayer(),
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
    @SubscribeEvent(priority = EventPriority.LOW)
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
