(ns learnhandsontable.views
    (:require [reagent.core :as reagent]
              [reagent.ratom :as ratom]
              [re-frame.core :refer [subscribe dispatch]]))

(defn gen-chart-config-handson
  [tableconfig]
  (let [ret (reagent/atom {
                           :title    {:text "Historic World Population by Region"}
                           :subtitle {:text "Source: Wikipedia.org"}
                           :xAxis    {:categories ["Africa" "America" "Asia" "Europe" "Oceania"]
                                      :title      {:text nil}}
                           :yAxis    {:min       0
                                      :title     {:text  "Population (millions)"
                                                  :align "high"}
                                      :labels    {:overflow "justify"}
                                      :plotLines [{
                                                   :value 0
                                                   :width 1}]}
                           :tooltip  {:valueSuffix " millions"}
                           :legend   {:layout        "vertical"
                                      :align         "left"
                                      :verticalAlign "middle"
                                      :shadow        false}
                           :credits  {:enabled false}})]
    (let [tabledata (:data tableconfig)
          categories (vec (rest (:colHeaders tableconfig)))
          mydata (reduce (fn [outdata input] (conj outdata (assoc {} :name (str (first input)) :data (vec (rest input))))) [] tabledata)]
      (println mydata)
      (swap! ret assoc-in [:xAxis :categories] categories)
      (swap! ret assoc-in [:series] mydata))
    ret))

(defn sampleTable-render []
  [:div {:style {:min-width "310px" :max-width "800px" :margin "0 auto"}}])

(defn sampleTable-did-mount [this]
  (let [[_ tableconfig] (reagent/argv this)
        tableconfigext (assoc-in tableconfig [:afterChange] #(dispatch [:set-tablevalue %]))]
    (do
      (js/Handsontable (reagent/dom-node this) (clj->js tableconfigext)))))

(defn sampleTable [tableconfig]
  (reagent/create-class {:reagent-render      sampleTable-render
                         :component-did-mount sampleTable-did-mount}))

(defn sampleHighchart-render []
  [:div {:style {:min-width "310px" :max-width "800px" :margin "0 auto"}}])

(defn sampleHighchart-did-mount [this]
  (let [[_ tableconfig] (reagent/argv this)
        my-chart-config (gen-chart-config-handson tableconfig)]
    (js/Highcharts.Chart. (reagent/dom-node this) (clj->js @my-chart-config))))

(defn sampleHighchart-did-update [this]
  (let [[_ tableconfig] (reagent/argv this)
        my-chart-config (gen-chart-config-handson tableconfig)]
    (do
      (js/Highcharts.Chart. (reagent/dom-node this) (clj->js @my-chart-config)))))

(defn sampleHighchart [tableconfig]
  (reagent/create-class {:reagent-render      sampleHighchart-render
                         :component-did-mount sampleHighchart-did-mount
                         :component-did-update sampleHighchart-did-update}))


(defn main-panel []
  (let [name (subscribe [:name])
        tableconfig (subscribe [:tableconfig])]
    (fn []
      [:div "Hello from " @name ". Vui qua ta"
       [sampleTable @tableconfig]
       [sampleHighchart @tableconfig]])))
