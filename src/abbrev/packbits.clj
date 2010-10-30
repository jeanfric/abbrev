(ns abbrev.packbits
  (:require [abbrev.rle :as rle]))

(defn encode [s]
  "Encodes a sequence using a packbits-like algorithm."
  (let [cs (rle/encode s)]
    (reverse
      (reduce 
        (fn [l r]
          (if (= 1 (first r))
            (cons (first (rest r))
              (cons 0 l))
            (cons (first (rest r))
              (cons (+ 1 (first r)) l))))
        '()
        cs))))
