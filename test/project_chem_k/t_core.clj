(ns project-chem-k.t-core
  (:require clojure.string)
  (:use midje.sweet)
  (:use clj-ml.data)
  (:use [project-chem-k.core]))

(def lr-string "\nLinear Regression Model\n\nkOH =\n\n      0.5668 * Alkyl_Carbon +\n      2.0916 * Vinylic_Carbon +\n      0.6991 * Arene +\n     -1.0974 * Ketone +\n     -0.7255 * Ether +\n     -2.8588 * Hydroxyl +\n      1.182  * Hydroxyl_in_Alcohol +\n     -0.3879 * Any_carbon_attached_to_any_halogen +\n      1.7402")
(def lrc-string "\nLinear Regression Model\n\nkOH =\n\n      0.5163 * Alkyl_Carbon +\n      2.1896 * Vinylic_Carbon +\n      0.6673 * Arene +\n     -0.9797 * Ketone +\n     -0.7381 * Ether +\n     -2.8335 * Hydroxyl +\n      1.1946 * Hydroxyl_in_Alcohol +\n     -5.5781 * Enol +\n      6.0982 * Phenol +\n     -2.7891 * Enol_or_Phenol +\n     -0.3912 * Any_carbon_attached_to_any_halogen +\n      1.8158")
(def m5-string "M5 pruned model tree:\n(using smoothed linear models)\n\nArene <= 2 : LM1 (183/34.794%)\nArene >  2 : LM2 (61/111.35%)\n\nLM num: 1\nkOH = \n\t0.5046 * Alkyl_Carbon \n\t+ 2.0306 * Vinylic_Carbon \n\t+ 0.053 * Arene \n\t- 0.2223 * Carbonyl_with_Carbon \n\t+ 0.9706 * Carbonyl_with_Nitrogen \n\t- 0.0831 * Ketone \n\t- 0.3634 * Ether \n\t- 1.9388 * Hydroxyl \n\t+ 0.8422 * Hydroxyl_in_Alcohol \n\t- 0.2394 * Any_carbon_attached_to_any_halogen \n\t+ 0.9447\n\nLM num: 2\nkOH = \n\t0.7003 * Alkyl_Carbon \n\t+ 0.4128 * Vinylic_Carbon \n\t- 0.3592 * Arene \n\t- 0.2166 * Ketone \n\t- 0.1432 * Ether \n\t- 2.0791 * Hydroxyl \n\t+ 0.2333 * Hydroxyl_in_Alcohol \n\t+ 1.4669 * Enol \n\t- 1.1539 * Any_carbon_attached_to_any_halogen \n\t+ 10.5873\n\nNumber of Rules : 2")

(let [ds  (setup-ds)
      fds (remove-compound-name ds)
      lr  (linreg fds)
      lrc (linreg-colinear fds)
      m5  (m5p fds)]
  (facts "about the dataset"
         (fact "the first compound is called 2-Nitropropane"
               (clojure.string/trim (:Compound (instance-to-map (first (dataset-seq ds))))) => "2-Nitropropane")
         (fact "and its k-value is 0.08"
               (:kOH (instance-to-map (first (dataset-seq ds)))) => 0.08
               (:kOH (instance-to-map (first (dataset-seq fds)))) => 0.08))
  (facts "about the classifiers"
         (fact "the regular linear regression model should be:"
               (.toString lr) => lr-string)
         (fact "the keep-colinear linear regression model should be:"
               (.toString lrc) => lrc-string)
         (fact "the M5-model should be:"
               (.toString m5) => m5-string)))

