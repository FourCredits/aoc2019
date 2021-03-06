(ns aoc2019.day-2
  (:gen-class)
  (:require [aoc2019.intcode :as i]))

(defn part-1
  "Solves part 1 of day 2"
  [program]
  (let [program' (assoc program 1 12 2 2)]
    (get-in (i/run-pure program') [:memory 0])))

(defn part-2
  "Solves part 2 of day 2"
  [program]
  (first
   (for [noun  (range 100)
         verb  (range 100)
         :let  [program' (assoc program 1 noun 2 verb)]
         :when (= (get-in (i/run-pure program') [:memory 0]) 19690720)]
     (+ (* 100 noun) verb))))

(defn -main
  "Runs day 2"
  [& _]
  (let [program (i/read-intcode-program "resources/day2.txt")]
    (println "Part 1:" (part-1 program))
    (println "Part 2:" (part-2 program))))
