(defproject project-chem-k "1.0.0"
  :description "Class project to predict a chemical compound's reaction speed constant."
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [weka "3.6.2"] ;; clj-ml uses 3.6.3 internally
                 [com.leadtune/clj-ml "0.2.5"]] ;; GitHub repo is at 0.2.4
  :aot [project-chem-k.core]
  :main project-chem-k.core
  :profiles {:dev {:dependencies [[midje "1.5.0"]]}})
  
