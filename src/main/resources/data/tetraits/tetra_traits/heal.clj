(fn [evt]
  (case evt
    "INVENTORY_TICK" (fn [player stack]
                       (->> (TetraitsAPI.EntityHelper/getHealth player)
                            (inc)
                            (TetraitsAPI.EntityHelper/setHealth player)))
    nil))
