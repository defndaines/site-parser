(ns site-parser.core.csv
  (:require [clojure.java.io :as io]
            [clojure.data.csv :as csv]
            [semantic-csv.core :as sc]
            [clojure.set :as set]))

(defn to-site [path]
  (into []
        (with-open [in-file (io/reader path)]
          (->> (csv/read-csv in-file)
               (remove #(empty? (first %))) ; handle blank line
               sc/mappify
               (map #(set/rename-keys % {(keyword "is mobile") :mobile}))
               (sc/cast-with {:score sc/->double
                              :mobile boolean})
               doall))))
