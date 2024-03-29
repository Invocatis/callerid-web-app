(defproject phonewagon "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}

  :plugins [[lein-ring "0.12.5"]]

  :dependencies [[org.clojure/clojure "1.10.0"]
                 [compojure "1.6.1"]
                 [org.clojure/data.json "0.2.6"]
                 [org.clojure/data.csv "0.1.4"]]

  :ring {:handler phonewagon.core/handler
         :port 3000
         :nrepl {:start? true
                 :port 9998}}

  :repl-options {:init-ns phonewagon.core})
