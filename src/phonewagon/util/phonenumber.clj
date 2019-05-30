(ns phonewagon.util.phonenumber
  (:require
    [clojure.string :as string]
    [phonewagon.util :refer [nil->]]))

(defn e164?
  [number]
  (re-matches #"^\+?[1-9]\d{9,14}$" number))

(defn only-numbers
  [number]
  (string/replace number #"[^0-9]" ""))

(defn assume-usa?
  [number]
  (if (-> number count (= 10))
    (str "1" number)
    number))

(defn cleanse
  [number]
  (nil-> number only-numbers e164? assume-usa?))
