(ns abbrev.rle.test
  (:use [abbrev.rle] :reload)
  (:use [clojure.test]))

(deftest ensure-rle-encode-works-with-a-simple-input
  (is (=
        '((3 \e) (1 \a)) 
        (encode "eeea"))))

(deftest ensure-rle-encode-works-with-an-empty-seq
  (is (=
        '()
        (encode '()))))

(deftest ensure-we-can-decode-our-own-encoding
  (is (=
        '(\e \e \e \a)
        (decode (encode '(\e \e \e \a))))))

(deftest ensure-we-can-decode-our-own-encoding-to-a-string
  (is (=
        "eeea"
        (decode-to-str (encode "eeea")))))
