(ns abbrev.base64)

(def standard-base64-alphabet ; cf. http://tools.ietf.org/html/rfc4648#section-4
  [\A \B \C \D \E \F \G \H \I \J \K \L \M \N \O \P \Q \R \S \T \U \V \W \X \Y \Z
   \a \b \c \d \e \f \g \h \i \j \k \l \m \n \o \p \q \r \s \t \u \v \w \x \y \z
   \0 \1 \2 \3 \4 \5 \6 \7 \8 \9 \+ \/
   \=]) ; <-- position 64: the padding character

(defn- indexify [alphabet]
  (loop [output {}
         idx 0]
    (if (= idx 65)
      output
      (let [new-output (assoc output (nth alphabet idx) idx)]
        (recur new-output (inc idx))))))

(defn- encode-raw [s]
  (loop [input-blocks s
         output-vals []]
    (if (= 0 (count input-blocks))
      output-vals
      (let [unpadded-block (take 3 input-blocks)
            block (take 3 (concat unpadded-block (list 0 0)))
            new-input-blocks (drop 3 input-blocks)
            block-value (+
                          (* 256 256 (int (first block))) 
                          (* 256 (int (nth block 1)))
                          (int (nth block 2)))
            first-val (bit-shift-right (bit-and block-value 16515072) 18)
            second-val (bit-shift-right (bit-and block-value 258048) 12)
            third-val (bit-shift-right (bit-and block-value 4032) 6)
            fourth-val (bit-and block-value 63)
            new-output-vals (concat
                              output-vals
                              (take 4
                                (concat
                                  (take 
                                    (+ (count unpadded-block) 1)
                                    (list first-val second-val third-val fourth-val))
                                  (list 64 64 64 64))))]
        (recur new-input-blocks new-output-vals)))))

(defn encode
  "Encodes a string using base64."
  ([string] (encode string standard-base64-alphabet))
  ([string alphabet]
    (let [raw-encoded (encode-raw string)]
      (apply str
        (map #(nth alphabet %) raw-encoded)))))

(defn- decode-raw [s]
  (loop [input s
         output []]
    (if (= 0 (count input))
      output
      (let [new-input (drop 4 input)
            cleaned-block (filter #(not (= 64 %)) (take 4 input))
            decode-block (take 4 (concat cleaned-block (list 0 0 0)))
            number (+
                     (bit-shift-left (nth decode-block 0) 18)
                     (bit-shift-left (nth decode-block 1) 12)
                     (bit-shift-left (nth decode-block 2) 6)
                     (nth decode-block 3))
            charlist (list
                       (bit-shift-right (bit-and number (- (* 256 256 256) 1)) 16)
                       (bit-shift-right (bit-and number (- (* 256 256) 1)) 8)
                       (bit-and number (- 256 1)))
            new-output (concat
                         output
                         (take (- (count cleaned-block) 1) charlist))]
        (recur new-input new-output)))))

(defn decode 
  "Decodes a base64-encoded string."
  ([string] (decode string standard-base64-alphabet))
  ([string alphabet]
    (let [intseq (map #(get (indexify alphabet) %) string)]
      (apply str (map char (decode-raw intseq))))))
