(ns aoc2019.day-14
  (:gen-class)
  (:require [clojure.string :as str]
            [clojure.math.numeric-tower :as m]))

(defn chemicals-needed
  "Takes the map of possible reactions, the chemical to produce, the amount to
  produce, and the chemicals you already have to work with. Returns the needed
  chemicals, as well as what's left over."
  [reactions chemical amount starting]
  (let [[amount-produced inputs] (reactions chemical)
        starting-amount (get starting chemical 0)
        num-reactions (m/ceil (/ (- amount starting-amount) amount-produced))
        needed (into {} (map #(update % 1 (partial * num-reactions)) inputs))
        leftovers (merge-with +
                              starting
                              {chemical (- (* num-reactions amount-produced)
                                           amount)})]
    [needed leftovers]))

(defn ore-needed
  "Takes the map of possible reactions, and the amount of fuel to produce.
  Returns the amount of ore needed to produce that amount of fuel"
  [reactions amount]
  (loop [[[c a] & others] [["FUEL" amount]]
         leftovers {}]
    (cond
      (and (= c "ORE") (empty? others)) a
      (= c "ORE") (recur (concat others [[c a]]) leftovers)
      :else (let [[needed leftovers'] (chemicals-needed reactions c a leftovers)]
              (recur (merge-with + (into {} others) needed) leftovers')))))

(defn part-1
  "Special case of the `ore-needed` function: How much ore is needed to produce
  a single unit of fuel"
  [reactions]
  (ore-needed reactions 1))

; Part 2

(defn find-bounds
  "Finds the order of magnitude (in terms of a lower and upper bound) of fuel
  produced by using target ore."
  [reactions target]
  (loop [low (quot target (ore-needed reactions 1))
         high (* 10 low)]
    (if (>= (ore-needed reactions high) target)
      [low high]
      (recur high (* 10 high)))))

(defn part-2
  "Performs a binary search to find how much fuel is produced with 1 trillion
  ore, given the set of reactions"
  [reactions]
  (let [target 1000000000000]
    (loop [[low high] (find-bounds reactions target)]
      (let [mid (quot (+ low high) 2)
            ore (ore-needed reactions mid)]
        (cond
          (>= low (dec high)) mid
          (< ore target) (recur [mid high])
          (> ore target) (recur [low mid])
          :else mid)))))

; Input

(def filepath "resources/day14.txt")

(defn parse-element [string]
  (let [[amount chemical] (str/split string #" ")]
    [chemical (Long/valueOf amount)]))

(defn parse-line [line]
  (let [[input-string output] (str/split line #" => ")
        inputs (into {} (map parse-element (str/split input-string #", ")))
        [out-chemical out-amount] (parse-element output)]
    [out-chemical [out-amount inputs]]))

(defn parse [input-string]
  (->> input-string
       str/split-lines
       (map parse-line)
       (into {})))

(defn get-input []
  (parse (slurp filepath)))

(defn -main []
  (let [input (get-input)]
    (println (part-1 input))
    (println (part-2 input))))
