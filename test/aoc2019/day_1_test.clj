(ns aoc2019.day-1-test
  (:require [clojure.test :as t]
            [aoc2019.day-1 :refer [part-1 part-2 get-input solve]]))

(t/deftest part-1-test
  (t/are [in out] (= (part-1 in) out)
    12     2
    14     2
    1969   654
    100756 33583))

(t/deftest part-2-test
  (t/are [in out] (= (part-2 in) out)
    14     2
    1969   966
    100756 50346))

(def puzzle-input (get-input))

(t/deftest real
  (t/are [part answer] (= (solve part puzzle-input) answer)
    part-1 3216868
    part-2 4822435))
