(ns aoc2019.utils
  (:gen-class))

(defn manhattan-distance [[x y]]
  (+ (Math/abs x) (Math/abs y)))

(defn one-based-index-of
  "Like `.indexOf`, but one-based, like MATLAB."
  [coll v]
  (inc (.indexOf coll v)))

(defn intersection [c1 c2]
  (filter #(some #{%} c1) c2))

(defn groups-of
  "Separates `coll` into groups of `n`"
  [n coll]
  (loop [remainder coll
         result []]
    (if (empty? remainder)
      result
      (recur (drop n remainder)
             (conj result (take n remainder))))))

(defn transpose [m] (apply mapv vector m))

(defn count-occurrence
  "Counts the occurence of a value in a collection"
  ([v] (partial count-occurrence v))
  ([v coll] (count (filter #{v} coll))))

(defn digits
  "Takes a number `a` and returns its digits as a list"
  [a]
  (loop [acc '()
         b a]
    (if (zero? b)
      acc
      (recur (conj acc (mod b 10)) (int (/ b 10))))))

(defn gcd [a b]
  (if (zero? b)
    a
    (recur b (mod a b))))

(defn arg
  "Finds the argument of a complex number."
  [real imag]
  (Math/atan2 imag real))

(defn magnitude
  "Finds the magnitude of a vector (or complex number)."
  [vect]
  (Math/sqrt (apply + (map #(* % %) vect))))

(defn complex-sub
  "Subtracts two complex numbers"
  [[a1 b1] [a2 b2]]
  [(- a1 a2) (- b1 b2)])
