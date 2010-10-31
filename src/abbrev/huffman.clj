(ns abbrev.huffman)

(defn- sorted-map-by-value 
  "Returns a sorted map, ordered by value"
  ([m]
    (sorted-map-by-value > m))
  ([f m]
    (into
      (sorted-map-by
        (fn [k1 k2]
          (let [val1 (get m k1) 
                val2 (get m k2)]
            (if (f val1 val2) 1 -1)
            )))
      m)))

(defn encoding-of [s]
  "Returns a map of symbols to Huffman encoding."
  (condp = (count s)
    0 {}
    1 {(first s) 0}
    (let [freq-map (sorted-map-by-value < (frequencies s))]
      (loop [idx 1
             out {(ffirst freq-map) '(0)}
             sym '(0)]
        (if (= idx (count freq-map))
          out
          (let [keyh (nth (keys freq-map) idx)
                new-sym (if (= idx (dec (count freq-map)))
                          (cons 1 (drop-last sym))
                          (cons 1 sym))
                new-out (conj out {keyh new-sym})]
            (recur (inc idx) new-out new-sym)))))))
