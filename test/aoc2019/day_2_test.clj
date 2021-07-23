(ns aoc2019.day-2-test
  (:require [clojure.test :refer :all]
            [aoc2019.day-2 :refer [part-1 part-2]]
            [aoc2019.intcode :refer [read-intcode-program]]))

(def filepath "resources/day2.txt")

(deftest part-1-real
  (testing "Part 1 of day 2"
    (is (= (part-1 (read-intcode-program filepath)) 6730673))))

(deftest part-2-real
  (testing "Part 2 of day 2"
    (is (= (part-2 (read-intcode-program filepath)) 3749))))
