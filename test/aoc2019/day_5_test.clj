(ns aoc2019.day-5-test
  (:require [clojure.test :as t]
            [aoc2019.day-5 :refer [filepath part-1 part-2]]
            [aoc2019.intcode :refer [read-intcode-program]]))

(def puzzle-input (read-intcode-program filepath))

(t/deftest real
  (t/are [part answer] (= (part puzzle-input) answer)
    part-1 7259358
    part-2 11826654))
