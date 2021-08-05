(ns aoc2019.day-5
  (:gen-class)
  (:require [aoc2019.intcode :as i]))

(defn part-1 [program] (last (:output (i/run-pure program [1]))))

(defn part-2 [program] (first (:output (i/run-pure program [5]))))

(def filepath "resources/day5.txt")

(defn -main
  [& _]
  (let [input-program (i/read-intcode-program filepath)]
    (println (part-1 input-program))
    (println (part-2 input-program))))

