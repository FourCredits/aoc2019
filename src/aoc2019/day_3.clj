(ns aoc2019.day-3
  (:gen-class)
  (:require [clojure.string :as str]
            [clojure.set :as set]
            [aoc2019.utils :as i]))

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

(defn part-1
  "Takes in a string of input, representing the paths of two wires, and returns
  the manhattan distance of the nearest intersection to the point that the two
  wires start from."
  [wires]
  (let [paths (map (comp set make-path) wires)]
    (->> (apply set/intersection paths)
         (map i/manhattan-distance)
         (sort)
         (first))))

(defn sum-of-steps
  "For each path in `paths`, finds the number of steps needed to reach the given
  intersection. Returns the sum of those numbers."
  [paths intersection]
  (apply + (map #(i/one-based-index-of % intersection) paths)))

(defn part-2
  [wires]
  (let [paths         (map make-path wires)
        intersections (apply set/intersection (map set paths))]
    (apply min (map #(sum-of-steps paths %) intersections))))

(defn parse-line
  "Parses a single line."
  [line]
  (for [command (str/split line #",")]
    [(first command) (Integer/valueOf (subs command 1))]))

(defn parse-input [input]
  (map parse-line (str/split-lines input)))

(defn get-input
  "Reads from the input file and returns a pair of vectors. Each vector is a
  collection of pairs, where the first element is one of {:left, :right, :up,
  :down}, and the second is a number. This represents the two wires, and the
  description of how they move from the centre."
  []
  (parse-input (slurp "resources/day3.txt")))

(defn -main
  "Runs day 3"
  [& _]
  (let [input (get-input)]
    (println (part-1 input))
    (println (part-2 input))))
