(ns aoc2019.day-1-test
  (:require [clojure.test :refer :all]
            [aoc2019.day-1 :refer :all]))

(deftest part-1-test
  (testing "part 1 works"
    (is (= (part-1 12) 2))
    (is (= (part-1 14) 2))
    (is (= (part-1 1969) 654))
    (is (= (part-1 100756) 33583))))

(deftest part-1-real
  (testing "real answer is correct"
    (is (= (let [input (get-input)] (solve part-1 input)) 3216868))))
