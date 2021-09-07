(ns aoc2019.day-8-test
  (:require [clojure.test :as t]
            [aoc2019.day-8 :refer
             [decode-image get-input part-1 part-2 verify-transmission]]))

; Part 1

(t/deftest part-1-example
  (t/is (= (verify-transmission [1 2 3 1 5 6 7 8 9 0 1 2] 3 2) 2)))

; Part 2

(t/deftest part-2-example
  (t/is (= (decode-image [0 2 2 2 1 1 2 2 2 2 1 2 0 0 0 0] 2 2) [[0 1] [1 0]])))

; Real

(def puzzle-input (get-input))

(t/deftest part-1-real
  (t/is (not= (part-1 puzzle-input) 2193))
  (t/is (not= (part-1 puzzle-input) 0))
  (t/is (= (part-1 puzzle-input) 1596)))

(t/deftest part-2-real
  (t/is (= (part-2 puzzle-input)
         ; @         @ @ @     @ @ @       @ @     @ @ @ @  
         ; @         @     @   @     @   @     @   @        
         ; @         @ @ @     @     @   @         @ @ @    
         ; @         @     @   @ @ @     @         @        
         ; @         @     @   @   @     @     @   @        
         ; @ @ @ @   @ @ @     @     @     @ @     @ @ @ @  
           ['(1 0 0 0 0 1 1 1 0 0 1 1 1 0 0 0 1 1 0 0 1 1 1 1 0)
            '(1 0 0 0 0 1 0 0 1 0 1 0 0 1 0 1 0 0 1 0 1 0 0 0 0)
            '(1 0 0 0 0 1 1 1 0 0 1 0 0 1 0 1 0 0 0 0 1 1 1 0 0)
            '(1 0 0 0 0 1 0 0 1 0 1 1 1 0 0 1 0 0 0 0 1 0 0 0 0)
            '(1 0 0 0 0 1 0 0 1 0 1 0 1 0 0 1 0 0 1 0 1 0 0 0 0)
            '(1 1 1 1 0 1 1 1 0 0 1 0 0 1 0 0 1 1 0 0 1 1 1 1 0)])))
