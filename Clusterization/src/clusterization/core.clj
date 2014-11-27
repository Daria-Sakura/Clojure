(ns clusterization.core
  (:gen-class)
  (:require [clojure.java.io :as io])
  (:require [clojure.math.numeric-tower :as math])
  (import java.lang.Math))

(def Ra 3)
(def Rb (* 1.5 Ra))
(def EpsHigh 0.5)
(def EpsLow 0.15)
(def alpha (/ 4 (math/expt Ra 2)))             
(def beta  (/ 4 (math/expt Rb 2)))

(defn calculate-potentials [points distance]
  (let [point-potential 
       (fn [x]
         (try(let [len (reduce + 0 (map #(Math/exp (- (* alpha (distance % x)))) points))]
         (list len x))(catch Exception e str ""))
		)]
  (map point-potential points))
)

(defn clusterization [points distance]
    (let [potentials (calculate-potentials points distance)]
		(loop [potentials potentials clusters ()]
			(let 	[	
						P1  (or (first (first clusters)) 0)          
						Pxk (apply max-key first potentials)       
						Pk  (first Pxk)
						Xk  (last Pxk)
						potentials-n (map #(cons (- (first %)
													(* Pk (Math/exp (- (* beta (distance (last %) Xk)))))
												 ) 
												 (rest %)
											)
										  potentials
									  )
					]
				(cond
				 (> Pk (* EpsHigh P1)) (recur potentials-n (conj clusters Pxk)) 
				 (< Pk (* EpsLow P1)) clusters                              
				 :else 
				   (let [Dmin (apply min (map #(distance Xk (last %)) clusters))] 
						(if (>= (+ (/ Dmin Ra) (/ Pk P1)) 1)
							(recur potentials-n (conj clusters Pxk))                  
							(recur (map #(if (= Xk (last %)) (list 0 Xk) %) potentials) clusters)
						)
					)
				)
			)
		)
	)
)

(defn split-string [string]
  (into [] 
        (map read-string
             (drop-last (clojure.string/split string #","))
         )
   )
 )

 (def lines (atom []))
 
(defn read-file [file-path]
       (with-open [rdr (io/reader file-path)]
         (doseq [line (line-seq rdr)]
           (if (not= line "")
             (swap! lines conj (split-string line))
           )
         )
       )
)

(defn hamming-distance [P1 p2]
  ( count
    (filter true? 
            (map not= P1 p2)
    )
  )
)

(defn euclid-distance [P1 p2]
  (->> (map - P1 p2)
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
        (println (clusterization @lines distance))
      )    
      (println "Not enough arguments specified")
   )
 )