package eutros.tetraits.handler;

import clojure.lang.ArityException;
import eutros.tetraits.Tetraits;
import eutros.tetraits.data.DataManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent;

import java.util.Objects;

public class TickHandler {

    public static void tick(LivingEvent.LivingUpdateEvent evt) {
        LivingEntity living = evt.getEntityLiving();
        ItemStack stack = living.getHeldItemMainhand();

        Object lock = new Object();
        DataManager.getInstance().moduleExt
                .getTraits(stack)
                .parallelStream()
                .map(ActionHandler.instance::getAction)
                .filter(Objects::nonNull)
                .forEach(action -> {
                    try {
                        try {
                            synchronized(lock) {
                                action.invoke(evt);
                            }
                        } catch(ArityException ignored) {
                            try {
                                synchronized(lock) {
                                    action.invoke();
                                }
                            } catch(ArityException ignored1) {
                            }
                        }
                    } catch(Throwable e) {
                        Tetraits.LOGGER.error("Error:", e);
                    }
                });
    }

}
