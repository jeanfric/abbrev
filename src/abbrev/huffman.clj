(ns abbrev.huffman)

(defn- huffman-sorted-freq-map [m] 
  "Sorted map by value in decreasing order, and by key in increasing 
   order if the values are equal, e.g. {:f 32, :e 16, :d 8, :c 4, :a 1, :b 1}."
  (into
    (sorted-map-by
      (fn [k1 k2]
        (let [val1 (get m k1) 
              val2 (get m k2)
              val-comp (* -1 (compare val1 val2))]
          (if (= 0 val-comp)
            ; keys must be ordered (non-zero compare) since
            ; a map won't allow two identical keys 
            (compare k1 k2) 
            val-comp))))
    m))

; inspired by http://michael.dipperstein.com/huffman/index.html
(defn encoding-of [s]
  "Returns a map of symbols to Huffman encoding."
  (condp = (count s)
    0 {}
    (let [freq-map (huffman-sorted-freq-map (frequencies s))]
      (loop [idx 1
             out {(ffirst freq-map) '(0)}
             sym '(0)]
        (if (= idx (count freq-map))
          out
          (if (>= idx (- (count freq-map) 2))
            ; For the last two elements, following the canonical Huffman encoding algorithm,
            ; the keys should be treated in alphabetical order, so even if the probability
            ; of the first one is higher, it gets the last Huffman code.
            (let [keyh1 (nth (keys freq-map) idx)
                  keyh2 (nth (keys freq-map) (inc idx))
                  possible-sym1 (reverse (conj (reverse (drop-last sym)) 1 0))
                  possible-sym2 (reverse (conj (reverse (drop-last sym)) 1 1))
                  new-out (conj out (if (= (compare keyh1 keyh2) -1) ; keyh1 < keyh2
                                      {keyh1 possible-sym1 keyh2 possible-sym2}
                                      {keyh1 possible-sym2 keyh2 possible-sym1}))]
              (recur (+ idx 2) new-out nil))
            ; Else we just go down the list as usual (pre-sorted correctly).
            (let [keyh (nth (keys freq-map) idx)
                  new-sym (if (= idx (dec (count freq-map)))
                            (cons 1 (drop-last sym))
                            (cons 1 sym))
                  new-out (conj out {keyh new-sym})]
              (recur (inc idx) new-out new-sym))))))))
