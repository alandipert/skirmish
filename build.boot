(set-env!
  :dependencies '[[adzerk/boot-cljs          "1.7.228-1"]
                  [adzerk/boot-reload        "0.4.6"]
                  [compojure                 "1.5.0"]
                  [hoplon/boot-hoplon        "0.1.13"]
                  [hoplon/castra             "3.0.0-SNAPSHOT"]
                  [hoplon/hoplon             "6.0.0-alpha13"]
                  [org.clojure/clojure       "1.7.0"]
                  [org.clojure/clojurescript "1.8.40"]
                  [pandeiro/boot-http        "0.7.3"]
                  [ring                      "1.4.0"]
                  [ring/ring-defaults        "0.2.0"]
                  [cljsjs/codemirror         "5.11.0-1"]]
  :resource-paths #{"assets" "src/clj"}
  :source-paths   #{"src/cljs" "src/hl"})

(require
  '[adzerk.boot-cljs      :refer [cljs]]
  '[adzerk.boot-reload    :refer [reload]]
  '[hoplon.boot-hoplon    :refer :all]
  '[pandeiro.boot-http    :refer [serve]])

(deftask dev
  "Build skirmish for local development."
  []
  (comp
    (serve
      :port    8000
      :handler 'skirmish.handler/app
      :reload  true)
    (watch)
    (speak :theme "pillsbury")
    (hoplon)
    (reload)
    (cljs)))

(deftask prod
  "Build skirmish for production deployment."
  []
  (comp
    (hoplon)
    (cljs :optimizations :advanced)
    (prerender)))

(deftask make-war
  "Build a war for deployment"
  []
  (comp (hoplon)
        (cljs :optimizations :advanced)
        (uber :as-jars true)
        (web :serve 'skirmish.handler/app)
        (war)))
