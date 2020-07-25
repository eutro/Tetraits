(ns traits.psi.sword
  (:use tetraits.core))

(when-loaded
 "psi"
 (import
  vazkii.psi.api.PsiAPI
  vazkii.psi.api.cad.ISocketable
  vazkii.psi.common.item.tool.IPsimetalTool
  vazkii.psi.common.item.ItemCAD
  vazkii.psi.common.core.handler.PlayerDataHandler
  net.minecraft.entity.player.PlayerEntity
  java.util.function.Consumer
  vazkii.psi.api.spell.SpellContext
  eutros.tetraits.clojure_api.ItemStackHelper)
 (let [pstool
       (reify
        IPsimetalTool)]

   (fn [evt _]
     (case evt
       "ATTACK" (fn [stack world attacker target source amount]
                  (if (and (.isEnabled pstool stack)
                           (instance? PlayerEntity attacker))
                    (let [cad (PsiAPI/getPlayerCAD attacker)]
                      (if-not (ItemStackHelper/isEmpty cad)
                        (ItemCAD/cast world
                                      attacker
                                      (PlayerDataHandler/get attacker)
                                      (-> (ISocketable/socketable stack)
                                          (.getSelectedBullet))
                                      cad
                                      5
                                      10
                                      0.05
                                      (reify
                                       Consumer
                                       (accept [this context]
                                               (set! (.attackedEntity ^SpellContext context) target)
                                               (set! (.tool ^SpellContext context) stack))))))))
       nil))))
