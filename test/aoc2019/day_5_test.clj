(ns aoc2019.day-5-test
  (:require [clojure.test :refer :all]
            [aoc2019.day-5 :refer :all]
            [aoc2019.intcode :refer [read-intcode-program]]))

(def input-program (read-intcode-program filepath))

(deftest part-1-real
  (is (= (part-1 input-program) 7259358)))

(deftest part-2-real
  (is (= (part-2 input-program) 11826654)))
