(ns abbrev.huffman)

(defn list-contains? [l value]
  (not (empty? (filter #(= value %) l)))) 

(defn sorted-map-by-value 
  "Returns a sorted map, ordered by value."
  ([m]
    (sorted-map-by-value < m))
  ([f m]
    (into
      (sorted-map-by
        (fn [k1 k2]
          (let [val1 (get m k1) 
                val2 (get m k2)]
            (if (f val1 val2) -1 1))))
      m)))

(defstruct #^{:private true} node 
  :is_leaf?
  :symbols
  :weight
  :left
  :right)

(defn- make-code-tree [l r]
  (struct-map node
    :is_leaf? false
    :symbols (concat (:symbols l) (:symbols r))
    :weight (+ (:weight l) (:weight r))
    :left l
    :right r))

(defn- sorted-leaf-list [sorted-freq-map]
  (map 
    #(struct-map node
       :is_leaf? true
       :symbols (list (first %))
       :weight (second %)
       :left '()
       :right '()) 
    sorted-freq-map))

(defn- insert-in-sorted-node-list [e l]
  (if (empty? l) ; might get here if (rest l) was at the end of the list
    (list e)
    (if (>= (:weight e) (:weight (first l)))
      (cons (first l) (insert-in-sorted-node-list e (rest l)))
      (cons e l))))

(defn- make-huffman-tree [sorted-freq-map]    
  (loop [node-list (sorted-leaf-list sorted-freq-map)]
    (if (<= (count node-list) 1)
      (first node-list)
      (let [first-e (first node-list)
            second-e (second node-list)
            new-node (make-code-tree first-e second-e)
            new-list (insert-in-sorted-node-list new-node (drop 2 node-list))]
        (recur new-list)))))

(comment "The tree will look like this:"
  ({:is_leaf? false, :symbols (3 4 2 1), :weight 7, 
    :left {:is_leaf? true, :symbols (3), :weight 3, :left (), :right ()}, 
    :right {:is_leaf? false, :symbols (4 2 1), :weight 4, 
            :left {:is_leaf? true, :symbols (4), :weight 2, :left (), :right ()}, 
            :right {:is_leaf? false, :symbols (2 1), :weight 2, 
                    :left {:is_leaf? true, :symbols (2), :weight 1, :left (), :right ()}, 
                    :right {:is_leaf? true, :symbols (1), :weight 1, :left (), :right ()}}}}))

(defn- find-symbol-path [s hufftree]
  (loop [sym '()
         tree hufftree]
    (if (or (:is_leaf? tree) (empty? tree))
      (reverse sym)
      (if (list-contains? (:symbols (:left tree)) s)
        (recur (cons 0 sym) (:left tree))
        (recur (cons 1 sym) (:right tree))))))

(defn encoding-of [s]
  (let [sorted-freq-map (sorted-map-by-value (frequencies s))
        symbols (keys sorted-freq-map)
        tree (make-huffman-tree sorted-freq-map)]
    (into {}
      (map 
        (fn [s] {s (find-symbol-path s tree)})
        symbols))))
