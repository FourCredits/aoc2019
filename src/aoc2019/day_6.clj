(ns aoc2019.day-6
  (:gen-class)
  (:require [clojure.string :as str]))

(defn find-sub-orbits
  "Returns a list of objects that orbit `object`, taken from the `orbits`
  collection."
  [object orbits]
  (map second (filter #(= object (first %)) orbits)))

(defn make-table [orbits]
  (let [unique-orbits (set (apply concat orbits))
        sub-orbits    (map #(find-sub-orbits % orbits) unique-orbits)
        lookup-table  (into {} (map vector unique-orbits sub-orbits))]
    lookup-table))

(defn part-1 [orbits]
    ; Depth-first traversal
    (loop [result 0
           queue  '([0 "COM"])]
      (if (empty? queue)
        result
        (let [[depth k] (peek queue)]
          (recur (+ result depth)
                 (apply list (concat (pop queue) (map #(vector (inc depth) %) (orbits k)))))))))

(defn get-input [] nil)
(defn parse [string]
  (make-table (map #(str/split % #"\)") (str/split-lines string))))

(defn get-input []
  (parse (slurp "resources/day6.txt")))

(defn -main [& args]
  (let [input (get-input)]
    (println (part-1 input))
    (println (part-2 input))))
