(ns aoc2019.intcode
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

(defn intcode
  "The Intcode interpreter - takes a sequence of integers as input, and spits
  out the completed program state"
  ([program] (intcode program 0))
  ([program pc]
   (let [opcode (nth program pc)]
     (case opcode
       ; Addition
       1  (recur (binary-op program pc +) (+ pc 4))
       ; Multiplication
       2  (recur (binary-op program pc *) (+ pc 4))
       ; Read input
       3  (let [input (Integer/valueOf (read-line))
                program' (assoc program (nth program (inc pc)) input)]
            (recur program' (+ pc 2)))
       ; Print output
       4 (do
           (println (program (program (inc pc))))
           (recur program (+ pc 2)))
       ; Halt
       99 program
       :error))))

(defn get-input
  "Read the input from the given file."
  [filepath]
  (mapv
   #(Integer/valueOf %)
   (str/split (str/trim (slurp filepath)) #",")))
