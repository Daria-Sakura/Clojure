(ns clusterization.core
  (:gen-class))
(use 'clojure.java.io)

(defn clusterization [path destType]
      (println path)
      (println destType)
      (read-file path)
)

(require '[clojure.java.io :as io])
(defn read-file [filePath]

)


(defn -main
  [& args]
  (if (>= (count args) 2)

    (println "Not enough arguments specified"))
 )
