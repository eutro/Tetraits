(let [getBurst
      (fn [player]
        (let [burst          (vazkii.botania.common.entity.EntityManaBurst. player)
              mult           7]
          (.setColor burst 0x20FF20)
          (.setMana burst 100)
          (.setStartingMana burst 100)
          (.setMinManaLoss burst 40)
          (.setManaLossPerTick burst 4)
          (.setGravity burst 0)
          (as-> (TetraitsAPI.EntityHelper/getMotion burst) $
                (TetraitsAPI.VectorHelper/mul $ mult mult mult)
                (TetraitsAPI.EntityHelper/setMotion burst $))
          (.setSourceLens burst
                          (net.minecraft.item.ItemStack. vazkii.botania.common.item.ModItems/terraSword))
          burst))

      trySpawnBurst
      (fn [stack player world]
        (when
          (and
           (not (TetraitsAPI.WorldHelper/isRemote world))
           (-> (TetraitsAPI.EntityHelper/getCooledAttackStrength player 0)
               (= 1.0)))
          (->> (getBurst player)
               (TetraitsAPI.WorldHelper/addEntity world))
          (vazkii.botania.common.item.equipment.tool.ToolCommons/damageItem stack
                                                                            1
                                                                            player
                                                                            100)))

      handlePacket
      (fn [ctx]
        (if (-> (.getDirection ctx)
                (.getReceptionSide)
                (.isServer))
          (.enqueueWork ctx
                        (fn []
                          (let [player (.getSender ctx)]
                            (if (and (not (nil? player))
                                     (-> (TetraitsAPI.EntityHelper/getEntityWorld player)
                                         (nil?)
                                         (not))
                                     (-> (TetraitsAPI.EntityHelper/getHeldItemMainhand player)
                                         (TetraitsAPI.ItemStackHelper/isEmpty)
                                         (not)))
                              (trySpawnBurst (TetraitsAPI.EntityHelper/getHeldItemMainhand player)
                                             player
                                             (TetraitsAPI.EntityHelper/getEntityWorld player))))))))

      schemeName
      "botania:blade_beam/left_click"]

  (TetraitsAPI.PacketHelper/addScheme schemeName
                                      (reify
                                       TetraitsAPI.IPacketScheme
                                       (encode [this data buf])
                                       (decode [this buf] nil)
                                       (handle [this data ctx] (handlePacket ctx))))

  (fn [evt _]
    (case evt
      "ENTITY_ATTACK"    (fn [stack world player target]
                           (trySpawnBurst stack player world))
      "LEFT_CLICK_EMPTY" (fn [stack world player hand pos face]
                           (TetraitsAPI.PacketHelper/sendToServer schemeName nil))
      nil)))
