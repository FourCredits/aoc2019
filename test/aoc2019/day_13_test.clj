(ns aoc2019.day-13-test
  (:require [clojure.test :as t]
            [aoc2019.day-13 :refer [filepath part-1]]
            [aoc2019.intcode :as i]))

(def puzzle-input (i/read-intcode-program filepath))

(t/deftest part-1-real
  (t/is (= (part-1 puzzle-input) 265)))
