(ns clusterization.core
  (:gen-class)
  (:require [clojure.java.io :as io]))

(def Ra 3)
(def Rb (* 1.5 Ra))
(def EpsHigh 0.5)
(def EpsLow 0.15)

(defn clusterization [points distance]
      
)

(defn split-string [string]
  (into [] 
        (map read-string
             (drop-last (clojure.string/split string #","))
         )
   )
 )

(defn read-file [file-path]
  (let [lines (atom [])]
    (fn []
       (with-open [rdr (io/reader file-path)]
         (doseq [line (line-seq rdr)]
           (if (not= line "")
             (swap! @lines conj (split-string line))
           )
         )
       )
    )
  )
)

(defn hamming-distance [p1 p2]
  ( count
    (filter true? 
            (map not= p1 p2)
    )
  )
)

(defn euclid-distance [p1 p2]
  (->> (map - p1 p2)
       (map #(* % %))
       (reduce +) 
       (Math/sqrt)
  )
)

(defn -main
  [& args]
  (if (>= (count args) 2) 
      (let [points (read-file (first args))
          distance (if (= (last args) "h") hamming-distance euclid-distance)]
        (clusterization points distance)
      )    
      (println "Not enough arguments specified")
   )
 )