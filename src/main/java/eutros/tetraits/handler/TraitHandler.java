package eutros.tetraits.handler;

import clojure.lang.IFn;
import eutros.tetraits.Tetraits;
import eutros.tetraits.data.DataManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class TraitHandler {

    public static void init() {
        MinecraftForge.EVENT_BUS.addListener(TraitHandler::tick);
    }

    public static void tick(LivingEvent.LivingUpdateEvent evt) {
        LivingEntity living = evt.getEntityLiving();
        if(!(living instanceof PlayerEntity)) return;

        Object lock = new Object();
        PlayerEntity player = (PlayerEntity) living;
        IntStream.range(0, player.inventory.getSizeInventory())
                .parallel()
                .forEach(slot -> {
                    ItemStack stack = player.inventory.getStackInSlot(slot);
                    if(stack.isEmpty()) return;
                    List<ResourceLocation> traits = DataManager.getInstance().moduleExt.getTraits(stack);
                    traits.parallelStream()
                            .forEach(rl -> {
                                IFn action = ActionHandler.instance.getAction(rl);
                                if(action == null) return;

                                synchronized(lock) {
                                    try {
                                        Optional.ofNullable((IFn) action.invoke("INVENTORY_TICK"))
                                                .ifPresent(fn -> fn.invoke(
                                                        stack,
                                                        player.world,
                                                        player,
                                                        slot,
                                                        player.inventory.currentItem == slot)
                                                );
                                    } catch(ClassCastException e) {
                                        Tetraits.LOGGER.error("Bad function: {}, didn't return a function itself.", rl.toString());
                                        ActionHandler.instance.remove(rl);
                                    } catch(Throwable t) {
                                        Tetraits.LOGGER.debug("Error: {}", t.getMessage());
                                    }
                                }
                            });
                });
    }

}
