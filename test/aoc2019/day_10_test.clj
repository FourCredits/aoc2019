(ns aoc2019.day-10-test
  (:require [clojure.test :refer :all]
            [aoc2019.day-10 :refer :all]))

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

(deftest parsing
  (is (= (parse "..\n#.") [[0 1]])))

(deftest line-of-sight
  (is (let [[asteriod & others] (parse (test-fields 5))]
        (= (num-line-of-sight asteriod others) 7))))

(deftest part-1-examples
  (are [answer field] (= (part-1 (parse field)) answer)
    8   (test-fields 0)
    33  (test-fields 1)
    35  (test-fields 2)
    41  (test-fields 3)
    210 (test-fields 4)))

(def puzzle-input (get-input))

(deftest part-1-real
  (is (= (part-1 puzzle-input) 309)))

(deftest part-2-worked-example
  (let [field     (parse (test-fields 6))
        station   (find-best-station field)
        others    (remove #{station} field)
        destroyed (destroyed-order station others)]
    (is (= station [8 3]) (str "station should be [8 3] but is" station))
    (is (= (take 9 destroyed)
           [[8 1] [9 0] [9 1] [10 0] [9 2] [11 1] [12 1] [11 2] [15 1]]))))

(deftest part-2-example
  (is (= (part-2 (parse (test-fields 4))) 802)))

(deftest part-2-real
  (is (= (part-2 puzzle-input) 416)))
