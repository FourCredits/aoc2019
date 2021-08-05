(ns aoc2019.day-1
  (:gen-class)
  (:require [clojure.string :as str]))

(defn part-1 
  "Calculates the fuel needed for a given weight x."
  [x]
  (- (quot x 3) 2))

(defn part-2
  "Calculates the fuel needed for a given weight x, and the fuel needed for
  that fuel, and so on, until the amount becomes less than 0. Returns the sum."
  [x]
  (->> x
       (iterate part-1)
       (next)
       (take-while pos?)
       (apply +)))

(defn solve
  "For a given list of weights `nums`, finds the amount of fuel needed for
  that fuel using the weight function f. Returns the sum of those amounts
  of fuel."
  [f nums]
  (apply + (map f nums)))

(defn get-input
  "Reads the input from the input file."
  []
  (map read-string (str/split (slurp "resources/day1.txt") #"\n")))

(defn -main
  "Prints the result for part 1 and part 2 of day 1"
  [& _]
  (let [input (get-input)]
    (println "Part 1:" (solve part-1 input))
    (println "Part 2:" (solve part-2 input))))
