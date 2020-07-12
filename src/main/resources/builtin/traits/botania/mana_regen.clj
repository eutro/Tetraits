(fn [evt manaPerDamage]
  (case evt
    "INVENTORY_TICK" (fn [stack world player slot isCurrent]
                       (if (and (not (TetraitsAPI.WorldHelper/isRemote world))
                                (> (TetraitsAPI.ItemStackHelper/getDamage stack)
                                   0)
                                (-> (vazkii.botania.api.mana.ManaItemHandler/instance)
                                    (.requestManaExactForTool stack
                                                              player
                                                              (* 2 manaPerDamage)
                                                              true)))
                         (->> (TetraitsAPI.ItemStackHelper/getDamage stack)
                              (dec)
                              (TetraitsAPI.ItemStackHelper/setDamage stack))))
    nil))
