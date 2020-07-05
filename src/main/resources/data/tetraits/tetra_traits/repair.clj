(fn [evt]
  (let [stack (-> (.getEntityLiving evt)
                  (tetraits_api.EntityHelper/getHeldItemMainhand))]
    (as-> (tetraits_api.ItemStackHelper/getDamage stack) $
          (- $ 1)
          (min 0 $)
          (tetraits_api.ItemStackHelper/setDamage stack $))))