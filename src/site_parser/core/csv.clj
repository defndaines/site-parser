(ns site-parser.core.csv
  (:require [clojure.data.csv :as csv]
            [semantic-csv.core :as sc]
            [clojure.set :as set]))

(defn ^:private ->bool
  "Convert boolean representations to actual boolean value."
  [b]
  (contains? #{"true" "t" "yes" "1"} b))

(defn to-site
  "Convert CSV input into sites."
  [input]
  (->> (csv/read-csv input)
       (remove #(empty? (first %))) ; Handles blank lines.
       sc/mappify
       (map #(set/rename-keys % {(keyword "is mobile") :mobile}))
       (sc/cast-with {:score sc/->double
                      :mobile ->bool})
       doall)) ; Cannot be lazy when reading from file.
