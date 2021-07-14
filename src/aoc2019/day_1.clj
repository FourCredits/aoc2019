(ns aoc2019.day-1
  (:gen-class)
  (:require [clojure.string :as str]))

(defn part-1 [x] (- (int (/ x 3)) 2))

(defn part-2 [x]
  (->> x
       (iterate part-1)
       (next)
       (take-while pos?)
       (apply +)))

(defn solve [f nums]
  (apply + (map f nums)))

(defn get-input []
  (map read-string (str/split (slurp "resources/day1.txt") #"\n")))

(defn -main [& args]
  (let [input (get-input)]
    (println "Part 1:" (solve part-1 input))))
