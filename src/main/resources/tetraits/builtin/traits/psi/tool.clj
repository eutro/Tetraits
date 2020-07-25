(ns traits.psi.tool
  (:use tetraits.core))

(when-loaded
 "psi"
 (import vazkii.psi.common.item.tool.IPsimetalTool)
 (let [pstool
       (reify
        IPsimetalTool)]

   (def ^:dynamic casting false)

   (fn [evt _]
     (case evt
       "BREAK_BLOCK" (fn [stack world player pos xp setXp]
                       (when-not casting
                         (binding [casting true]
                           (.castOnBlockBreak pstool
                                              stack
                                              player))))
       nil))))
