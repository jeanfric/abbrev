(ns abbrev.bytepair.test
  (:use [abbrev.bytepair] :reload)
  (:use [clojure.test]))


(deftest ensure-bytepair-encode-works-like-the-wikipedia-example
  (is (=
        {:encoded "Wac",
         :table {\Z "aa"
                 \Y "Za"
                 \X "Yb"
                 \W "XX"}}
        (encode "aaabaaabac"))))
