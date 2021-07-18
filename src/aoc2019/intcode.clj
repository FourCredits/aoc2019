(ns aoc2019.intcode
  (:gen-class)
  (:require [clojure.string :as str]))

(defn digits
  "Takes a number `n` and returns its digits as a list"
  [n]
  (loop [acc '()
         i   n]
    (if (zero? i)
      acc
      (recur (conj acc (mod i 10)) (int (/ i 10))))))

(defn process-opcode-and-modes
  "Takes in a number and returns an opcode (the two rightmost digits), and a
  list of parameter modes (the left digits, in reverse order). After the given
  digits of the mode is an infinite lazy list of 0s.
  `(process-opcode-and-modes 1002)` returns `[2 [0 1 0 0 ...]]`"
  [n]
  (let [opcode (mod n 100)
        modes  (reverse (digits (int (/ n 100)) ))]
    [opcode (concat modes (repeat 0))]))

(defn intcode
  "The Intcode interpreter - takes a sequence of integers as input, and spits
  out the completed program state"
  ([program] (intcode program 0))
  ([program pc]
   (let [[opcode modes]  (process-opcode-and-modes (nth program pc))
         read-param      (fn [n]
                           (let [param (nth program (+ pc n))]
                             (if (zero? (nth modes (dec n)))
                               (nth program param)
                               param)))
         get-write-param (fn [n] (nth program (+ pc n)))
         write-at-param  (fn [pos v] (assoc program (get-write-param pos) v))
         binary-op       (fn [f]
                           (write-at-param 3 (f (read-param 1) (read-param 2))))
         jump-op         (fn [f]
                           (if (f (read-param 1)) (read-param 2) (+ pc 3)))
         comp-op         (fn [f] (binary-op #(if (f %1 %2) 1 0)))
         input-from-user (fn [] (let [input (Integer/valueOf (read-line))]
                                  (write-at-param 1 input)))
         print-op        (fn [] (do (println (read-param 1)) program))]
     (case opcode
       1 (recur (binary-op +)     (+ pc 4))                     ; Add
       2 (recur (binary-op *)     (+ pc 4))                     ; Mult
       3 (recur (input-from-user) (+ pc 2))                     ; Read
       4 (recur (print-op)        (+ pc 2))                     ; Print
       5 (recur program           (jump-op (complement zero?))) ; Jump if true
       6 (recur program           (jump-op zero?))              ; Jump if false
       7 (recur (comp-op <)       (+ pc 4))                     ; Less than
       8 (recur (comp-op =)       (+ pc 4))                     ; Equals
       99 program                                               ; Halt
       :error))))

(defn get-input
  "Read the input program from the given file."
  [filepath]
  (mapv
   #(Integer/valueOf %)
   (str/split (str/trim (slurp filepath)) #",")))
