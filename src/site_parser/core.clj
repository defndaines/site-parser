(ns site-parser.core
  (:require [clojure.java.io :as io]
            [site-parser.core.csv :as parse-csv]
            [site-parser.core.json :as parse-json]
            [site-parser.core.keyword-service :as kws]
            [clojure.data.json :as json] )
  (:gen-class))

(defn ^:private mobile-as-digit [k v]
  (if (= k :mobile)
    (if v 1 0)
    v))

(defn ^:private collect [f path]
  (let [file (.getName (io/file path))]
    ; Using non-standard keywords to conform to output expectations.
    {:collectionId file
     :sites (map #(assoc % :keywords (kws/resolve-keywords %)) (f path))}))

(defmulti ^:private to-site
  "Parse a file containing site definitions into a hash map. Dispatch off extension of filename."
  (fn [filename]
    (subs filename
          (inc (clojure.string/last-index-of filename \.)))))

(defmethod ^:private to-site "csv" [filename] (parse-csv/to-site filename))
(defmethod ^:private to-site "json" [filename] (parse-json/to-site filename))

(defn ^:private dir-list
  "Return the full, canonical path of each file in the provided directory."
  [dir]
  (map #(.getCanonicalPath %) (.listFiles (io/file dir))))

(defn -main
  "Parse files into Site JSON, writing to output file."
  [input-dir output-file & args]
  (if-let [files (dir-list input-dir)]
    (let [sites (map #(collect to-site %) files)]
      (with-open [writer (io/writer output-file)]
        (doseq [site sites]
          (json/write site writer :value-fn mobile-as-digit)
          (.write writer "\n"))))))
