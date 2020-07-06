(fn [evt]
  (case evt
    "INVENTORY_TICK" (fn [stack world player slot isCurrent]
                       (if (and
                            (not (TetraitsAPI.WorldHelper/isRemote world))
                            (< (rand) 0.01))
                         (->> (TetraitsAPI.ItemStackHelper/getDamage stack)
                              (dec)
                              (max 0)
                              (TetraitsAPI.ItemStackHelper/setDamage stack)))
                       nil)
    nil))
