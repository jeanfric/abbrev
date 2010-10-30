(ns abbrev.base64.test
  (:use [abbrev.base64] :reload)
  (:use [clojure.test]))

(def sample-text
  "Lorem ipsum dolor sit volutpat.")

(def base64-encoded-sample-text
  "TG9yZW0gaXBzdW0gZG9sb3Igc2l0IHZvbHV0cGF0Lg==")

(deftest ensure-base64-encode-works-the-same-way-as-on-the-online-motobit-dot-com-encoder
  (is (=
        base64-encoded-sample-text
        (encode sample-text))))

(deftest ensure-base64-decode-works-the-same-way-as-on-the-online-motobit-dot-com-encoder
  (is (=
        sample-text
        (decode base64-encoded-sample-text))))
