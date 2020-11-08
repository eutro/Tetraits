package eutros.tetraits.handler;

import clojure.lang.AFn;
import clojure.lang.IFn;
import eutros.tetraits.Tetraits;
import eutros.tetraits.data.CapData;
import eutros.tetraits.util.TetraHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.INBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.commons.lang3.tuple.Triple;
import se.mickelus.tetra.blocks.workbench.WorkbenchTile;
import se.mickelus.tetra.items.modular.IItemModular;
import se.mickelus.tetra.module.data.ModuleVariantData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Stream;

public class CapabilityHandler {

    @CapabilityInject(IDataCapability.class)
    private static Capability<IDataCapability> DATA_CAPABILITY;

    public static void init() {
        MinecraftForge.EVENT_BUS.addGenericListener(ItemStack.class, CapabilityHandler::onAttachCaps);
        MinecraftForge.EVENT_BUS.addGenericListener(TileEntity.class, CapabilityHandler::onTile);
        MinecraftForge.EVENT_BUS.addListener(CapabilityHandler::onTick);
        FMLJavaModLoadingContext.get().getModEventBus().addListener((FMLCommonSetupEvent fcse) ->
                CapabilityManager.INSTANCE.register(IDataCapability.class, new NoopStorage<>(), () -> (IDataCapability) () -> {
                }));
    }

    private static void onAttachCaps(AttachCapabilitiesEvent<ItemStack> evt) {
        ItemStack stack = evt.getObject();
        if(stack.getItem() instanceof IItemModular) {
            evt.addCapability(new ResourceLocation(Tetraits.MOD_ID, "data_cap"), new DataCapability(stack));
        }
    }

    private static Queue<Runnable> delegatedTasks = new LinkedList<>();

    private static void onTile(AttachCapabilitiesEvent<TileEntity> evt) {
        TileEntity tile = evt.getObject();
        if(tile instanceof WorkbenchTile) {
            delegatedTasks.add(() ->
                    ((WorkbenchTile) tile).addChangeListener(Tetraits.MOD_ID + ":listener",
                            () -> ((WorkbenchTile) tile).getTargetItemStack()
                                    .getCapability(DATA_CAPABILITY)
                                    .ifPresent(IDataCapability::checkCache))
            );
        }
    }

    private static void onTick(TickEvent.ServerTickEvent evt) {
        if(evt.phase == TickEvent.Phase.END) {
            Runnable polled = delegatedTasks.poll();
            while(polled != null) {
                polled.run();
                polled = delegatedTasks.poll();
            }
        }
    }

    interface IDataCapability {

        void checkCache();

    }

    private static class DataCapability implements ICapabilityProvider, IDataCapability {

        private final ItemStack stack;
        private int mvdHash = 0;
        private boolean firstCached;

        public DataCapability(ItemStack stack) {
            this.stack = stack;
        }

        private final Map<ResourceLocation, Object> userCache = new HashMap<>();
        private final List<Triple<ResourceLocation, IFn, Object>> fnCache = new LinkedList<>();
        private final Map<Capability<?>, LazyOptional<?>> capCache = new IdentityHashMap<>();

        public void checkCache() {
            if(mvdHash != hashMvd()) recalculateCache();
        }

        private int hashMvd() {
            Stream<ModuleVariantData> stream = TetraHelper.getRelevantVariantData(stack);
            return stream == null ? 0 : stream.mapToInt(Object::hashCode).reduce(0, (a, b) -> a * 31 + b);
        }

        private void recalculateCache() {
            fnCache.clear();
            capCache.values().forEach(LazyOptional::invalidate);
            capCache.clear();
            userCache.clear();
            mvdHash = hashMvd();
            TetraHelper.forAllFrom(stack,
                    CapData.getInstance(),
                    (rl, fn, extra) -> {
                        fnCache.add(Triple.of(rl, fn, extra));
                        return null;
                    });
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            if(cap == DATA_CAPABILITY) return LazyOptional.of(() -> this).cast();

            if(!firstCached) {
                firstCached = true;
                recalculateCache();
            }

            if(capCache.containsKey(cap)) return capCache.get(cap).cast();

            for(Triple<ResourceLocation, IFn, Object> triple : fnCache) {
                @SuppressWarnings("unchecked")
                // this may cause heap pollution but oh well
                T val = (T) triple.getMiddle().invoke(stack,
                        cap,
                        side,
                        new AFn() {
                            @Override
                            public Object invoke() {
                                return userCache.get(triple.getLeft());
                            }

                            @Override
                            public Object invoke(Object arg1) {
                                userCache.put(triple.getLeft(), arg1);
                                return arg1;
                            }
                        },
                        triple.getRight());
                if(val != null) {
                    LazyOptional<T> lo = LazyOptional.of(() -> val);
                    capCache.put(cap, lo);
                    lo.addListener(l -> {
                        if(!l.isPresent()) capCache.remove(cap);
                    });
                    return lo;
                }
            }
            return LazyOptional.empty();
        }

    }

    private static class NoopStorage<T> implements Capability.IStorage<T> {

        @Nullable
        @Override
        public INBT writeNBT(Capability<T> capability, T instance, Direction side) {
            return null;
        }

        @Override
        public void readNBT(Capability<T> capability, T instance, Direction side, INBT nbt) {
        }

    }

}
