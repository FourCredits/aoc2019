(ns aoc2019.day-8
  (:gen-class)
  (:require [clojure.string :as str]))

(defn groups-of
  "Separates `coll` into groups of `n`"
  [n coll]
  (loop [remainder coll
         result []]
    (if (empty? remainder)
      result
      (recur (drop n remainder)
             (conj result (take n remainder))))))

(defn count-digit
  ([n] (partial count-digit n))
  ([n coll] (count (filter #{n} coll))))

(defn verify-transmission
  "Takes a collection of numbers `coll`, a width `w`, and a height `h`. Finds
  the layer of the image with the fewest 0s, and then returns (num of 1s on that
  layer * num of 2s on that layer)"
  [coll w h]
  {:pre [(zero? (mod (count coll) (* w h)))]}
  (->> coll
       (groups-of (* w h))
       (sort-by (count-digit 0))
       (first)
       ((juxt (count-digit 1) (count-digit 2)))
       (apply *)))

(defn transpose [m] (apply mapv vector m))

(defn determine-color [pixels]
  (if-let [[p & ps] (seq pixels)]
    (if (= p 2) (recur ps) p)))

(defn decode-image [coll w h]
  (->> coll
       (groups-of (* w h))
       (transpose)
       (map determine-color)
       (groups-of w)))

(defn part-1 [input] (verify-transmission input 25 6))

(defn part-2 [input] (decode-image input 25 6))

(def filepath "resources/day8.txt")

(defn char->int [i] (- (int i) (int \0)))

(defn get-input []
  (map char->int (str/trim (slurp filepath))))

(defn -main [& args]
  (let [input (get-input)]
    (println (part-1 input))
    (doseq [i (part-2 input)] (println i))))
