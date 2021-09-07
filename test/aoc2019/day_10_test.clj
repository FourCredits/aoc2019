(ns aoc2019.day-10-test
  (:require [clojure.test :as t]
            [aoc2019.day-10 :refer
             [destroyed-order find-best-station get-input num-line-of-sight
              parse part-1 part-2]]))

(def test-fields
  [".#..#
.....
#####
....#
...##"
   "......#.#.
#..#.#....
..#######.
.#.#.###..
.#..#.....
..#....#.#
#..#....#.
.##.#..###
##...#..#.
.#....####"
   "#.#...#.#.
.###....#.
.#....#...
##.#.#.#.#
....#.#.#.
.##..###.#
..#...##..
..##....##
......#...
.####.###."
   ".#..#..###
####.###.#
....###.#.
..###.##.#
##.##.#.#.
....###..#
..#.#..#.#
#..#.#.###
.##...##.#
.....#.#.."
   ".#..##.###...#######
##.############..##.
.#.######.########.#
.###.#######.####.#.
#####.##.#.##.###.##
..#####..#.#########
####################
#.####....###.#.#.##
##.#################
#####.##.###..####..
..######..##.#######
####.##.####...##..#
.#####..#.######.###
##...#.##########...
#.##########.#######
.####.#.###.###.#.##
....##.##.###..#####
.#.#.###########.###
#.#.#.#####.####.###
###.##.####.##.#..##"
   "#.........
...#......
...#..#...
.####....#
..#.#.#...
.....#....
..###.#.##
.......#..
....#...#.
...#..#..#"
   ".#....#####...#..
##...##.#####..##
##...#...#.#####.
..#.....#...###..
..#.#.....#....##"])

(t/deftest parsing
  (t/is (= (parse "..\n#.") [[0 1]])))

(t/deftest line-of-sight
  (t/is (let [[asteriod & others] (parse (test-fields 5))]
          (= (num-line-of-sight asteriod others) 7))))

(t/deftest part-1-examples
  (t/are [answer field] (= (part-1 (parse field)) answer)
    8   (test-fields 0)
    33  (test-fields 1)
    35  (test-fields 2)
    41  (test-fields 3)
    210 (test-fields 4)))

(t/deftest part-2-worked-example
  (let [field     (parse (test-fields 6))
        station   (find-best-station field)
        others    (remove #{station} field)
        destroyed (destroyed-order station others)]
    (t/is (= station [8 3]) (str "station should be [8 3] but t/is" station))
    (t/is (= (take 9 destroyed)
             [[8 1] [9 0] [9 1] [10 0] [9 2] [11 1] [12 1] [11 2] [15 1]]))))

(t/deftest part-2-example
  (t/is (= (part-2 (parse (test-fields 4))) 802)))

; Real

(def puzzle-input (get-input))

(t/deftest real
  (t/are [part answer] (= (part puzzle-input) answer)
    part-1 309
    part-2 416))
