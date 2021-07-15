(ns aoc2019.day-6-test
  (:require [clojure.test :refer :all]
            [aoc2019.day-6 :refer :all]))

(def example-string
  "COM)B
B)C
C)D
D)E
E)F
B)G
G)H
D)I
E)J
J)K
K)L")

(deftest part-1-basic
  (is (= (part-1 (parse "COM)AAA")) 1)))

(deftest part-1-example
  (is (= (part-1 (parse example-string))) 42))
