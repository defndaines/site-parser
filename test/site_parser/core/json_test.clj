(ns site-parser.core.json-test
  (:require [clojure.test :refer [deftest is]]
            [site-parser.core.json :as json]))

(deftest mobile-parsed-to-boolean
  (is (= [{:mobile true} {:mobile false}] (json/to-site "[{\"mobile\":1},{\"mobile\":0}]"))))

(deftest score-parsed-to-double
  (is (= [{:score 25.0}] (json/to-site "[{\"score\":25}]"))))

(def single-site-input
  "[{\"site_id\":\"13000\",\"name\":\"example.com/json1\",\"mobile\":1,\"score\":21}]")

(def single-site-output
  [{:site_id "13000" :name "example.com/json1" :mobile true :score 21.0}])

(deftest single-site
  (is (= single-site-output (json/to-site single-site-input))))

(def multiple-sites-input
  (str
    "[{\"site_id\":\"13000\",\"name\":\"example.com/json1\",\"mobile\":1,\"score\":21},"
    "{\"site_id\":\"13001\",\"name\":\"example.com/json2\",\"mobile\":0,\"score\":97},"
    "{\"site_id\":\"13002\",\"name\":\"example.com/json3\",\"mobile\":0,\"score\":311}]"))

(def multiple-sites-output
  [{:mobile true :name "example.com/json1" :score 21.0 :site_id "13000"}
   {:mobile false :name "example.com/json2" :score 97.0 :site_id "13001"}
   {:mobile false :name "example.com/json3" :score 311.0 :site_id "13002"}])

(deftest multiple-sites
  (is (= multiple-sites-output (json/to-site multiple-sites-input))))
