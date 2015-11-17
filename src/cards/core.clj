(ns cards.core
  (:gen-class))

(def deck (atom #{}))

(def drawn-cards (atom #{}))

(defn create-standard-deck []
  (let [vals (map (partial hash-map :val) (range 1 14))]
    (doseq [suit ["hearts" "spades" "clubs" "diamonds"]]
      (swap! deck clojure.set/union 
             (set (map (partial conj {:suit suit}) vals))))))

(defn random-card []
  (last (take
         (+ 1 (rand-int (count @deck)))
         @deck)))

(defn draw-card []
  (if (= (count @drawn-cards) (count @deck))
    "deck empty"
    (let [card (random-card)]
      (if (contains? @drawn-cards card)
        (draw-card)
        (swap! drawn-cards conj card)))))

(create-standard-deck)
