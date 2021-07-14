(ns aoc2019.core
  (:gen-class)
  (:require [aoc2019.day-1 :as d1]))

(defn -main
  "Dispatch to the day to run"
  ([day & args]
   (case (read-string day)
     1 (d1/-main args)
     (println "ERROR: Can't do that day")))
  ([] (println "ERROR: Please specify a day")))
