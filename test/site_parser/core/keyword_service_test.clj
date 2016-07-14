(ns site-parser.core.keyword-service-test
  (:require [clojure.test :refer [deftest is]]
            [site-parser.core.keyword-service :as kws]))

(deftest always-same
  (is (= "resolve,keywords,here" (kws/resolve-keywords {}))))
