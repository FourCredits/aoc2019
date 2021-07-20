(ns aoc2019.day-8-test
  (:require [clojure.test :refer :all]
            [aoc2019.day-8 :refer :all]))

(def puzzle-input (get-input))

; Part 1

(deftest part-1-example
  (is (= (verify-transmission [1 2 3 1 5 6 7 8 9 0 1 2] 3 2) 2)))

(deftest part-1-real
  (is (not= (part-1 puzzle-input) 2193))
  (is (not= (part-1 puzzle-input) 0))
  (is (= (part-1 puzzle-input) 1596)))

; Part 2

(deftest part-2-example
  (is (= (decode-image [0 2 2 2 1 1 2 2 2 2 1 2 0 0 0 0] 2 2) [[0 1] [1 0]])))

(deftest part-2-real
  (is (= (part-2 puzzle-input)
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
