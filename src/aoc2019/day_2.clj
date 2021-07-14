(ns aoc2019.day-2
  (:gen-class)
  (:require [clojure.string :as str]))

(defn binary-op [program pc f]
  "An abstraction of a binary operation on a program. Reads a number from
  address (pc + 1), another from (pc + 2), performs the given binary operation
  on them, and then writes the result to address (pc + 3)"
  (let [target-1   (program (program (+ pc 1)))
        target-2   (program (program (+ pc 2)))
        source-pos (program (+ pc 3))]
     (assoc program source-pos (f target-1 target-2))))

(defn run-intcode
  "The Intcode interpreter - takes a sequence of integers as input, and spits
  out the completed program state"
  ([program] (run-intcode program 0))
  ([program pc]
   (let [opcode (program pc)]
     (case opcode
       1  (recur (binary-op program pc +) (+ pc 4))
       2  (recur (binary-op program pc *) (+ pc 4))
       99 program
       :error))))

(defn get-starting-program
  "Read the input from the given file."
  []
  (mapv read-string (str/split (slurp "resources/day2.txt") #",")))

(defn part-1
  "Solves part 1 of day 2"
  [program]
  (let [program' (assoc program 1 12 2 2)]
    ((run-intcode program') 0)))

(defn part-2
  "Solves part 2 of day 2"
  [program]
  (first (for [noun (range 100)
               verb (range 100)
               :let [program' (assoc program 1 noun 2 verb)]
               :when (= ((run-intcode program') 0) 19690720)]
           (+ (* 100 noun) verb))))

(defn -main
  "Runs day 2"
  [& args]
  (let [program  (get-starting-program)
        result-1 (part-1 program)
        result-2 (part-2 program)]
    (println "Part 1:" result-1)
    (println "Part 2:" result-2)))
