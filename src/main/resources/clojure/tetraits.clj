(ns tetraits.core
  (:import net.minecraftforge.fml.common.ObfuscationReflectionHelper
           cpw.mods.modlauncher.api.INameMappingService$Domain))

(defmacro fobf
  "Access a field (static or not) by its SRG name."
  [srg object]
  (list `.
        object
        (->> (str srg)
             (ObfuscationReflectionHelper/remapName INameMappingService$Domain/FIELD)
             (symbol))))

(defmacro mobf
  "Invoke a method (static or not) by its SRG name."
  [srg object & args]
  (concat
   (list `.
         object
         (->> (str srg)
              (ObfuscationReflectionHelper/remapName INameMappingService$Domain/METHOD)
              (symbol)))
   args))
