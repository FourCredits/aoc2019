(ns aoc2019.day-12
  (:gen-class)
  (:require [clojure.string :as str]
            [aoc2019.utils :as u]
            [clojure.math.numeric-tower :refer [lcm]]))

(defn update-velocity-amount
  "Takes two moons. Returns the amount to update the velocity of the first moon
  by, according to the rules of gravity defined in the question."
  [m1 m2]
  (map compare (:pos m2) (:pos m1)))

(defn update-velocity [moon others]
  (let [diffs (map #(update-velocity-amount moon %) others)]
    (update moon :vel #(apply u/vector-add % diffs))))

(defn update-position [{:keys [vel] :as moon}]
  (update moon :pos #(u/vector-add vel %)))

(defn tick
  "One simulation tick of the moons of jupiter"
  [moons]
  (for [moon moons
        :let [others (remove #{moon} moons)]]
    (update-position (update-velocity moon others))))

(defn simulate-until [n moons]
  (if (zero? n)
    moons
    (recur (dec n) (tick moons))))

(defn sum-of-absolutes [coll]
  (apply + (map #(Math/abs %) coll)))

(defn total-energy [{:keys [pos vel]}]
  (* (sum-of-absolutes pos) (sum-of-absolutes vel)))

(defn part-1 ([moons] (part-1 1000 moons))
  ([n moons] (apply + (map total-energy (simulate-until n moons)))))

; Part 2

(defn get-axis
  "Takes the state of the system, an axis to find, and returns a collection of
  the positions and velocities of each moon at that axis"
  [axis moons]
  (for [{:keys [pos vel]} moons]
    [(nth pos axis) (nth vel axis)]))

(defn part-2 [moons]
  (let [[_ & states] (iterate tick moons)]
    (reduce
     lcm
     (for [axis [0 1 2]]
       (let [initial-axis (get-axis axis moons)]
         (->> states
              (u/find-first-index #(= initial-axis (get-axis axis %)))
              ; We ignore the first state (a copy of the original), so we have
              ; to add 1 to get back to the correct number
              inc))))))

; Input

(def filepath "resources/day12.txt")

(defn parse-line [line]
  (map #(Integer/valueOf (str/replace % #"[^-0-9]" "")) (str/split line #",")))

(defn make-moon [starting-position]
  {:pos starting-position
   :vel [0 0 0]})

(defn get-input []
  (map (comp make-moon parse-line) (str/split-lines (slurp filepath))))

(defn -main [& _]
  (let [input (get-input)]
    (println (part-1 input))
    (println "This bit takes a while, give it a sec")
    (println (part-2 input))))
