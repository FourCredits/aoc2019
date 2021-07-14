(ns aoc2019.day-3-test
  (:require [clojure.test :refer :all]
            [aoc2019.day-3 :refer :all]))

(def ^:private examples
  ["R8,U5,L5,D3\nU7,R6,D4,L4"
   "R75,D30,R83,U83,L12,D49,R71,U7,L72\nU62,R66,U55,R34,D71,R55,D58,R83"
   "R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51\nU98,R91,D20,R16,D67,R40,U7,R15,U6,R7"])

(deftest parse-line-test
  (testing "Real basics"
    (is (= (parse-line "R5,D6,U7,L8")
           [[\R 5]
            [\D 6]
            [\U 7]
            [\L 8]]))))

(deftest parse-test
  (testing "Basic case"
    (let [input "R8,U5,L5,D3\nU7,R6,D4,L4"]
      (is (= (parse-input input)
             [[[\R 8] [\U 5] [\L 5] [\D 3]]
              [[\U 7] [\R 6] [\D 4] [\L 4]]])))))

(deftest part-1-tests
  (are [answer input] (= (part-1 (parse-input input)) answer)
    6   (examples 0)
    159 (examples 1)
    135 (examples 2)))

(deftest part-1-real
  (is (part-1 (get-input)) 1195))

(deftest part-2-tests
  (are [answer input] (= (part-2 (parse-input input)) answer)
    30  (examples 0)
    610 (examples 1)
    410 (examples 2)))

(deftest part-2-real
  (is (part-2 (get-input)) 91518))
