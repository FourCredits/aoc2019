(ns aoc2019.day-13-test
  (:require [clojure.test :refer :all]
            [aoc2019.day-13 :refer :all]
            [aoc2019.intcode :as i]))

(def puzzle-input (i/read-intcode-program filepath))

(deftest draw-board-test
  (is (= (draw-board [1 2 3 6 5 4])
         {[1 2] 3 [6 5] 4})))

(deftest part-1-real
  (is (= (part-1 puzzle-input)
         265)))
