(ns aoc2019.day-3-test
  (:require [clojure.test :refer :all]
            [aoc2019.day-3 :refer :all]))

(deftest parse-test
  (testing "Basic case"
    (let [input "R8,U5,L5,D3\nU7,R6,D4,L4"]
      (is (= (parse-input input)
             [[[:right 8] [:up 5] [:left 5] [:down 3]]
              [[:up 7] [:right 6] [:down 4] [:left 4]]])))))

(deftest part-1-tests
  (testing
   (for [[answer input] [[6   "R8,U5,L5,D3\nU7,R6,D4,L4"]
                         [159 "R75,D30,R83,U83,L12,D49,R71,U7,L72\nU62,R66,U55,R34,D71,R55,D58,R83"]
                         [135 "R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51\nU98,R91,D20,R16,D67,R40,U7,R15,U6,R7"]]]
     (is (= (solve input) answer)))))
