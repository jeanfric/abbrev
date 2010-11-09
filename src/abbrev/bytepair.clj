(ns abbrev.bytepair
  (require [abbrev.huffman])
  (use [clojure.set]))

; TODO: use the _whole_ range.
(def ascii-letters-set
  (apply sorted-set (map char (concat (range 65 91) (range 97 123)))))

(defn- all-pairs-of [s]
  (concat (partition 2 s) (partition 2 (drop 1 s))))

(defn- find-next-double [s]
  (let [freq (abbrev.huffman/sorted-map-by-value > (frequencies (all-pairs-of s)))]
    (if (>= (second (first freq)) 2)
      (first (first freq))
      nil)))
  
(defn- replace-doubles [s to-replace replacement]
  (loop [in-s s
         out-s '()]
    (if (empty? in-s)
      (reverse out-s)
      (let [new-in-s (if (and (= (first in-s) (first to-replace)) (= (second in-s) (second to-replace)))
                       (drop 2 in-s)
                       (drop 1 in-s))
            new-out-s (if (and (= (first in-s) (first to-replace)) (= (second in-s) (second to-replace)))
                        (cons replacement out-s)
                        (cons (first in-s) out-s))]
        (recur new-in-s new-out-s)))))

(defn encode [s]
  (loop [double-to-replace (find-next-double s)
         in-s (seq s)
         replacements '()
         used-rep-vals (apply sorted-set (seq s))]
    (if (nil? double-to-replace)
      {:encoded in-s
       :table replacements}
      (let [new-used-rep-vals (conj used-rep-vals (first double-to-replace) (second double-to-replace))
            replacement-val (first (difference ascii-letters-set new-used-rep-vals))
            new-in-s (replace-doubles in-s double-to-replace replacement-val)]
        (recur 
          (find-next-double new-in-s) 
          new-in-s 
          (cons (list replacement-val double-to-replace) replacements)
          new-used-rep-vals)))))
