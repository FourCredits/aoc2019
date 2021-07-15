(ns aoc2019.day-5
  (:gen-class)
  (:require [aoc2019.intcode :refer :all]
            [clojure.string :as str]))

(defn part-1
  [program]
  (let [output (with-out-str
                 (with-in-str "1"
                   (intcode program)))]
    (Integer/valueOf (last (str/split-lines output)))))

(defn part-2
  [program]
  (let [output (with-out-str
                 (with-in-str "5"
                   (intcode program)))]
    (Integer/valueOf (str/trim output))))

(def filepath "resources/day5.txt")

(defn -main
  [& args]
  (let [input-program (get-input filepath)]
    (println (part-1 input-program))
    (println (part-2 input-program))))

