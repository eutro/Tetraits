(fn [evt]
  (case evt
    "INVENTORY_TICK" (fn [stack world player slot isCurrent]
                       (->> (TetraitsAPI.EntityHelper/getHealth player)
                            (inc)
                            (TetraitsAPI.EntityHelper/setHealth player)))
    nil))
