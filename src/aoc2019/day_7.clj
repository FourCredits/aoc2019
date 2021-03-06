(ns aoc2019.day-7
  (:gen-class)
  (:require [aoc2019.intcode :as i]
            [clojure.math.combinatorics :as combo]))

(defn make-amplifier
  "Takes an input program, a phase setting, and returns an amplifier - a
  computer ready to take input signals."
  [program phase]
  (i/input-value (i/make-computer program) phase))

(defn series-connection
  "Takes any number of amplifiers, and produces an amplifier which is the
  combination of them. The output of `amp1` goes to `amp2`, and so on."
  [& amplifiers]
  (fn [input]
    (reduce (comp :output i/run-until-needs-input i/input-values)
            [input]
            amplifiers)))

(defn feedback-loop [& amplifiers]
  (fn [input]
    (loop [signal [input]
           amps amplifiers]
      (if-let [[a & as] (seq amps)]
        (let [a' (i/run-until-needs-input (i/input-values signal a))]
          (recur (:output a')
                 (concat as (when-not (:halted? a')
                              [(assoc a' :output [])])))) ; Reset the output
        signal))))

(defn run-amplifiers
  "Runs the `program` on five amplifiers, using the `settings` to configure
  them. Gives the first amplifier the input `0`, then chains the output to the
  next amplifier, and so on. Returns the output of the last amplifier."
  [connection-method program settings]
  (let [amplifiers (map #(make-amplifier program %) settings)
        cascade (apply connection-method amplifiers)]
    (first (cascade 0))))

(defn solve [program method [lo hi]]
  (apply max (map #(run-amplifiers method program %)
                  (combo/permutations (range lo (inc hi))))))

(defn part-1 [program] (solve program series-connection [0 4]))

(defn part-2 [program] (solve program feedback-loop [5 9]))

(defn -main [& _]
  (let [program (i/read-intcode-program "resources/day7.txt")]
    (println (part-1 program))
    (println (part-2 program))))
