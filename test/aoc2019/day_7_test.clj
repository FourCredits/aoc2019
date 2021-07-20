(ns aoc2019.day-7-test
  (:require [clojure.test :refer :all]
            [aoc2019.day-7 :refer :all]
            [aoc2019.intcode :refer [get-input]]))

(def puzzle-input (get-input "resources/day7.txt"))

; Part 1

(def testcases-part-1
  [{:program [3 15 3 16 1002 16 10 16 1 16 15 15 4 15 99 0 0]
    :settings [4 3 2 1 0]
    :output 43210}
   {:program [3 23 3 24 1002 24 10 24 1002 23 -1 23 101 5 23 23 1 24 23 23 4 23
              99 0 0]
    :settings [0 1 2 3 4]
    :output 54321}
   {:program [3 31 3 32 1002 32 10 32 1001 31 -2 31 1007 31 0 33 1002 33 7 33 1
              33 31 31 1 32 31 31 4 31 99 0 0 0]
    :settings [1 0 4 3 2]
    :output 65210}])

(deftest run-amplifiers-test
  (are [testcase]
       (let [{:keys [program settings output]} testcase]
         (= output (run-amplifiers series-connection program settings)))
    (testcases-part-1 0)
    (testcases-part-1 1)
    (testcases-part-1 2)))

(deftest part-1-examples
  (are [testcase]
       (let [{:keys [program output]} testcase]
         (= output (part-1 program)))
    (testcases-part-1 0)
    (testcases-part-1 1)
    (testcases-part-1 2)))

(deftest part-1-real
  (is (= (part-1 puzzle-input) 47064)))

; Part 2

(def testcases-part-2
  [{:program [3 26 1001 26 -4 26 3 27 1002 27 2 27 1 27 26 27 4 27 1001 28 -1
              28 1005 28 6 99 0 0 5]
    :settings [9 8 7 6 5]
    :max-output 139629729}
   {:program [3 52 1001 52 -5 52 3 53 1 52 56 54 1007 54 5 55 1005 55 26 1001
              54 -5 54 1105 1 12 1 53 54 53 1008 54 0 55 1001 55 1 55 2 53 55
              53 4 53 1001 56 -1 56 1005 56 6 99 0 0 0 0 10]
    :settings [9 7 8 5 6]
    :max-output 18216}])

(deftest part-2-examples
  (are [testcase]
       (let [{:keys [program max-output]} testcase]
         (= max-output (part-2 program)))
    (testcases-part-2 0)
    (testcases-part-2 1)))

(deftest part-2-real
  (is (= (part-2 puzzle-input) 4248984)))
