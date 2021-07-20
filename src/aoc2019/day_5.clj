(ns aoc2019.day-5
  (:gen-class)
  (:require [aoc2019.intcode :refer :all]
            [clojure.string :as str]))

(defn part-1 [program] (last (:output (run-pure program [1]))))

(defn part-2 [program] (first (:output (run-pure program [5]))))

(def filepath "resources/day5.txt")

(defn -main
  [& args]
  (let [input-program (get-input filepath)]
    (println (part-1 input-program))
    (println (part-2 input-program))))

