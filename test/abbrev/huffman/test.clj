(ns abbrev.huffman.test
  (:use [abbrev.huffman] :reload)
  (:use [clojure.test]))

; From http://en.wikipedia.org/wiki/Huffman_coding#Basic_technique, table on the left side.
; With a slightly different frequency, but with the same ordering.
(deftest ensure-huffman-encode-works-with-a-wikipedia-example
  (is (=
        {:a1 '(0),
         :a2 '(1 0),
         :a3 '(1 1 0), 
         :a4 '(1 1 1)}
        (encoding-of [:a1 :a1 :a1 :a1 :a1 :a2 :a2 :a2 :a3 :a3 :a4]))))
