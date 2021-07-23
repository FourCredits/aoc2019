(ns aoc2019.day-4
  (:gen-class))

(defn six-digit? [n]
  (= 6 (count (str n))))

(defn adjacent-digit? [n]
  (let [str-rep (str n)]
    (some #{true} (map = str-rep (rest str-rep)))))

(defn group-adjacent
  "Takes a colllection and returns that collection grouped by adjacent equal
  elements."
  [coll]
  (loop [[e :as remainder] coll
         acc []]
    (let [pred #(= e %)]
      (if (empty? remainder)
        acc
        (recur (drop-while pred remainder)
               (conj acc (take-while pred remainder)))))))

(defn adjacent-group-of-two?
  "Takes a number and returns true if any adjacent digits are equal to each
  other, but are only in a group of two, e.g. 112 will work, but 111 will not."
  [n]
  (->> n
       (str)
       (group-adjacent)
       (filter #(= 2 (count %)))
       (count)
       (pos?)))

(defn non-decreasing? [n]
  (apply <= (map int (str n))))

(defn meets-criteria?
  ([criteria] (partial meets-criteria? criteria))
  ([criteria ^Long n] (every? #(% n) criteria)))

(defn solve [criteria lo hi]
    (count (filter (meets-criteria? criteria) (range lo (inc hi)))))

; six-digit? is always true for the numbers we're given, so there's no point
; running it

(def criteria1
  [adjacent-digit?
   non-decreasing?
   #_six-digit?])

(def criteria2
  [adjacent-group-of-two?
   non-decreasing?
   #_six-digit?])

(defn part-1 [lo hi] (solve criteria1 lo hi))

(defn part-2 [lo hi] (solve criteria2 lo hi))

(defn get-input
  "Day 4 doesn't need a file, seeing its input is two numbers, but it's nice to
  be consistent."
  []
  [278384 824795])

(defn -main [& args]
  (let [[lo hi] (get-input)]
    (println (part-1 lo hi))
    (println (part-2 lo hi))))
