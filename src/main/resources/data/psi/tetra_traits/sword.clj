(def pstool
  (reify
   vazkii.psi.common.item.tool.IPsimetalTool))

(fn [evt]
  (case evt
    "ATTACK" (fn [stack world attacker target source amount]
               (if (and (.isEnabled pstool stack)
                        (instance? net.minecraft.entity.player.PlayerEntity attacker))
                 (let [cad (vazkii.psi.api.PsiAPI/getPlayerCAD attacker)]
                   (if (not (TetraitsAPI.ItemStackHelper/isEmpty cad))
                     (vazkii.psi.common.item.ItemCAD/cast world
                                                          attacker
                                                          (vazkii.psi.common.core.handler.PlayerDataHandler/get attacker)
                                                          (-> (vazkii.psi.api.cad.ISocketable/socketable stack)
                                                              (.getSelectedBullet))
                                                          cad
                                                          5
                                                          10
                                                          0.05
                                                          (reify
                                                           java.util.function.Consumer
                                                           (accept [this context]
                                                                   (set! (. context -attackedEntity) target)
                                                                   (set! (. context -tool) stack))))))))
    nil))
