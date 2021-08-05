(ns aoc2019.day-13
  (:gen-class)
  (:require [aoc2019.intcode :as i]
            [aoc2019.utils :refer [groups-of]]))

(def tile-id-meanings
  {:empty 0
   :wall 1
   :block 2
   :paddle 3
   :ball 4})

(defn draw-board [instructions]
  (->> instructions
       (groups-of 3)
       (reduce
        (fn [acc [x y tile-id]]
          (assoc acc [x y] tile-id))
        {})))

(defn part-1 [program]
  (->> program
       i/run-pure
       :output
       draw-board
       (filter #(= (:block tile-id-meanings) (val %)))
       count))

(defn part-2 [program]
  nil)

(def filepath "resources/day13.txt")

(defn -main [& args]
  (let [input (i/read-intcode-program filepath)]
    (println (part-1 input))
    (println (part-2 input))))
