(fn [evt manaPerDamage]
  (let [regen (fn [stack world player slot isCurrent]
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
                       (TetraitsAPI.ItemStackHelper/setDamage stack))))]

    (case evt
      "INVENTORY_TICK" (regen manaPerDamage)
      nil)))