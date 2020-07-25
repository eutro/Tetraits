(ns traits.botania.blade_beam
  (:use tetraits.core))

(when-loaded
 "botania"
 (import
  vazkii.botania.common.entity.EntityManaBurst
  vazkii.botania.common.item.ModItems
  vazkii.botania.common.item.equipment.tool.ToolCommons
  net.minecraft.item.ItemStack
  net.minecraftforge.fml.network.NetworkEvent$Context
  (eutros.tetraits.clojure_api EntityHelper
                               VectorHelper
                               WorldHelper
                               ItemStackHelper
                               IPacketScheme
                               PacketHelper))
 (let [getBurst
       (fn [player]
         (let [burst          (EntityManaBurst. player)
               mult           7]
           (.setColor burst 0x20FF20)
           (.setMana burst 100)
           (.setStartingMana burst 100)
           (.setMinManaLoss burst 40)
           (.setManaLossPerTick burst 4)
           (.setGravity burst 0)
           (as-> (EntityHelper/getMotion burst) $
                 (VectorHelper/mul $ mult mult mult)
                 (EntityHelper/setMotion burst $))
           (.setSourceLens burst
                           (ItemStack. ModItems/terraSword))
           burst))

       trySpawnBurst
       (fn [stack player world]
         (when
           (and
            (not (WorldHelper/isRemote world))
            (-> (EntityHelper/getCooledAttackStrength player 0)
                (= 1.0)))
           (->> (getBurst player)
                (WorldHelper/addEntity world))
           (ToolCommons/damageItem stack
                                   1
                                   player
                                   100)))

       handlePacket
       (fn [^NetworkEvent$Context ctx]
         (if (-> (.getDirection ctx)
                 (.getReceptionSide)
                 (.isServer))
           (.enqueueWork ctx
                         (fn []
                           (let [player (.getSender ctx)]
                             (if (and (not (nil? player))
                                      (-> (EntityHelper/getEntityWorld player)
                                          (nil?)
                                          (not))
                                      (-> (EntityHelper/getHeldItemMainhand player)
                                          (ItemStackHelper/isEmpty)
                                          (not)))
                               (trySpawnBurst (EntityHelper/getHeldItemMainhand player)
                                              player
                                              (EntityHelper/getEntityWorld player))))))))

       schemeName
       "botania:blade_beam/left_click"]

   (PacketHelper/addScheme schemeName
                           (reify
                            IPacketScheme
                            (encode [this data buf])
                            (decode [this buf] nil)
                            (handle [this data ctx] (handlePacket ctx))))

   (fn [evt _]
     (case evt
       "ENTITY_ATTACK"    (fn [stack world player target]
                            (trySpawnBurst stack player world))
       "LEFT_CLICK_EMPTY" (fn [stack world player hand pos face]
                            (PacketHelper/sendToServer schemeName nil))
       nil))))
