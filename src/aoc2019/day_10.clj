(ns aoc2019.day-10
  (:gen-class)
  (:require [clojure.string :as str]
            [aoc2019.utils :refer :all]))

(defn num-line-of-sight
  "Counts the number of `asteriods` that are in direct line of sight of `pos`"
  [pos asteriods]
  (count (set (map #(apply arg (complex-sub % pos)) asteriods))))

(defn find-best-station
  "Among `potentials`, finds the best place to put a station - the best place
  being where the most asteriods are within direct line of sight."
  [potentials]
  (apply max-key #(num-line-of-sight % (remove #{%} potentials)) potentials))

(defn part-1 [asteriods]
  (let [station (find-best-station asteriods)]
    (num-line-of-sight station (remove #{station} asteriods))))

(defn round-robin-ordering
  "Takes a collection of collections, and returns a flattened version, in a
  special order: it takes the first element of each collection, then the
  second of each, and so on."
  [colls]
  (loop [acc   []
         rests colls]
    (if (every? empty? rests)
      acc
      (recur (concat acc (remove nil? (map first rests)))
             (map rest rests)))))

(defn angle-between
  "Returns the angle ABC, where A is the station + i, B is the station,
  and C is the asteriod. Note that the angle is from 0 to 2 * pi."
  [station asteriod]
  (let [[x y] (complex-sub asteriod station)
        angle (Math/atan2 x (- y))]
    (if (neg? angle)
      (+ angle (* 2 Math/PI))
      angle)))

(defn group-by-angle
  "Groups the members of `others` by those that have the same angle to the
  station. Returns in sorted order, going from directly above and then
  clockwise after that."
  [station others]
  (->> others
       (group-by #(angle-between station %))
       (into (sorted-map))
       (vals)))

(defn sort-by-magnitude
  "Sorts a collection of asteriods by the magnitude of the distance from that
  asteriod to the station"
  [station others]
  (sort-by #(magnitude (complex-sub station %)) others))

(defn destroyed-order
  "Orders the asteriods in `others` in the order they would be destroyed by the
  laser."
  [station others]
  (->> others
       (group-by-angle station)
       (map #(sort-by-magnitude station %))
       (round-robin-ordering)))

(defn part-2 [asteriods]
  (let [station   (find-best-station asteriods)
        others    (remove #{station} asteriods)
        destroyed (destroyed-order station others)
        [x y]     (nth destroyed (dec 200))] ; Prevent OBOEs
    (+ (* x 100) y)))

(defn parse [input]
  (let [field (str/split-lines input)]
    (for [y (range (count field))
          x (range (count (first field)))
          :when (= (get-in field [y x]) \#)]
      [x y])))

(def filepath "resources/day10.txt")

(defn get-input []
  (parse (slurp filepath)))

(defn -main [& args]
  (let [input (get-input)]
    (println (part-1 input))
    (println (part-2 input))))
