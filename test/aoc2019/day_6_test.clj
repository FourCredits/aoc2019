(ns aoc2019.day-6-test
  (:require [clojure.test :as t]
            [aoc2019.day-6 :refer [get-input parse part-1 part-2]]))

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

(t/deftest part-1-basic
  (t/is (= (part-1 (parse "COM)AAA")) 1)))

(t/deftest part-1-example
  (t/is (= (part-1 (parse example-string-1)) 42)))

(t/deftest part-2-example
  (t/is (= (part-2 (parse example-string-2)) 4)))

(def puzzle-input (get-input))

(t/deftest real
  (t/are [part answer] (= (part puzzle-input) answer)
    part-1 254447
    part-2 445))
