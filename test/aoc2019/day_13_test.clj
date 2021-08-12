(ns aoc2019.day-13-test
  (:require [clojure.test :refer :all]
            [aoc2019.day-13 :refer :all]
            [aoc2019.intcode :as i]))

(def puzzle-input (i/read-intcode-program filepath))

(deftest part-1-real
  (is (= (part-1 puzzle-input)
         265)))
