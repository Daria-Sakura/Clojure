(ns clusterization.core
  (:gen-class))
(use 'clojure.java.io)

(def Ra 3)
(def Rb (* 1.5 Ra))
(def EpsHigh 0.5)
(def EpsLow 0.15)

(defn clusterization [path dest-type]
      (println path)
      (println dest-type)
      (read-file path)
)

(defn read-file [file-path]

)

(defn hamming-distance [p1 p2]

)

(defn euclid-distance [p1 p2]

)

(defn -main
  [& args]
  (if (>= (count args) 2)
      (let [points (read-file (first args))
          distance (if (= (last args) "h") hamming-distance euclid-distance)]
      (clusterization points distance))
    (println "Not enough arguments specified"))
 )
