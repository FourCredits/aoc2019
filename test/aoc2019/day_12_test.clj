(ns aoc2019.day-12-test
  (:require [clojure.test :as t]
            [aoc2019.day-12 :refer
             [get-input make-moon part-1 part-2 simulate-until tick
              update-position update-velocity update-velocity-amount]]))

(def starting-positions-1
  [{:pos [-1  0  2] :vel [0 0 0]}
   {:pos [2 -10 -7] :vel [0 0 0]}
   {:pos [4  -8  8] :vel [0 0 0]}
   {:pos [3   5 -1] :vel [0 0 0]}])

(def starting-positions-2
  [{:pos [-8 -10  0] :vel [0 0 0]}
   {:pos [5   5 10] :vel [0 0 0]}
   {:pos [2  -7  3] :vel [0 0 0]}
   {:pos [9  -8 -3] :vel [0 0 0]}])

(t/deftest update-velocity-amount-test
  (t/are [i1 i2 o1] (= (update-velocity-amount (make-moon i1) (make-moon i2)) o1)
    [1 1 1] [4 4 4] [1  1 1]
    [1 4 2] [1 0 3] [0 -1 1]))

(t/deftest update-velocity-test
  (t/is (= (:vel (update-velocity
                  (starting-positions-1 0)
                  (map starting-positions-1 [1 2 3])))
           [3 -1 -1])))

(t/deftest update-position-test
  (t/is (= (:pos (update-position {:pos [-1 0 2] :vel [3 -1 -1]}))
           [2 -1 1])))

(t/deftest tick-test
  (t/is (= (tick starting-positions-1)
           [{:pos [2 -1  1] :vel [3 -1 -1]}
            {:pos [3 -7 -4] :vel [1  3  3]}
            {:pos [1 -7  5] :vel [-3  1 -3]}
            {:pos [2  2  0] :vel [-1 -3  1]}])))

(t/deftest simulate-until-test
  (t/is (= (simulate-until 10 starting-positions-1)
           [{:pos [2  1 -3] :vel [-3 -2  1]}
            {:pos [1 -8  0] :vel [-1  1  3]}
            {:pos [3 -6  1] :vel [3  2 -3]}
            {:pos [2  0  4] :vel [1 -1 -1]}])))

(t/deftest part-1-example
  (t/is (= (part-1 10 starting-positions-1) 179)))

(t/deftest part-2-examples
  (t/is (= (part-2 starting-positions-1) 2772))
  (t/is (= (part-2 starting-positions-2) 4686774924)))

; Real

(def puzzle-input (get-input))

(t/deftest real
  (t/are [part answer] (= (part puzzle-input) answer)
    part-1 7077
    part-2 402951477454512))
