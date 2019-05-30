(ns phonewagon.data
  (:require
    [clojure.data.csv :as csv]
    [clojure.java.io :as io]))

(def db (atom {:caller_id {}}))

(defn matches?
  [params entry]
  (every? #(= (get entry %) (get params %)) (keys params)))

(defn query
  [what params]
  (filterv (partial matches? params) (vals (get @db what))))

(defn insert!
  [what id entry]
  (swap! db update what assoc id entry))

; --- DB Mock ---

(require '[phonewagon.util.phonenumber :as phone])

(def file-path "resources/interview-callerid-data.csv")

(defn read-mock-file
  [path]
  (with-open [reader (io/reader path)]
    (doall
      (csv/read-csv reader))))

(defn initialize
  []
  (let [m (transient {})
        data (read-mock-file file-path)]
    (swap! db
      assoc
      :caller_id
      (persistent!
        (reduce (fn [m [num context id]]
                  (let [num (phone/cleanse num)]
                    (assoc! m
                      (str context "-" num)
                      {:number num
                       :context context
                       :caller_id id})))
                m
                data)))))

(initialize)
