(ns site-parser.core.json
  (:require [clojure.data.json :as json]))

(defn ^:private json-value-fn
  "Custom parsing of values by key."
  [k v]
  (case k
    :score (double v)
    :mobile (= 1 v)
    v))

(defn to-site
  "Convert JSON input into sites."
  [input]
  (json/read-str input :key-fn keyword :value-fn json-value-fn))
