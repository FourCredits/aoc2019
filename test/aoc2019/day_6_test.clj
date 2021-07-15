(ns aoc2019.day-6-test
  (:require [clojure.test :refer :all]
            [aoc2019.day-6 :refer :all]))

(def example-string-1
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

(def example-string-2
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
K)L
K)YOU
I)SAN")

(deftest part-1-basic
  (is (= (part-1 (parse "COM)AAA")) 1)))

(deftest part-1-example
  (is (= (part-1 (parse example-string-1)) 42)))

(deftest part-1-real
  (is (= (part-1 (get-input)) 254447)))

(deftest part-2-example
  (is (= (part-2 (parse example-string-2)) 4)))

(deftest part-2-real
  (is (= (part-2 (get-input)) 445)))
