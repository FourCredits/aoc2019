(ns aoc2019.day-4-test
  (:require [clojure.test :as t]
            [aoc2019.day-4 :refer
             [criteria1 criteria2 get-input meets-criteria? part-1 part-2]]))

(t/deftest part-1-tests
  (t/are [n result] (= (meets-criteria? criteria1 n) result)
    111111 true
    223450 false
    123789 false
    122345 true
    111123 true
    135679 false))

(t/deftest part-2-tests
  (t/are [n result] (= (meets-criteria? criteria2 n) result)
    112233 true
    123444 false
    111122 true))

(def puzzle-input (get-input))

(t/deftest real
  (t/are [part answer] (= (apply part puzzle-input) answer)
    part-1 921
    part-2 603))
