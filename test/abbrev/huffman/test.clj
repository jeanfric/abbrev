(ns abbrev.huffman.test
  (:use [abbrev.huffman] :reload)
  (:use [clojure.test]))

(deftest ensure-a-simple-list-gets-encoded-correctly
  (is
    (=
      {1 '(0)
       2 '(1 0)
       3 '(1 1 0)
       4 '(1 1 1 0) 
       5 '(1 1 1 1)}
      (encoding-of [1 1 1 1 1 
                    2 2 2 
                    3 3 
                    4 
                    5]))))

(deftest ensure-insert-in-sorted-node-list-works-with-an-empty-list
  (is
    (=
      '({:is_leaf? true, :symbols (1), :weight 1, :left nil, :right nil})
      (#'abbrev.huffman/insert-in-sorted-node-list
        {:is_leaf? true, :symbols '(1), :weight 1, :left nil, :right nil}
        '()))))  

(deftest ensure-insert-in-sorted-node-list-works-with-a-list-of-only-one-node
  (is
    (=
      '(
         {:is_leaf? true, :symbols (1), :weight 1, :left nil, :right nil} 
         {:is_leaf? true, :symbols (2), :weight 2, :left nil, :right nil})
      (#'abbrev.huffman/insert-in-sorted-node-list
        {:is_leaf? true, :symbols '(2), :weight 2, :left nil, :right nil}
        '({:is_leaf? true, :symbols (1), :weight 1, :left nil, :right nil})))))

(deftest ensure-insertion-works-with-a-few-more-nodes
  (is
    (=
      '(
         {:is_leaf? true, :symbols (1), :weight 1, :left nil, :right nil} 
         {:is_leaf? true, :symbols (2), :weight 2, :left nil, :right nil}
         {:is_leaf? true, :symbols (3), :weight 3, :left nil, :right nil}
         {:is_leaf? true, :symbols (4), :weight 4, :left nil, :right nil}
         {:is_leaf? true, :symbols (5), :weight 5, :left nil, :right nil})
      (#'abbrev.huffman/insert-in-sorted-node-list
        {:is_leaf? true, :symbols '(4), :weight 4, :left nil, :right nil}
        '(
           {:is_leaf? true, :symbols (1), :weight 1, :left nil, :right nil}
           {:is_leaf? true, :symbols (2), :weight 2, :left nil, :right nil}
           {:is_leaf? true, :symbols (3), :weight 3, :left nil, :right nil}
           {:is_leaf? true, :symbols (5), :weight 5, :left nil, :right nil})))))
