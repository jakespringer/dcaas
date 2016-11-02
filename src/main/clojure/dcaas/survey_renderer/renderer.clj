(ns dcaas.survey-renderer.renderer
  (:require [clojure.xml :as xml]
            [clojure.string :as s]
            [clojure.core.match :refer [match]]))

(defn- escape-str
  [string]
  string)

(defn- escape-url
  [url]
  url)

(defn- whitespaceless
  [string]
  (s/replace string #"\s+" (fn [[_]] " ")))

(defn compile-survey-descriptor
  [uri]
  nil)


(defn- render-h1
  [content]
  (str
    "<h1>"
    (escape-str (apply str content))
    "</h1>"))

(defn- render-h2
  [content]
  (str
    "<h2>"
    (escape-str (apply str content))
    "</h2>"))

(defn- render-h3
  [content]
  (str
    "<h3>"
    (escape-str (apply str content))
    "</h3>"))

(defn- render-h4
  [content]
  (str
    "<h4>"
    (escape-str (apply str content))
    "</h4>"))

(defn- render-h5
  [content]
  (str
    "<h5>"
    (escape-str (apply str content))
    "</h5>"))

(defn- render-h6
  [content]
  (str
    "<h6>"
    (escape-str (apply str content))
    "</h6>"))

(defn- render-b
  [content]
  (str
    "<b>"
    (escape-str (apply str content))
    "</b>"))

(defn- render-i
  [content]
  (str
    "<i>"
    (escape-str (apply str content))
    "</i>"))

(defn- render-u
  [content]
  (str
    "<u>"
    (escape-str (apply str content))
    "</u>"))

(defn- render-a
  [content href]
  (str
    "<a href=\"" (escape-url href) "\">"
    (escape-str (apply str content))
    "</a>"))

(defn- render-code
  [content]
  (str
    "<code>"
    (escape-str (apply str content))
    "</code>"))


(defn- render-br
  [content]
  (str
    "<br>"
    (escape-str (apply str content))
    "</br>"))

(defn- render-hr
  [content]
  (str
    "<hr>"
    (escape-str (apply str content))
    "</hr>"))

(defn- render-ul
  [content]
  (str
    "<ul>"
    (escape-str (apply str content))
    "</ul>"))

(defn- render-ol
  [content]
  (str
    "<ol>"
    (escape-str (apply str content))
    "</ol>"))

(defn- render-p
  [content]
  (str
    "<p>"
    (escape-str (apply str content))
    "</p>"))

(defn- render-sub
  [content]
  (str
    "<sub>"
    (escape-str (apply str content))
    "</sub>"))

(defn- render-sup
  [content]
  (str
    "<sup>"
    (escape-str (apply str content))
    "</sup>"))

(defn- render-short-answer
  [content name]
  (whitespaceless (str
    "<div class=\"row\">
      <div class=\"col-md-3\"></div>
      <div class=\"col-md-6\">
        <label for=\"" name "\">" (escape-str (apply str content)) "</label>
        <input type=\"text\" class=\"form-control\" id=\"" name "\">
      </div>
      <div class=\"col-md-3\"></div>
    </div>")))

(defn- render-long-answer
  [content name]
  (whitespaceless (str
    "<div class=\"row\">
      <div class=\"col-md-3\"></div>
      <div class=\"col-md-6\">
        <label for=\"" name "\">" (escape-str (apply str content)) "</label>
        <textarea class=\"form-control\" rows=\"3\" id=\"" name "\"></textarea>
      </div>
      <div class=\"col-md-3\"></div>
    </div>")))

(defn- render-checkboxes
  [content name]
  (whitespaceless (str
    "<div class=\"row\">
      <div class=\"col-md-3\"></div>
      <div class=\"col-md-6\">
        <label>"
          (escape-str (apply str (map
            #(if (nil? (:content %))
              %
              ""))))
        "</label><br>"
        (render-checkbox-options
          (filter #(not (nil? (:content %)))))
      "</div>
      <div class=\"col-md-3\"></div>
    </div>")))

(defn- render-datepicker
  [content name]
  (whitespaceless (str
    "<div class=\"row\">
      <div class=\"col-md-3\"></div>
      <div class=\"col-md-6\">
        <label>" (escape-str (apply str content)) "</label><br>
        <div><input type=\"text\" id=\"" name "\"></div>
      </div>
      <div class=\"col-md-3\"></div>
    </div>")))

(defn- render-survey-elements
  [elems]
  (apply str
    (map #(match [(:tag %)]
      [:h1] (render-h1 (:content %))
      [:h2] (render-h2 (:content %))
      [:h3] (render-h3 (:content %))
      [:h4] (render-h3 (:content %))
      [:h5] (render-h3 (:content %))
      [:h6] (render-h3 (:content %))
      [:b] (render-b (:content %))
      [:i] (render-i (:content %))
      [:u] (render-u (:content %))
      [:a] (render-a (:content %) (-> % :attrs :href))
      [:code] (render-code (:content %))
      [:br] (render-br (:content %))
      [:hr] (render-hr (:content %))
      [:ul] (render-ul (:content %))
      [:ol] (render-ol (:content %))
      [:p] (render-p (:content %))
      [:sub] (render-sub (:content %))
      [:sup] (render-sup (:content %))
      [:short-answer] (str (render-short-answer (:content %) (-> % :attrs :name)) "<br>")
      [:long-answer] (str (render-long-answer (:content %) (-> % :attrs :name)) "<br>")
      ; [:checkboxes] (str (render-checkboxes (:content %) (-> % :attrs :name)) "<br>")
      ; [:datepicker] (str (render-datepicker (:content %) (-> % :attrs :name)) "<br>")
      ; [:timepicker] (str (render-timepicker (:content %) (-> % :attrs :name)) "<br>")
      ; [:natural-language-date-time] (str (render-natural-language-date-time (:content %) (-> % :attrs :name)) "<br>")
      ; [:multiple-choice] (str (render-multiple-choice (:content %) (-> % :attrs :name)) "<br>")
      ; [:linear-scale] (str (render-linear-scale (:content %) (-> % :attrs :name)) "<br>")
      ; [:multiple-choice-grid] (str (render-multiple-choice-grid (:content %) (-> % :attrs :name)) "<br>")
      ; [:dropdown] (str (render-dropdown (:content %) (-> % :attrs :name)) "<br>")
      :else nil) elems)))

(defn render-survey
  [uri]
  (let [xml (xml/parse uri)]
    (if (= (:tag xml) :survey)
      (whitespaceless (str
        "<html>
        <head>
          <title>Element Examples</title>
          <link href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\" rel=\"stylesheet\">
          <script src=\"https://code.jquery.com/jquery-3.1.1.min.js\" integrity=\"sha256-hVVnYaiADRTO2PzUGmuLJr8BLUSjGIZsDYGmIJLv2b8=\" crossorigin=\"anonymous\"></script>
          <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\"></script>
          <script src=\"https://code.jquery.com/ui/1.12.1/jquery-ui.min.js\" integrity=\"sha256-VazP97ZCwtekAsvgPBSUwPFKdrwD3unUfSGVYrahUqU=\" crossorigin=\"anonymous\"></script>
          <link href=\"./elements.css\" rel=\"stylesheet\">
          <link href=\"https://code.jquery.com/ui/1.12.1/themes/smoothness/jquery-ui.css\" rel=\"stylesheet\">
          <script src=\"./elements.js\"></script>
        </head>
        <body>"
          (render-survey-elements (:content xml))
        "</body></html>"))
      nil)))
