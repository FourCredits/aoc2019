(ns aoc2019.core-test
  (:require [clojure.test :refer :all]
            [aoc2019.core :refer :all]))

(deftest part-1-test
  (testing "part 1 works"
    (is (= (part-1 12) 2))
    (is (= (part-1 14) 2))
    (is (= (part-1 1969) 654))
    (is (= (part-1 100756) 33583))))
