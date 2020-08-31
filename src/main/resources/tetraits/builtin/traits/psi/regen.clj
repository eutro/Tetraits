(ns traits.psi.regen
  (:use tetraits.core))

(when-loaded "psi"
  (import vazkii.psi.common.item.tool.IPsimetalTool)
  (fn [evt _]
    (case evt
      "INVENTORY_TICK" (fn [stack world player slot isSelected]
                         (IPsimetalTool/regen stack
                                              player))
      nil)))
