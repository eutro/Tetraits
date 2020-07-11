package eutros.tetraits.handler;

import clojure.lang.AFn;
import eutros.tetraits.Tetraits;
import eutros.tetraits.data.CapData;
import eutros.tetraits.util.TetraHelper;
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
        private final Map<ResourceLocation, Object> cache = new HashMap<>();

        public DataCapability(ItemStack stack) {
            this.stack = stack;
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            return TetraHelper.forAllFrom(stack,
                    CapData.getInstance(),
                    (rl, fn, extra) -> {
                        try {
                            @SuppressWarnings("unchecked")
                            T ret = (T) fn.invoke(stack,
                                    cap,
                                    side,
                                    new AFn() {
                                        @Override
                                        public Object invoke() {
                                            return cache.get(rl);
                                        }

                                        @Override
                                        public Object invoke(Object arg1) {
                                            return cache.put(rl, arg1);
                                        }
                                    },
                                    extra);

                            if(ret != null) {
                                return LazyOptional.of(() -> ret);
                            }
                        } catch(ClassCastException e) {
                            Tetraits.LOGGER.error("Wrong type returned for capability: {}.", cap);
                        }

                        return null;
                    })
                    .orElseGet(LazyOptional::empty);
        }

    }

}
