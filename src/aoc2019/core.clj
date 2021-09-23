(ns aoc2019.core
  (:gen-class)
  (:require [aoc2019.day-1  :as d1]
            [aoc2019.day-2  :as d2]
            [aoc2019.day-3  :as d3]
            [aoc2019.day-4  :as d4]
            [aoc2019.day-5  :as d5]
            [aoc2019.day-6  :as d6]
            [aoc2019.day-7  :as d7]
            [aoc2019.day-8  :as d8]
            [aoc2019.day-9  :as d9]
            [aoc2019.day-10 :as d10]
            [aoc2019.day-11 :as d11]
            [aoc2019.day-12 :as d12]
            [aoc2019.day-13 :as d13]
            [aoc2019.day-14 :as d14]
            [aoc2019.day-15 :as d15]))

(def main-funcs
  {1  d1/-main
   2  d2/-main
   3  d3/-main
   4  d4/-main
   5  d5/-main
   6  d6/-main
   7  d7/-main
   8  d8/-main
   9  d9/-main
   10 d10/-main
   11 d11/-main
   12 d12/-main
   13 d13/-main
   14 d14/-main
   15 d15/-main})

(defn -main
  "Dispatch to the day to run"
  ([day & args]
   (if-let [main (get main-funcs (Integer/valueOf day))]
     (main args)
     (println "ERROR: Can't do that day")))
  ([] (println "ERROR: Please specify a day")))
