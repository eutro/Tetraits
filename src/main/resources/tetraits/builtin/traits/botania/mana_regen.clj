(ns traits.botania.mana_regen
  (:use tetraits.core))

(when-loaded
 "botania"
 (import
  vazkii.botania.api.mana.ManaItemHandler
  (eutros.tetraits.clojure_api WorldHelper
                               ItemStackHelper))
 (fn [evt manaPerDamage]
   (case evt
     "INVENTORY_TICK" (fn [stack world player slot isCurrent]
                        (if (and (not (WorldHelper/isRemote world))
                                 (> (ItemStackHelper/getDamage stack)
                                    0)
                                 (-> (ManaItemHandler/instance)
                                     (.requestManaExactForTool stack
                                                               player
                                                               (* 2 manaPerDamage)
                                                               true)))
                          (->> (ItemStackHelper/getDamage stack)
                               (dec)
                               (ItemStackHelper/setDamage stack))))
     nil)))
