(ns dummy-image-cljs.core
  (:require [cljs.nodejs :as node]))

(def gm (node/require "gm"))
(def express (node/require "express"))
(def morgan (node/require "morgan"))
(def compress (node/require "compression"))
(def body-parser (node/require "body-parser"))
(def method-override (node/require "method-override"))
(def helmet (node/require "helmet"))

(defn -main []
  (let [app  (express)
        port (or (-> node/process.env.PORT) 5000)]
    (doto app
      (.use (morgan "combined"))
      (.use (compress))
      (.use (.urlencoded body-parser (js-obj "extended" false)))
      (.use (.json body-parser))
      (.use (.xssFilter helmet))
      (.use (.nosniff helmet))
      (.use (method-override))
      (.get "/" #(-> (.status %2 200) (.send %2 "ok")))
      (.get "/:width(\\d+)/:height(\\d+)/:id(\\d+)"
            (fn [req res]
              (.setHeader res "Content-Type" "image/png")
              (-> (gm "logo.png")
                  (.options (js-obj "imageMagick" true))
                  (.extent (.. req -params -width) (.. req -params -height))
                  (.colorize (rand-int 255) (rand-int 255) (rand-int 255))
                  (.autoOrient)
                  (.stream #(-> (if %1 (.log js/console %1 %3) (.pipe %2 res)))))))
      (.listen port #(-> (.log js/console (str "Express server started!! on port: " port)))))))

(set! *main-cli-fn* -main)
