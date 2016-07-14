(ns site-parser.core.json
  (:require [clojure.java.io :as io]
            [clojure.data.json :as json]))

;; Custom parsing of values by key.
(defn ^:private json-value-fn [k v]
  (case k
    :score (double v)
    :mobile (boolean v)
    v))

(defn to-site [path]
  (with-open [in-file (io/reader path)]
    (json/read in-file :key-fn keyword :value-fn json-value-fn)))
