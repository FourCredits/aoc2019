(ns aoc2019.day-13
  (:gen-class)
  (:require [aoc2019.intcode :as i]
            [aoc2019.utils :refer [groups-of]]
            [lanterna.screen :as s]))

; Part 1

(def tile-ids
  {:empty 0
   :wall 1
   :block 2
   :paddle 3
   :ball 4})

(defn make-board [instructions]
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
       make-board
       (filter #(= (:block tile-ids) (val %)))
       count))

; Part 2

(defn find-kind
  "Returns all the positions where there's a certain kind of block"
  [board kind]
  (map key (filter #(= kind (val %)) board)))

(defn no-blocks?
  "Returns true if there are no more blocks to destroy"
  [board]
  (empty? (find-kind board (tile-ids :block))))

(defn get-score
  "Gets the score out of the rest of the game state"
  [board]
  (val (first (filter #(= [-1 0] (key %)) board))))

(def tile-reps
  {0 "."
   1 "#"
   2 "x"
   3 "_"
   4 "o"})

(defn draw-board [screen board]
  (s/clear screen)
  (doseq [[[x y] v] board]
    (if (= [x y] [-1 0])
      (s/put-string screen 0 23 (str "Score:" v))
      (s/put-string screen x y (tile-reps v))))
  (s/redraw screen))

(defn interactive-joystick [screen]
  (case (s/get-key-blocking screen {:timeout 100})
    :left  -1
    :right  1
    nil     0))

(defn part-2-interactive
  "Runs part 2 as a game. It's basically unplayable - you have to time your
  moves perfectly. I don't think I can be bothered getting it to work properly"
  [program]
  (let [screen (s/get-screen)]
    (s/in-screen
     screen
     (loop [computer (i/make-computer (assoc program 0 2))]
       (let [{:keys [output] :as computer'} (i/run-until-needs-input computer)
             board (make-board output)]
         (draw-board screen board)
         (Thread/sleep 200)
         (if (no-blocks? board)
           (do
             (s/put-string screen 0 24 "Game over, man! Game over!")
             (s/redraw screen)
             (s/get-key-blocking screen)
             (get-score board))
           (recur (i/input-value computer' (interactive-joystick screen)))))))))

(defn automatic-joystick [board]
  (let [ball-position  (first (find-kind board (tile-ids :ball)))
        paddle-postion (first (find-kind board (tile-ids :paddle)))]
    (compare (first ball-position) (first paddle-postion))))

(defn part-2
  "Runs the game automatically, without displaying it to the user. It still
  isn't particularly fast, but that's more due to the fact that the game takes
  a while to finish."
  [program]
  (loop [computer (i/make-computer (assoc program 0 2))]
    (let [{:keys [output] :as computer'} (i/run-until-needs-input computer)
          board (make-board output)]
      (if (no-blocks? board)
        (get-score board)
        (recur (i/input-value computer' (automatic-joystick board)))))))

(def filepath "resources/day13.txt")

(defn -main [& _]
  (let [input (i/read-intcode-program filepath)]
    (println (part-1 input))
    (println (part-2 input))))
