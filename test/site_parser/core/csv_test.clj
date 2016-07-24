(ns site-parser.core.csv-test
  (:require [clojure.test :refer [deftest is]]
            [site-parser.core.csv :as csv]))

(deftest empty-input
  (is (empty? (csv/to-site ""))))

(deftest is-mobile-to-mobile-boolean
  (is (= [{:mobile true :score 0.0} {:mobile false :score 1.0}] (csv/to-site "is mobile,score\ntrue,0\nfalse,1"))))

(deftest score-parsed-to-double
  (is (= [{:mobile false :score 25.0}] (csv/to-site "score\n25"))))

(def one-site-input
  "id,name,is mobile,score\n12000,example.com/csv1,true,454")

(def one-site-output
  [{:id "12000", :mobile true, :name "example.com/csv1", :score 454.0}])

(deftest single-site
  (is (= one-site-output (csv/to-site one-site-input))))

(deftest handles-blank-lines
  (is (= one-site-output (csv/to-site (str one-site-input \newline \newline)))))

(def multiple-sites-input
  (str one-site-input \newline "12001,example.com/csv2,true,128\n12002,example.com/csv3,false,522"))

(def multiple-sites-output
  [{:id "12000", :mobile true, :name "example.com/csv1", :score 454.0}
   {:id "12001", :mobile true, :name "example.com/csv2", :score 128.0}
   {:id "12002", :mobile false, :name "example.com/csv3", :score 522.0}])

(deftest multiple-sites
  (is (= multiple-sites-output (csv/to-site multiple-sites-input))))
