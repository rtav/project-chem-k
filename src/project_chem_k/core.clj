(ns project-chem-k.core
  (:use clj-ml.io)
  (:use clj-ml.data)
  (:use clj-ml.filters)
  (:use clj-ml.classifiers)
  (:gen-class))

(defn setup-ds []
  (let [arff-file "file:resources/results.arff"
        ds (load-instances :arff arff-file)]
    (dataset-set-class ds 1)
    ds))

(defn remove-compound-name [ds]
  (let [rcn-filter (make-filter :remove-attributes {:dataset-format ds :attributes [0]})]
    (filter-apply rcn-filter ds)))

(defn linreg [ds]
  (classifier-train (make-classifier :regression :linear) ds))

(defn linreg-colinear [ds]
  (classifier-train (make-classifier :regression :linear {:keep-colinear true}) ds))

(defn m5p [ds]
  (classifier-train (make-classifier :decision-tree :m5p) ds))

(defn evaluate [classifier training-data test-data]
  (let [evaluation (new weka.classifiers.Evaluation training-data)]
    (.evaluateModel evaluation classifier test-data (into-array []))
    (println "==== evaluating\n" classifier)
    (println (.toSummaryString evaluation))))

(defn idem-evaluate [classifier training-data]
  (evaluate classifier training-data training-data))

(defn cross-evaluate [classifier folds training-data]
  (let [evaluation (new weka.classifiers.Evaluation training-data)]
    (.crossValidateModel evaluation classifier training-data 
                         folds (new java.util.Random (.getTime (new java.util.Date))) (into-array []))
    (println "====" folds "fold cross-evaluating\n" classifier)
    (println (.toSummaryString evaluation))))

(defn -main [& args]
  (def ds (setup-ds))                 ;(instance-to-map (first (dataset-seq ds)))
  (def fds (remove-compound-name ds)) ;(instance-to-map (first (dataset-seq fds)))

  (def lr  (linreg fds))
  (def lrc (linreg-colinear fds))
  (def m5  (m5p fds))

  ;; Evaluating the classifiers with the training data should indicate overfitting.
  ;; Unfortunately, the builtin `classifier-evaluate` throws a NullPointerException
  ;; because it asks for the dataset's class labels (there are none), so I evaluate manually.
  (idem-evaluate lr  fds)
  (idem-evaluate lrc fds)
  (idem-evaluate m5  fds)

  ;; Cross-validation should prove our intuition, barring that error.
  (cross-evaluate lr  10 fds)
  (cross-evaluate lrc 10 fds)
  (cross-evaluate m5  10 fds)

  ;; Interestingly, using 25 folds improves the precision for linear regression but
  ;; is bad for the M5 decision tree.
  (cross-evaluate lr  25 fds)
  (cross-evaluate lrc 25 fds)
  (cross-evaluate m5  25 fds)
  )


