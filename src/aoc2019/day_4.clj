(ns aoc2019.day-4
  (:gen-class))

(defn six-digit? [n]
  (= 6 (count (str n))))

(defn adjacent-digit? [n]
  (let [str-rep (str n)]
    (some #{true} (map = str-rep (rest str-rep)))))

(defn chomp [[e :as coll]]
  (let [pred #(= e %)]
    (if (empty? coll)
      '()
      (cons (take-while pred coll) (chomp (drop-while pred coll))))))

(defn adjacent-group-of-two? [n]
  (not= 0 (count (filter #(= 2 (count %)) (chomp (str n))))))

(defn never-decreasing? [n]
  (apply <= (map int (str n))))

(defn solve [lo hi criteria]
    (count (filter (fn [n] (every? #(% n) criteria)) (range lo (inc hi)))))

(def criteria1
  [adjacent-digit?
   never-decreasing?
   six-digit?])

(def criteria2
  [adjacent-group-of-two?
   never-decreasing?
   six-digit?])

(defn part-1 [lo hi] (solve lo hi criteria1))

(defn part-2 [lo hi] (solve lo hi criteria2))

(defn meets-criteria1? [n] (every? #(% n) criteria1))

(defn meets-criteria2? [n] (every? #(% n) criteria2))

(defn -main [& args]
  (let [lo 278384
        hi 824795]
  (println (part-1 lo hi))
  (println (part-2 lo hi))))
