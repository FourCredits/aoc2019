(ns aoc2019.core
  (:gen-class)
  (:require [aoc2019.day-1 :as d1]
            [aoc2019.day-2 :as d2]
            [aoc2019.day-3 :as d3]))

(defn -main
  "Dispatch to the day to run"
  ([day & args]
   (case (read-string day)
     1 (d1/-main args)
     2 (d2/-main args)
     3 (d3/-main args)
     (println "ERROR: Can't do that day")))
  ([] (println "ERROR: Please specify a day")))
