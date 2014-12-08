(defproject dummy-image-cljs "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :min-lein-version "2.0.0"
  :license {:name "MIT" }
  :plugins [[lein-cljsbuild "1.0.2"]]
  :dependencies [[org.clojure/clojurescript "0.0-2173"]
                 [org.clojure/clojure "1.6.0"]]
  :cljsbuild
  {:builds [{:source-paths ["src"],
             :compiler {:pretty-print false,
                        :target :nodejs,
                        :output-to "build/app.js",
                        :output-dir "build",
                        :optimizations :simple}}]})
