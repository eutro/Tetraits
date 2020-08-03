(ns traits.psi.equipment
  (:use tetraits.core))

(when-loaded
 "psi"
 (import
  vazkii.psi.common.item.tool.IPsimetalTool
  net.minecraft.util.text.TranslationTextComponent
  vazkii.psi.api.cad.ISocketable
  java.util.List)
 (fn [evt [doRegen addTooltip]]
   (case evt
     "INVENTORY_TICK" (fn [stack world player slot isSelected]
                        (if doRegen
                          (IPsimetalTool/regen stack
                                               player)))
     "TOOLTIP"        (fn [stack world player flags ^List tooltip]
                        (if addTooltip
                          (.add tooltip
                                1
                                (TranslationTextComponent.
                                  "psimisc.spell_selected"
                                  (into-array [(ISocketable/getSocketedItemName stack "psimisc.none")])))))
     nil)))
