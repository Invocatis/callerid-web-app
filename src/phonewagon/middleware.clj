(ns phonewagon.middleware
  (:require
    [clojure.data.json :as json]))

(defn json-in
  [{:keys [body headers] :as request}]
  (if (= (get headers "content-type") "application/json")
    (update request :body json/read-str :key-fn keyword)
    request))

(defn json-out
  [{:keys [body headers] :as response}]
  (if (= (get headers "content-type") "application/json")
    (update response :body json/write-str)
    response))

(defn slurp-body
  [request]
  (update request :body
    (fn [body]
      (if (instance? java.io.InputStream body)
        (slurp body)
        body))))

(defn wrap
  [app & {:keys [in out]}]
  (->> [out app in]
    (remove nil?)
    (reduce comp)))
