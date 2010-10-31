(ns abbrev.huffman.test
  (:use [abbrev.huffman] :reload)
  (:use [clojure.test]))

; From http://en.wikipedia.org/wiki/Huffman_coding#Basic_technique, table on the left side.
; With a slightly different frequency, but with the same ordering.
(deftest ensure-huffman-encode-works-with-a-wikipedia-example
  (is (=
        {:a1 '(0)
         :a2 '(1 0)
         :a3 '(1 1 0) 
         :a4 '(1 1 1)}
        (encoding-of [:a1 :a1 :a1 :a1 :a1 
                      :a2 :a2 :a2 
                      :a3 :a3 
                      :a4]))))

; http://michael.dipperstein.com/huffman/index.html
; Probabilities: A = 1, B = 2, C = 4, D = 8, E = 16, F = 32
(deftest ensure-huffman-encode-works-like-described-on-michael-dot-dipperstein-dot-com
  (is (=
        {:f '(0)
         :e '(1 0)
         :d '(1 1 0)
         :c '(1 1 1 0)
         :a '(1 1 1 1 0)
         :b '(1 1 1 1 1)}
        (encoding-of 
          (flatten 
            [(repeat 1 :a) 
             (repeat 2 :b) 
             (repeat 4 :c) 
             (repeat 8 :d) 
             (repeat 16 :e) 
             (repeat 32 :f)])))))
