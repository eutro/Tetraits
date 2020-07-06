(fn [evt]
  (case evt
    "INVENTORY_TICK" (fn [player stack]
                       (if (and
                            (-> (TetraitsAPI.EntityHelper/getEntityWorld player)
                                (TetraitsAPI.WorldHelper/isRemote)
                                (not))
                            (-> (rand)
                                (< 0.01)))
                         (->> (TetraitsAPI.ItemStackHelper/getDamage stack)
                              (dec)
                              (max 0)
                              (TetraitsAPI.ItemStackHelper/setDamage stack)))
                       nil)
    nil))
