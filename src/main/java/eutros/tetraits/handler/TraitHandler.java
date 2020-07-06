package eutros.tetraits.handler;

import TetraitsAPI.Events;
import clojure.lang.IFn;
import eutros.tetraits.Tetraits;
import eutros.tetraits.data.DataManager;
import eutros.tetraits.util.StreamHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;

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
        StreamHelper.mapFirst(
                StreamHelper.split(
                        IntStream.range(0, player.inventory.getSizeInventory())
                                .parallel()
                                .mapToObj(player.inventory::getStackInSlot)
                                .filter(s -> !s.isEmpty())),
                DataManager.getInstance().moduleExt::getTraits
        )
                .forEach(pair -> {
                    ItemStack stack = pair.getRight();
                    pair.getLeft().parallelStream()
                            .forEach(rl -> {
                                        IFn action = ActionHandler.instance.getAction(rl);
                                        if(action == null) return;

                                        synchronized(lock) {
                                            try {
                                                Optional.ofNullable((IFn) action.invoke(Events.INVENTORY_TICK.name()))
                                                        .ifPresent(fn -> fn.invoke(player, stack));
                                            } catch(ClassCastException e) {
                                                Tetraits.LOGGER.error("Bad function: {}, didn't return a function itself.", rl.toString());
                                                ActionHandler.instance.remove(rl);
                                            } catch(Throwable t) {
                                                Tetraits.LOGGER.debug("Error: {}", t.getMessage());
                                            }
                                        }
                                    }
                            );
                });
    }

}
