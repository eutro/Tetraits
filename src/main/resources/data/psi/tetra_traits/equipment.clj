(fn [evt]
  (case evt
    "INVENTORY_TICK" (fn [stack world player slot isSelected]
                       (vazkii.psi.common.item.tool.IPsimetalTool/regen stack
                                                                        player
                                                                        isSelected))
    "TOOLTIP"        (fn [stack world player flags tooltip]
                       (.add tooltip
                             (net.minecraft.util.text.TranslationTextComponent. "psimisc.spell_selected"
                                                                                (into-array
                                                                                 [(vazkii.psi.api.cad.ISocketable/getSocketedItemName stack "psimisc.none")]))))
    nil))