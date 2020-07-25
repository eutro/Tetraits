(ns capabilities.psi.equipment
  (:use tetraits.core))

(when-loaded
 "psi"
 (import
   vazkii.psi.api.PsiAPI
   vazkii.psi.api.spell.ISpellAcceptor
   (vazkii.psi.api.cad ISocketable
                       IPsiBarDisplay)
   net.minecraft.nbt.CompoundNBT
   (eutros.tetraits.clojure_api ItemStackHelper
                                NBTHelper))
 (fn [stack cap side cache slots]
   (if (nil? (cache))
     (let [gOCCompound (fn [] (ItemStackHelper/getOrCreateChildTag stack "tetraits:psi_tool"))]
       (cache
        (reify
         ISocketable
         (isSocketSlotAvailable [this slot]
                                (< slot slots))
         (getBulletInSocket [this slot]
                            (let [cmp
                                  (->
                                   (gOCCompound)
                                   (NBTHelper/getCompound (str "bullet" slot)))]
                              (if (NBTHelper/isEmpty cmp)
                                ItemStackHelper/EMPTY
                                (ItemStackHelper/read cmp))))
         (setBulletInSocket [this slot bullet]
                            (let [cmp (CompoundNBT.)]
                              (if-not (ItemStackHelper/isEmpty bullet)
                                (ItemStackHelper/write bullet cmp))
                              (->
                               (gOCCompound)
                               (NBTHelper/put (str "bullet" slot) cmp))))
         (getSelectedSlot [this]
                          (-> (gOCCompound)
                              (NBTHelper/getInt "selectedSlot")))
         (setSelectedSlot [this slot]
                          (-> (gOCCompound)
                              (NBTHelper/putInt "selectedSlot" slot)))

         IPsiBarDisplay
         (shouldShow [this data] false)

         ISpellAcceptor
         (setSpell [this player spell]
                   (let [slot   (.getSelectedSlot this)
                         bullet (.getBulletInSocket this slot)]
                     (when
                       (and (not (ItemStackHelper/isEmpty bullet))
                            (ISpellAcceptor/isAcceptor bullet))
                       (-> (ISpellAcceptor/acceptor bullet)
                           (.setSpell player spell))
                       (.setBulletInSocket this slot bullet))))))))

   (if (or (= cap PsiAPI/SOCKETABLE_CAPABILITY)
           (= cap PsiAPI/PSI_BAR_DISPLAY_CAPABILITY)
           (= cap PsiAPI/SPELL_ACCEPTOR_CAPABILITY))
     (cache))))
