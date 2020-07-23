(ns clojure-exercices-ch8.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(def validations {:name ["Name should not be empty" not-empty]
                  :email ["Email should not be empty" not-empty
                          "Email should be valid" #(re-seq #"@" %)]})

(defn get-error-msg
  [to-validate validations]
  (first (reduce
          (fn [error-msgs [msg validation]]
            (if (validation to-validate)
              error-msgs
              (conj error-msgs msg)))
          []
          (partition 2 validations))))

(defn validate
  [to-validate validations]
  (reduce
   (fn [errors [key validations]]
     (let [value (key to-validate)
           error-msgs (get-error-msg value validations)]
       (if (not-empty error-msgs)
         (assoc errors key error-msgs)
         errors)))
   {}
   validations))


(defmacro when-valid
  [to-validate validations & then]
  (let [errors `(validate ~to-validate ~validations)]
    `(if (empty? ~errors)
       (do ~@then)
       (println "Fail"))))

;; (when-valid {:name "Cazuza" :email "cazuza@mail"} validations (println "lol") (println "wtf"))

(defmacro my-or
  ([] true)
  ([x] x)
  ([x & rest]
   `(let [or# ~x]
      (if or# or# (or ~@rest)))))


(or false "lol")
(my-or false "lol")

(def character
  {:name "Smooches McCutes"
   :attributes {:intelligence 10
                :strength 4
                :dexterity 5}})




