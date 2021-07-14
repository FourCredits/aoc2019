(ns aoc2019.day-4-test
  (:require [clojure.test :refer :all]
            [aoc2019.day-4 :refer :all]))

(def test-input (get-input))

(deftest part-1-tests
  (are [n result] (= (meets-criteria? criteria1 n) result)
    111111 true
    223450 false
    123789 false
    122345 true
    111123 true
    135679 false))

(deftest part-1-real
  (is (= (apply part-1 test-input) 921)))

(deftest part-2-tests
  (are [n result] (= (meets-criteria? criteria2 n) result)
    112233 true
    123444 false
    111122 true))

(deftest part-2-real
  (is (= (apply part-2 test-input) 603)))
