(ns aoc2019.day-9-test
  (:require [clojure.test :refer :all]
            [aoc2019.day-9 :refer :all]
            [aoc2019.intcode :refer [read-intcode-program]]))

(def puzzle-input (read-intcode-program filepath))

(deftest part-1-real
  (is (= (part-1 puzzle-input) [2518058886])))
