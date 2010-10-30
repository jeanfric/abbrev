(ns abbrev.packbits.test
  (:use [abbrev.packbits] :reload)
  (:use [clojure.test]))

(deftest ensure-packbits-encode-works-with-a-simple-input
  (is (=
        '(0 1 3 2 4 3)
        (encode [1 2 2 3 3 3]))))

(deftest ensure-packbits-encode-works-with-an-empty-seq
  (is (=
        '()
        (encode '()))))
