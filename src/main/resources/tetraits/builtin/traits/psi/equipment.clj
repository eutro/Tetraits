(ns traits.psi.equipment
  (:use tetraits.core))

(when-loaded "psi"
  (import net.minecraft.util.text.TranslationTextComponent
          vazkii.psi.api.cad.ISocketable
          java.util.List)
  (fn [evt _]
    (case evt
      "TOOLTIP" (fn [stack world player flags ^List tooltip]
                  (.add tooltip
                        1
                        (TranslationTextComponent.
                          "psimisc.spell_selected"
                          (into-array [(ISocketable/getSocketedItemName stack "psimisc.none")]))))
      nil)))
