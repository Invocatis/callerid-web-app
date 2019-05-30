(ns phonewagon.util)


(defmacro nil->
  "Like clojure.core/-> but short circuits if a nil is returned, returing nil"
  [x & forms]
  (loop [x x, forms forms]
    (if forms
      (let [form (first forms)
            threaded (if (seq? form)
                       (with-meta `(and ~x (~(first form) ~x ~@(next form)) (meta form)))
                       `(and ~x (~form ~x)))]
        (recur threaded (next forms)))
      x)))
