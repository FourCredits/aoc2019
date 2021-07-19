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

(defn find-parent [object orbits]
  (first (first (filter #(some #{object} (second %)) orbits))))

(defn find-parents [object orbits]
  (let [parent (find-parent object orbits)]
    (if (nil? parent)
      '()
      (cons parent (find-parents parent orbits)))))

(defn num-orbital-transfers [orbits source target]
  (let [source-parents (find-parents source orbits)
        target-parents (find-parents target orbits)
        common-parent  (first
                         (filter #(some #{%} target-parents) source-parents))]
    (+ (.indexOf source-parents common-parent)
       (.indexOf target-parents common-parent))))

(defn part-2 [orbits]
  (num-orbital-transfers orbits "YOU" "SAN"))

(defn parse [string]
  (make-table (map #(str/split % #"\)") (str/split-lines string))))

(defn get-input []
  (parse (slurp "resources/day6.txt")))

(defn -main [& args]
  (let [input (get-input)]
    (println (part-1 input))
    (println (part-2 input))))