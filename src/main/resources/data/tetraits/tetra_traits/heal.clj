(fn [evt]
  (let [entity (.getEntityLiving evt)]
    (if (and (not (nil? entity))
             (= 0
                (-> (.getEntityWorld entity)
                    (.getGameTime)
                    (mod 5))))
      (->> (.getHealth entity)
           (+ 1)
           (.setHealth entity))
      nil)))
