(ns tetraits.core
  (:import net.minecraftforge.fml.common.ObfuscationReflectionHelper
           cpw.mods.modlauncher.api.INameMappingService$Domain
           net.minecraftforge.fml.ModList))

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

(defn is-loaded
  "Returns true if there is a mod loaded with the given modid, false otherwise."
  [modid]
  (-> (ModList/get)
      (.isLoaded modid)))

(defmacro if-loaded
  "Checks if the mod is loaded. If it is,
  evaluates and returns then expr,
  otherwise else expr, if supplied, else nil.

  The check is done when the macro is expanded,
  to avoid compile errors if the mod is not present."
  ([modid then] `(if-loaded ~modid ~then nil))
  ([modid then else]
   (if (is-loaded modid)
     then
     else)))

(defmacro when-loaded
  "Like if-loaded, but with an implicit do and no else clause."
  [modid & body]
  (list 'if-loaded modid (cons 'do body)))
