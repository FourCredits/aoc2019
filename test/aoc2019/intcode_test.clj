(ns aoc2019.intcode-test
  (:require [clojure.test :as t]
            [aoc2019.intcode :refer [run-io run-pure step]]))

(t/deftest step-tests
  (t/testing "halt"
    (t/is (:halted? (step {:memory [99 98 97] :pc 0}))
          "99, followed by anything, immediately halts.")
    (t/is (nil? (:halted? (step {:memory [1101 1 1 5 99 0] :pc 0})))
          "A different instruction shouldn't cause the program to halt"))
  (t/testing "add"
    (t/is (= (step {:memory [1 0 0 0 99] :pc 0})
             {:memory [2 0 0 0 99] :pc 4})
          "1 + 1 = 2"))
  (t/testing "multiply"
    (t/is (= (step {:memory [2 3 0 3 99] :pc 0})
             {:memory [2 3 0 6 99] :pc 4})
          "3 * 2 = 6")))

(t/deftest immediate-step-tests
  (t/are [in out] (= (step in) out)
    {:memory [1101 1 2 5 99 0] :pc 0} {:memory [1101 1 2 5 99 3] :pc 4}
    {:memory [1102 2 3 5 99 0] :pc 0} {:memory [1102 2 3 5 99 6] :pc 4}))

(t/deftest reading-io
  (t/is (= (vals (:memory (with-in-str "8\n9" (run-io [3 5 3 6 99 0 0]))))
           [3 5 3 6 99 8 9])))

(t/deftest reading-pure
  (t/is (= (vals (:memory (run-pure [3 5 3 6 99 0 0] [8 9])))
           [3 5 3 6 99 8 9])))

(t/deftest printing-io
  (t/is (= (with-out-str (run-io [4 0 4 4 99]))
           "4\n99\n")))

(t/deftest printing-pure
  (t/is (= (:output (run-pure [4 0 4 4 99])) [4 99])))

(t/deftest equals
  (t/testing "Basic tests"
    (t/is (= (vals (:memory (run-pure [8 0 1 5 99 -1])))
             [8 0 1 5 99 0]))
    (t/is (= (vals (:memory (run-pure [1108 5 5 0 99])))
             [1 5 5 0 99])))
  (t/testing "Given examples"
    (t/are [program in out] (= (:output (run-pure program in))
                               out)
      [3 9 8 9 10 9 4 9 99 -1 8] [8] [1]
      [3 9 8 9 10 9 4 9 99 -1 8] [9] [0]
      [3 3 1108 -1 8 3 4 3 99]   [8] [1]
      [3 3 1108 -1 8 3 4 3 99]   [9] [0])))

(t/deftest less-than
  (t/testing "Given examples"
    (let [position-mode  [3 9 7 9 10 9 4 9 99 -1 8]
          immediate-mode [3 3 1107 -1 8 3 4 3 99]]
      (t/are [program in out] (= (:output (run-pure program in)) out)
        position-mode  [7] [1]
        position-mode  [9] [0]
        immediate-mode [7] [1]
        immediate-mode [9] [0]))))

; TODO Basic jumping tests

(t/deftest jumping
  (t/testing "Given tests"
    (let [position-mode  [3 12 6 12 15 1 13 14 13 4 13 99 -1 0 1 9]
          immediate-mode [3 3 1105 -1 9 1101 0 0 12 4 12 99 1]]
      (t/are [program in out] (= (:output (run-pure program in)) out)
        position-mode  [0]  [0]
        position-mode  [77] [1]
        immediate-mode [0]  [0]
        immediate-mode [77] [1]))))

(t/deftest integration
  (let [program [3 21 1008 21 8 20 1005 20 22 107 8 21 20 1006 20 31 1106 0 36
                 98 0 0 1002 21 125 20 4 20 1105 1 46 104 999 1105 1 46 1101
                 1000 1 20 4 20 1105 1 46 98 99]]
    (t/are [in out] (= (:output (run-pure program in)) out)
      [7] [999]
      [8] [1000]
      [9] [1001])))

(t/deftest relative-mode
  (t/testing "Setting relative base"
    (t/is (= (:relative-base (run-pure [99 98 97])) 0)
          "Relative base starts at 0")
    (t/is (= (:relative-base (run-pure [109 19 109 -2 99])) 17)
          "The relative base instruction adds (or subtracts) from the current
        value"))
  (t/testing "Using relative base instructions"
    (t/is (= (:output (run-pure [2201 2 3 7 4 7 99 0])) [10])
          "Using relative instructions")
    (t/is (= (:output (run-pure [109 2 204 2 99])) [99])
          "Setting relative base, then using it")))

(t/deftest large-numbers
  (t/testing "handling arbitrarily large numbers"
    (t/is (= (:output (run-pure [1102 34915192 34915192 7 4 7 99 0]))
             [1219070632396864])
          "Adding two large numbers")
    (t/is (= (:output (run-pure [104 1125899906842624 99]))
             [1125899906842624])))
  (t/testing "addressing large numbers"
    (t/is (= (:output (run-pure [4 34567 99])) [0])
          "You can read addresses much larger than initial program, and t/is
           by default 0")
    (t/is (= (:output (run-pure [1102 4 5 34567 4 34567 99])) [20])
          "Large addresses outside of initial program can be written to and
           read from just like any other part of memory")))

(t/deftest quine
  (t/is (let [program [109 1 204 -1 1001 100 1 100 1008 100 16 101 1006 101 0 99]]
          (= (:output (run-pure program))
             program))))
