(ns abbrev.rle)

(defn encode [s]
  "Encodes a sequence using run-length encoding."
  (map
    (fn [e]
      (list (count e) (first e)))
    (reverse 
      (reduce
        (fn [l r]
          (if (= (first (first l)) r)
            (cons 
              (cons r (first l)) 
              (rest l))
            (cons
              (list r) 
              l)))
        '()
        s))))

(defn decode [s]
  "Decodes a run-length encoded sequence."
  (flatten
    (map 
      #(take (first %) (repeat (second %)))
      s)))

(defn decode-to-str [s]
  "Decodes a run-length encoded sequence, outputs a string."
  (apply str (decode s)))