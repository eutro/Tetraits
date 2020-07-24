(fn [stack cap side cache slots]
  (if-let (nil? (cache))
    [gOCCompound (fn [] (TetraitsAPI.ItemStackHelper/getOrCreateChildTag stack "tetraits:psi_tool"))]
    (cache
      (reify
       vazkii.psi.api.cad.ISocketable
       (isSocketSlotAvailable [this slot]
                              (< slot slots))
       (getBulletInSocket [this slot]
                          (let [cmp
                                (->
                                 (gOCCompound)
                                 (TetraitsAPI.NBTHelper/getCompound (str "bullet" slot)))]
                            (if (TetraitsAPI.NBTHelper/isEmpty cmp)
                              TetraitsAPI.ItemStackHelper/EMPTY
                              (TetraitsAPI.ItemStackHelper/read cmp))))
       (setBulletInSocket [this slot bullet]
                          (let [cmp (net.minecraft.nbt.CompoundNBT.)]
                            (if-not (TetraitsAPI.ItemStackHelper/isEmpty bullet)
                              (TetraitsAPI.ItemStackHelper/write bullet cmp))
                            (->
                             (gOCCompound)
                             (TetraitsAPI.NBTHelper/put (str "bullet" slot) cmp))))
       (getSelectedSlot [this]
                        (-> (gOCCompound)
                            (TetraitsAPI.NBTHelper/getInt "selectedSlot")))
       (setSelectedSlot [this slot]
                        (-> (gOCCompound)
                            (TetraitsAPI.NBTHelper/putInt "selectedSlot" slot)))

       vazkii.psi.api.cad.IPsiBarDisplay
       (shouldShow [this data] false)

       vazkii.psi.api.spell.ISpellAcceptor
       (setSpell [this player spell]
                 (let [slot   (.getSelectedSlot this)
                       bullet (.getBulletInSocket this slot)]
                   (when
                     (and (not (TetraitsAPI.ItemStackHelper/isEmpty bullet))
                          (vazkii.psi.api.spell.ISpellAcceptor/isAcceptor bullet))
                     (-> (vazkii.psi.api.spell.ISpellAcceptor/acceptor bullet)
                         (.setSpell player spell))
                     (.setBulletInSocket this slot bullet)))))))

  (if (or (= cap vazkii.psi.api.PsiAPI/SOCKETABLE_CAPABILITY)
          (= cap vazkii.psi.api.PsiAPI/PSI_BAR_DISPLAY_CAPABILITY)
          (= cap vazkii.psi.api.PsiAPI/SPELL_ACCEPTOR_CAPABILITY))
    (cache)))
