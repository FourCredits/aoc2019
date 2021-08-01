(ns aoc2019.day-11
  (:gen-class)
  (:require [aoc2019.intcode :refer :all]))

; A robot consists of an intcode machine, a current position, and direction.
; Each tick will update will do some running of the machine, measure the
; output, and give it some input, updating the position and the direction.

(defn make-robot
  "Default-initialises a robot"
  [program]
  {:computer (make-computer program)
   :position [0 0]
   :direction [0 1]})

(def white 1)
(def black 0)

(defn turn-left [[x y]] [(- y) x])

(defn turn-right [[x y]] [y (- x)])

(defn update-direction
  [current turn]
  {:pre [(or (= turn 1) (= turn 0))]}
  (if (zero? turn)
    (turn-left current)
    (turn-right current)))

(defn robot-step
  "Takes a step of the robot. If the square under the robot is black, inputs it
  the value 0, otherwise inputs the value 1. Then, the robot runs until it
  needs more input, in that time outputting two numbers. The first is what
  colour to paint the square underneath it - 0 for black, 1 for white. The
  second is the direction to turn - 0 for 90 degrees left, 1 for 90 degrees
  right.
  
  The program returns the updated robot state, as well as the updated set of
  panels the robot has painted white."
  [{:keys [computer position direction] :as robot} visited-panels]
  (let [; Input value
        computer' (input-value computer (get visited-panels position black))
        ; Run until needs input
        {:keys [output] :as computer''} (run-until-needs-input computer')
        [colour turn] output
        direction' (update-direction direction turn)]
    [; Updated robot
     {:computer (assoc computer'' :output [])
      :position (map + position direction')
      :direction direction'}
     ; Updated visited-panels
     (assoc visited-panels position colour)]))

(defn run-robot [robot panels]
  (->> [robot panels]
       (iterate #(apply robot-step %))
       (drop-while #(not (get-in % [0 :computer :halted?])))
       (first)))

(defn part-1 [program]
  (->> [(make-robot program) {}]
       (apply run-robot)
       (second)
       (count)))

(defn bounds
  "Takes a collection of (x, y) coordinates, and returns two new coordinates,
  describing the smallest rectangle the coordinates fall into."
  [coords]
  (let [xs (map first coords)
        ys (map second coords)]
    [[(apply min xs) (apply min ys)]
     [(apply max xs) (apply max ys)]]))

(defn part-2 [program]
  (let [[_ panels] (run-robot (make-robot program) {[0 0] white})
        [[min-x min-y] [max-x max-y]] (bounds (keys panels))]
    (reverse
     (for [y (range min-y (inc max-y))]
       (for [x (range min-x (inc max-x))]
         (get panels [x y] black))))))

(def filepath "resources/day11.txt")

(defn -main [& args]
  (let [input (read-intcode-program filepath)]
    (println (part-1 input))
    (println (part-2 input))))
