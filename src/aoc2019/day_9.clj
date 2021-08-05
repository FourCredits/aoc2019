(ns aoc2019.day-9
  (:gen-class)
  (:require [aoc2019.intcode :as i]))

(defn part-1 [program] (:output (i/run-pure program [1])))

(defn part-2 [program] (:output (i/run-pure program [2])))

(def filepath "resources/day9.txt")

(defn -main [& _]
  (let [program (i/read-intcode-program filepath)]
    (println (part-1 program))
    (println (part-2 program))))
