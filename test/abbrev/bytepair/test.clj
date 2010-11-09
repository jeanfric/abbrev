(ns abbrev.bytepair.test
  (:use [abbrev.bytepair] :reload)
  (:use [clojure.test]))

(deftest ensure-find-next-double-works
  (is (=
        '(\a \a)
        (#'abbrev.bytepair/find-next-double "aaabaaabac")))
  (is (=
        '(\Z \a)
        (#'abbrev.bytepair/find-next-double "ZabZabac"))))

; http://www.csse.monash.edu.au/cluster/RJK/Compress/problem.html
(deftest ensure-bytepair-encode-works-like-the-monash-dot-edu-dot-au-example
  (is (=
        {:encoded '(\E \F \F \D),
         :table (list
                  (list \F (list \E \C))   
                  (list \E (list \A \B)))}
        (encode "ABABCABCD"))))
