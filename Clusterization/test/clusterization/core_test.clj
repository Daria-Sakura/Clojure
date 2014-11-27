(ns clusterization.core-test
  (:require [clojure.test :refer :all]
            [clusterization.core :refer :all]))

(deftest euclid-distance-test
  (testing "Wrong result received."
    (is (= (euclid-distance [0 3] [1 5]) 2.23606797749979))))


(deftest hamming-distance-test
  (testing "Wrong result received."
    (is (= (hamming-distance [0 3] [1 5]) 2))))
	

(deftest calculate-potential-test
  (testing "Wrong result received."
    (is (= (calculate-potentials [[0 3] [1 5]] hamming-distance) '((1.4111122905071873 [0 3]) (1.4111122905071873 [1 5])))
	)
  )
)