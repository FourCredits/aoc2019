(ns aoc2019.day-2-test
  (:require [clojure.test :as t]
            [aoc2019.day-2 :refer [part-1 part-2]]
            [aoc2019.intcode :refer [read-intcode-program]]))

(def puzzle-input (read-intcode-program "resources/day2.txt"))

(t/deftest real
  (t/are [part answer] (= (part puzzle-input) answer)
    part-1 6730673
    part-2 3749))