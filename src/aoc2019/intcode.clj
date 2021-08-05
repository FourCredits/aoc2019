(ns aoc2019.intcode
  (:gen-class)
  (:require [clojure.string :as str]
            [aoc2019.utils :refer [digits]]))

; Intcode internal functions

(defn- process-instruction
  "Takes in a number and returns an opcode (the two rightmost digits), and a
  list of parameter modes (the left digits, in reverse order). After the given
  digits of the mode is an bunch of 0s - hopefully enough for future purposes.
  `(process-instruction 1002)` returns `[2 [0 1 0 0 ...]]`"
  [n]
  (let [opcode (mod n 100)
        modes (reverse (digits (quot n 100)))]
    [opcode (concat modes (repeat 5 0))]))

(defn- advance-by
  "Advances the program counter by n."
  [n computer]
  (update computer :pc #(+ n %)))

(defn- get-mem
  "Abstracts the process of reading from memory. With no address given, will
  default to pc"
  ([computer address] (get-in computer [:memory address] 0))
  ([{:keys [memory pc]}] (get memory pc 0)))

(defn- assoc-mem
  "Abstracts the process of writing to memory"
  [computer k v]
  (assoc-in computer [:memory k] v))

(defn- read-from-param
  "Reads from the param-num -th parameter, respecting the appropriate mode."
  [{:keys [pc relative-base] :as computer} param-num modes]
  {:pre [(pos? param-num)]}
  (let [param (get-mem computer (+ pc param-num))]
    (case (nth modes (dec param-num))
      0 (get-mem computer param)
      1 param
      2 (get-mem computer (+ relative-base param)))))

(defn- write-to-param
  "Writes to the param-num -th parameter, respecting the appropriate mode."
  [{:keys [pc relative-base] :as computer} modes param-num value]
  {:pre [(pos? param-num)]}
  (let [param (get-mem computer (+ pc param-num))
        address (case (nth modes (dec param-num))
                  0 param
                  1 :error
                  2 (+ relative-base param))]
    (assoc-mem computer address value)))

; User-level intcode functions

(defn step
  "Takes a single step of the intcode machine. Does *not* process input
  instructions."
  ([{:keys [memory pc] :as computer}]
   (let [[opcode modes] (process-instruction (get-mem computer pc))]
     (step computer opcode modes)))
  ([computer opcode modes]
   {:pre [(not (:halted? computer))]}
   (letfn [(read-p [n] (read-from-param computer n modes))
           (write-p [k v] (write-to-param computer modes k v))
           (binary-op [f] (write-p 3 (f (read-p 1) (read-p 2))))
           (comparison-op [f] (binary-op #(if (f %1 %2) 1 0)))
           (jump-op [f]
             (update computer :pc #(if (f (read-p 1)) (read-p 2) (+ % 3))))]
     (case opcode
       1  (advance-by 4 (binary-op +))
       2  (advance-by 4 (binary-op *))
       4  (advance-by 2 (update computer :output #(conj % (read-p 1))))
       5  (jump-op (complement zero?))
       6  (jump-op zero?)
       7  (advance-by 4 (comparison-op <))
       8  (advance-by 4 (comparison-op =))
       9  (advance-by 2 (update computer :relative-base #(+ % (read-p 1))))
       99 (assoc computer :halted? true)
       :error))))

(defn make-computer
  "Default values for pc and output for a given program"
  [program]
  {:memory (into {} (map vector (range) program))
   :pc 0
   :output []
   :relative-base 0})

(defn run-until-needs-input
  "Runs the provided intcode computer until it hits an instruction that needs
  input from the user, or the program halts."
  [{:keys [pc] :as computer}]
  (let [[opcode modes] (process-instruction (get-mem computer pc))]
    (cond
      (= 3 opcode) computer
      (:halted? computer) computer
      :else (recur (step computer opcode modes)))))

(defn input-value
  "Writes `value` to the address given at `pc + 1`. Requires that the
  instruction at pc is 3."
  [{:keys [memory pc] :as computer} value]
  {:pre [(= 3 (mod (get memory pc 0) 100))]}
  (let [[opcode modes] (process-instruction (get-mem computer pc))]
    (advance-by 2 (write-to-param computer modes 1 value))))

(defn input-values
  "Writes the first value from `values`, runs the `computer` until it needs
  more input, then gives the next value, and so on."
  [values computer]
  (reduce #(input-value (run-until-needs-input %1) %2) computer values))

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
          [i & is] inputs]
     (let [{:keys [halted? output] :as computer'} (run-until-needs-input
                                                     computer)
           outputs' (concat outputs-so-far output)]
       (cond
         halted? (assoc computer' :output outputs')
         i (recur (input-value computer' i) outputs' is)
         :else :error)))))

; Reading intcode programs

(defn read-intcode-program
  "Read the input program from the given file."
  [filepath]
  (mapv #(Long/valueOf %)
        (str/split (str/trim (slurp filepath)) #",")))
