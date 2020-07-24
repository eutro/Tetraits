(ns traits.psi.tool
  (:import vazkii.psi.common.item.tool.IPsimetalTool)
  (:use tetraits.core))

(if-loaded
 "psi"
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
