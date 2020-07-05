(fn [evt] (let [stack (-> (.getEntityLiving evt)
                          (.getHeldItemMainhand))]
            (->> (.getDamage stack)
                 (- 1)
                 (.setDamage stack))))