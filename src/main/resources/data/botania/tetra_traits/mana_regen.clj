(defn regen [MANA_PER_DAMAGE]
  (fn [stack world player slot isCurrent]
    (if (and (not (TetraitsAPI.WorldHelper/isRemote world))
             (> (TetraitsAPI.ItemStackHelper/getDamage stack)
                0)
             (-> (vazkii.botania.api.mana.ManaItemHandler/instance)
                 (.requestManaExactForTool stack
                                           player
                                           (* 2 MANA_PER_DAMAGE)
                                           true)))
      (->> (TetraitsAPI.ItemStackHelper/getDamage stack)
           (dec)
           (TetraitsAPI.ItemStackHelper/setDamage stack)))))

(fn [evt]
  (case evt
    "INVENTORY_TICK" (regen 60)
    nil))