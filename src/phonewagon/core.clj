(ns phonewagon.core
  (:require
    [phonewagon.service :as service]
    [phonewagon.middleware :as middleware :refer [wrap]]
    [ring.middleware.params :as ring-params]))


(def handler
  (-> service/app
      ring-params/wrap-params
      (wrap :in middleware/json-in :out middleware/json-out)
      (wrap :in middleware/slurp-body)))
