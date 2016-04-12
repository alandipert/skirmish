(ns skirmish.api
  (:require
    [cljs.closure :as closure]
    [cljs.env     :as env]
    [castra.core :refer [defrpc]]
    [hoplon.boot-hoplon.refer :as r]))

(def cljs-opts
  {:optimizations :whitespace :pretty-print false})

(def cljs-env
  (env/default-compiler-env cljs-opts))

(def ns-decl
  (let [nses   ['hoplon.core 'javelin.core]
        vars   (map r/make-require nses)
        macros (map r/make-require-macros nses)]
    (list 'ns 'hoplon.app-pages._index_DOT_html
          (list* :require vars)
          (list* :require-macros macros))))

(closure/build [ns-decl] cljs-opts cljs-env)

(def initial-env-value @cljs-env)

(defrpc get-state [expr]
  "aloha mundo")


(comment
  
  
  (clojure.pprint/pprint page-require)

  (closure/build [ns-decl '(prn :hi)] cljs-opts cljs-)
  
  )

