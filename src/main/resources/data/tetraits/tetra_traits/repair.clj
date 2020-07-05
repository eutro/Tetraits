(fn [evt]
  (let [stack (-> (.getEntityLiving evt)
                  (TetraitsAPI.EntityHelper/getHeldItemMainhand))]
    (->> (TetraitsAPI.ItemStackHelper/getDamage stack)
         (dec)
         (java.lang.Math/max 0)
         (TetraitsAPI.ItemStackHelper/setDamage stack))))