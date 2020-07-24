(ns traits.psi.tool)

(let [pstool
      (reify
       vazkii.psi.common.item.tool.IPsimetalTool)]

  (def ^:dynamic casting false)

  (fn [evt _]
    (case evt
      "BREAK_BLOCK" (fn [stack world player pos xp setXp]
                      (when-not casting
                        (binding [casting true]
                          (.castOnBlockBreak pstool
                                             stack
                                             player))))
      nil)))
