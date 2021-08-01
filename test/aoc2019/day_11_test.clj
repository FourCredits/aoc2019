(ns aoc2019.day-11-test
  (:require [clojure.test :refer :all]
            [aoc2019.day-11 :refer :all]
            [aoc2019.intcode :refer :all]))

(def puzzle-input (read-intcode-program filepath))

(deftest part-1-real
  (is (= (part-1 puzzle-input) 2082)))
