(ns aoc2019.day-2-test
  (:require [clojure.test :refer :all]
            [aoc2019.day-2 :refer :all]))

(deftest basic-int-code-tests
  (testing "quit"
    (is (= (run-intcode [99 98 97]) [99 98 97])
        "Base case: 99, followed by anything, immediately quits"))
  (testing "add"
    (is (= (run-intcode [1 0 0 0 99]) [2 0 0 0 99])
        "1 + 1 = 2"))
  (testing "multiply"
    (is (= (run-intcode [2 3 0 3 99]) [2 3 0 6 99])
        "3 * 2 = 6"))
  (testing "data space"
    (is (= (run-intcode [1 1 1 4 99 5 6 0 99]) [30 1 1 4 2 5 6 0 99])
        "You should be able to write into the space after a 99 command")))

(deftest part-1-real
  (testing "Part 1 of day 2"
    (is (= (part-1 (get-starting-program)) 6730673))))

(deftest part-2-real
  (testing "Part 2 of day 2"
    (is (= (part-2 (get-starting-program)) 3749))))
