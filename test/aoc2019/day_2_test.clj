(ns aoc2019.day-2-test
  (:require [clojure.test :refer :all]
            [aoc2019.day-2 :refer [part-1 part-2]]
            [aoc2019.intcode :refer [get-input]]))

(deftest part-1-real
  (testing "Part 1 of day 2"
    (is (= (part-1 (get-input)) 6730673))))

(deftest part-2-real
  (testing "Part 2 of day 2"
    (is (= (part-2 (get-input)) 3749))))
