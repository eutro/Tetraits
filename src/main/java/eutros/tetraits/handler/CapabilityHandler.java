package eutros.tetraits.handler;

import eutros.tetraits.Tetraits;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import se.mickelus.tetra.items.modular.IItemModular;

@Mod.EventBusSubscriber(modid = Tetraits.MOD_ID)
public class CapabilityHandler {

    @SubscribeEvent
    public static void onAttachCaps(AttachCapabilitiesEvent<ItemStack> evt) {
        ItemStack stack = evt.getObject();
        Item item = stack.getItem();
        if(!(item instanceof IItemModular)) {
            return;
        }

        IItemModular im = (IItemModular) item;
    }

}
