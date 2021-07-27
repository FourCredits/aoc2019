(ns aoc2019.day-10
  (:gen-class)
  (:require [clojure.string :as str]
            [aoc2019.utils :refer :all]))

(defn polar
  "Takes a `real` part and an `imag`inary part, and returns the magnitude and
  phase of the number. Takes phase at starting at 12 o'clock and going
  clockwise (due to part 2)"
  [real imag]
  [(Math/sqrt (+ (* real real) (* imag imag))) (Math/atan2 imag real)])

(defn complex-sub
  "Subtracts two complex numbers"
  [[a1 b1] [a2 b2]]
  [(- a1 a2) (- b1 b2)])

(defn num-line-of-sight
  "Counts the number of `asteriods` that are in direct line of sight of `(x y)`"
  [[x y] asteriods]
  (count (set (map #(second (apply polar (complex-sub % [x y]))) asteriods))))

(defn find-best-station
  [potentials]
  (let [lookup (into
                {}
                (map #(vector (num-line-of-sight % (remove #{%} potentials)) %)
                     potentials))]
    (get lookup (apply max (keys lookup)) :error)))

(defn part-1 [asteriods]
  (let [station (find-best-station asteriods)]
    (num-line-of-sight station (remove #{station} asteriods))))

(defn canonical-form
  [[mag arg]]
  [mag (if (neg? arg)
         (+ (* 2 Math/PI) arg)
         arg)])

(defn part-2 [asteriods]
  nil)

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
