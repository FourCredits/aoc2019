(ns aoc2019.intcode-test
  (:require [clojure.test :refer :all]
            [aoc2019.intcode :refer :all]))

(deftest basic-int-code-tests
  (testing "quit"
    (is (= (intcode [99 98 97]) [99 98 97])
        "Base case: 99, followed by anything, immediately quits"))
  (testing "add"
    (is (= (intcode [1 0 0 0 99]) [2 0 0 0 99])
        "1 + 1 = 2"))
  (testing "multiply"
    (is (= (intcode [2 3 0 3 99]) [2 3 0 6 99])
        "3 * 2 = 6"))
  (testing "data space"
    (is (= (intcode [1 1 1 4 99 5 6 0 99]) [30 1 1 4 2 5 6 0 99])
        "You should be able to write into the space after a 99 command")))

(deftest immediate-mode
  (is (= (intcode [1101 1 2 5 99 0]) [1101 1 2 5 99 3]))
  (are [in out] (= (intcode in) out)
    [1101 1 2 5 99 0] [1101 1 2 5 99 3]
    [1102 2 3 5 99 0] [1102 2 3 5 99 6]))

(defn io-test [program in out]
  (= out (with-out-str
           (with-in-str in
             (intcode program)))))

(deftest reading
  (= (with-in-str "8\n9" (intcode [3 6 3 7 99 0 0]))
     [3 6 3 7 99 8 9]))

(deftest printing
  (= (with-out-str (intcode [4 0 4 4 99]))
     "4\n99\n"))

(deftest equals
  (testing "Basic tests"
    (is (= (intcode [8 0 1 5 99 -1])
       [8 0 1 5 99 0]))
    (is (= (intcode [1108 5 5 0 99]))
        [1 5 5 0 99]))
  (testing "Given examples"
    (let [position-mode  [3 9 8 9 10 9 4 9 99 -1 8]
          immediate-mode [3 3 1108 -1 8 3 4 3 99]]
      (are [program in out] io-test
        position-mode  "8" "1\n"
        position-mode  "9" "0\n"
        immediate-mode "8" "1\n"
        immediate-mode "9" "0\n"))))

(deftest less-than
  (testing "Given examples"
    (let [position-mode  [3 9 7 9 10 9 4 9 99 -1 8]
          immediate-mode [3 3 1107 -1 8 3 4 3 99]]
      (are [program in out] io-test
        position-mode  "7" "1\n"
        position-mode  "9" "0\n"
        immediate-mode "7" "1\n"
        immediate-mode "9" "0\n"))))

; TODO Basic jumping tests

(deftest jumping
  (let [position-mode  [3 12 6 12 15 1 13 14 13 4 13 99 -1 0 1 9]
        immediate-mode [3 3 1105 -1 9 1101 0 0 12 4 12 99 1]]
    (are [program in out] io-test
      position-mode  "0"  "0\n"
      position-mode  "77" "1\n"
      immediate-mode "0"  "0\n"
      immediate-mode "77" "0\n")))

(deftest integration
  (let [program [3 21 1008 21 8 20 1005 20 22 107 8 21 20 1006 20 31 1106 0 36
                 98 0 0 1002 21 125 20 4 20 1105 1 46 104 999 1105 1 46 1101
                 1000 1 20 4 20 1105 1 46 98 99]]
    (are [in out] (io-test program in out)
      "7" "999\n"
      "8" "1000\n"
      "9" "1001\n")))
