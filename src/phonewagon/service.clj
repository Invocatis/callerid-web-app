(ns phonewagon.service
  (:require
    [phonewagon.data :as db]
    [phonewagon.util.phonenumber :as phone]
    [compojure.core :refer :all]
    [compojure.route :as route]
    [clojure.data.json :as json]))

(defn get|query
  [number]
  (if-let [number (phone/cleanse number)]
    (let [results (db/query :caller_id {:number number})]
      {:status 200
       :headers {"content-type" "application/json"}
       :body {:results results}})
    {:status 400
     :headers {"content-type" "text/html"}
     :body "invalid number format"}))

(defn post|number
  [entry]
  (let [entry (update entry :number phone/cleanse)]
    (if (get entry :number)
       (do (db/insert! :caller_id
             (str (:context entry) "-" (:number entry))
             entry)
           {:status 200
            :headers {"content-type" "application/json"}
            :body entry})
       {:status 400
        :headers {"content-type" "text/html"}
        :body "invalid number format"})))

(defroutes app
  (GET "/query" [number]
    (get|query number))
  (POST "/number" {:keys [body]}
    (post|number body))
  (route/not-found {:status 404}))
