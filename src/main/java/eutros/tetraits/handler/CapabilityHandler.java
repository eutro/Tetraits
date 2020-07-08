package eutros.tetraits.handler;

import clojure.lang.AFn;
import clojure.lang.IFn;
import eutros.tetraits.Tetraits;
import eutros.tetraits.data.DataManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import se.mickelus.tetra.items.modular.IItemModular;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class CapabilityHandler {

    public static void init() {
        MinecraftForge.EVENT_BUS.addGenericListener(ItemStack.class, CapabilityHandler::onAttachCaps);
    }

    private static void onAttachCaps(AttachCapabilitiesEvent<ItemStack> evt) {
        ItemStack stack = evt.getObject();
        if(stack.getItem() instanceof IItemModular) {
            evt.addCapability(new ResourceLocation(Tetraits.MOD_ID, "data_cap"), new DataCapability(stack));
        }
    }

    private static class DataCapability implements ICapabilityProvider {

        private final ItemStack stack;
        private final Map<Capability<?>, Object> capMap = new HashMap<>();
        private final Map<ResourceLocation, Object> cache = new HashMap<>();

        public DataCapability(ItemStack stack) {
            this.stack = stack;
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            DataManager dm = DataManager.getInstance();
            try {
                @SuppressWarnings("unchecked")
                T ret = (T) capMap.computeIfAbsent(cap,
                        c -> {
                            for(ResourceLocation rl : dm.moduleExt.getCaps(stack)) {
                                IFn fn = dm.capData.capMap.get(rl);
                                if(fn == null) {
                                    return null;
                                }

                                return fn.invoke(stack, cap, side, new AFn() {
                                    @Override
                                    public Object invoke() {
                                        return cache.get(rl);
                                    }

                                    @Override
                                    public Object invoke(Object arg1) {
                                        return cache.put(rl, arg1);
                                    }
                                });
                            }
                            return null;
                        });
                if(ret != null) {
                    return LazyOptional.of(() -> ret);
                }
            } catch(ClassCastException e) {
                Tetraits.LOGGER.error("Wrong type returned for capability: {}.", cap);
            }

            return LazyOptional.empty();
        }

    }

}
