(ns aoc2019.day-3
  (:gen-class)
  (:require [clojure.string :as str]
            [clojure.set :as set]))

(defn parse-line
  "Parses a single line"
  [line]
  (for [command (str/split line #",")]
    [(first command) (read-string (subs command 1))]))

(defn parse-input
  "Turns a string of input into a pair of vectors. Each vector is a collection
  of pairs, where the first element is one of {:left, :right, :up, :down}, and
  the second is a number. This represents the two wires, and the description of
  how they move from the centre."
  [input]
  (let [[line1 line2] (str/split-lines input)]
    (map parse-line [line1 line2])))

(def ^:private direction-vectors
  {\L [-1  0]
   \R [ 1  0]
   \U [ 0  1]
   \D [ 0 -1]})

(defn make-path
  "Takes a list of instructions describing the path a wire takes, and returns
  the positions that the wire passes through"
  ([wire] (make-path wire [0 0]))
  ([wire [x y]]
   (if-let [[direction target] (first wire)]
     (let [[dx dy]  (direction-vectors direction)
           progress (map #(vector (+ x (* dx %)) (+ y (* dy %)))
                         (range 1 (inc target)))]
       (concat progress (make-path (rest wire) (last progress)))))))

(defn manhattan-distance [[x y]]
  (+ (Math/abs x) (Math/abs y)))

(defn part-1
  "Takes in a string of input, representing the paths of two wires, and returns
  the manhattan distance of the nearest intersection to the point that the two
  wires start from."
  [input]
  (let [[wire1 wire2] (parse-input input)
        path1 (set (make-path wire1))
        path2 (set (make-path wire2))]
    (->> (set/intersection path1 path2)
         (map manhattan-distance)
         (sort)
         (first))))

(defn -main
  "Runs day 3"
  [& args]
  (println (part-1 (slurp "resources/day3.txt"))))
