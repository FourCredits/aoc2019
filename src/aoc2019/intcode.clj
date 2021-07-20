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
  digits of the mode is an bunch of 0s - hopefully enough for future purposes.
  `(process-opcode-and-modes 1002)` returns `[2 [0 1 0 0 ...]]`"
  [n]
  (let [opcode (mod n 100)
        modes (reverse (digits (int (/ n 100))))]
    [opcode (concat modes (repeat 5 0))]))

(defn advance-by
  "Advances the program counter by n."
  [n computer]
  (update computer :pc #(+ % n)))

(defn parse-read-parameter
  "Reads from `memory` at `pc + offset`, depending on the mode:
  - 0: Treats it as an address, dereferencing accordingly.
  - 1: Treats it as an immediate value, just returning it."
  [{:keys [memory pc]} offset modes]
  {:pre [(pos? offset)]}
  (let [param (get memory (+ pc offset))]
    (if (zero? (nth modes (dec offset)))
      (get memory param)
      param)))

(defn write-to-memory-from-parameter
  [{:keys [memory pc] :as computer} offset value]
  (let [address (get memory (+ pc offset))]
    (assoc-in computer [:memory address] value)))

(defn step
  "Takes a single step of the intcode machine. Does *not* process input
  instructions."
  ([{:keys [memory pc output] :as computer}]
   (let [[opcode modes] (process-opcode-and-modes (nth memory pc))]
     (step computer opcode modes)))
  ([{:keys [memory pc] :as computer} opcode modes]
   (letfn [(read-param [n] (parse-read-parameter computer n modes))
           (write-at-param [k v] (write-to-memory-from-parameter computer k v))
           (binary-op [f] (write-at-param 3 (f (read-param 1) (read-param 2))))
           (comparison-op [f] (binary-op #(if (f %1 %2) 1 0)))
           (jump-op [f]
             (update computer :pc #(if (f (read-param 1))
                                     (read-param 2)
                                     (+ % 3))))]
     (case opcode
       1  (advance-by 4 (binary-op +))
       2  (advance-by 4 (binary-op *))
       4  (advance-by 2 (update computer :output #(conj % (read-param 1))))
       5  (jump-op (complement zero?))
       6  (jump-op zero?)
       7  (advance-by 4 (comparison-op <))
       8  (advance-by 4 (comparison-op =))
       99 (assoc computer :halted? true)
       :error))))

(defn run-until-needs-input
  "Runs the provided intcode computer until it hits an instruction that needs
  input from the user, or the program halts."
  [{:keys [memory pc] :as computer}]
  (let [[opcode modes] (process-opcode-and-modes (nth memory pc))]
    (cond
      (= 3 opcode) computer
      (:halted? computer) computer
      :else (recur (step computer opcode modes)))))

(defn input-value
  "Writes `value` to the address given at `pc + 1`. Requires that the
  instruction at pc is 3."
  [{:keys [memory pc] :as computer} value]
  {:pre [(= 3 (get memory pc))]}
  (advance-by 2 (write-to-memory-from-parameter computer 1 value)))

(defn input-values
  "Writes the first value from `values`, runs the `computer` until it needs
  more input, then gives the next value, and so on."
  [values computer]
  (reduce #(input-value (run-until-needs-input %1) %2) computer values))

(defn make-computer
  "Default values for pc and output for a given program"
  [program]
  {:memory program :pc 0 :output []})

(defn run-io
  "Runs the given program on an intcode computer, taking input and output
  interactively."
  [program]
  (loop [computer (make-computer program)]
    (let [{:keys [halted? output] :as computer'} (run-until-needs-input
                                                   computer)]
      (doseq [item output] (println item))
      (if halted?
        computer'
        (->> (read-line)
             (Integer/valueOf)
             (input-value computer')
             (run-until-needs-input)
             (recur))))))

(defn run-pure
  "Runs the given `program` on an intcode computer programmatically, taking
  input from `inputs` and returning outputs as part of the `:output` of the
  computer. If you don't have enough input for what the computer asks for, you
  get an error."
  ([program] (run-pure program []))
  ([program inputs]
   (loop [computer (make-computer program)
          outputs-so-far []
          to-be-inputted inputs]
     (let [{:keys [halted? output] :as computer'} (run-until-needs-input
                                                     computer)
           outputs' (if output
                      (concat outputs-so-far output)
                      outputs-so-far)]
       (cond
         halted? (assoc computer' :output outputs')
         (empty? to-be-inputted) :error
         :else (recur (input-value computer (first to-be-inputted))
                      outputs'
                      (rest to-be-inputted)))))))

(defn read-intcode-program
  "Read the input program from the given file."
  [filepath]
  (mapv #(Integer/valueOf %)
        (str/split (str/trim (slurp filepath)) #",")))

(def get-input read-intcode-program)
