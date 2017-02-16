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
          categories (vec (rest (get tabledata 0)))
          data1 (assoc {} :name (str (first (get tabledata 1))) :data (vec (rest (get tabledata 1))))
          data2 (assoc {} :name (str (first (get tabledata 2))) :data (vec (rest (get tabledata 2))))
          data3 (assoc {} :name (str (first (get tabledata 3))) :data (vec (rest (get tabledata 3))))
          mydata (conj [] data1 data2 data3)]
      ;(println data1)
      ;(println data2)
      (swap! ret assoc-in [:xAxis :categories] categories)
      (swap! ret assoc-in [:series] mydata))
    ret))

(defn sampleTable-render []
  [:div {:style {:min-width "310px" :max-width "800px" :margin "0 auto"}}])

(defn sampleTable-did-mount [this]
  (let [[_ tableconfig] (reagent/argv this)
        tableconfigext (assoc-in tableconfig [:afterChange] #(dispatch [:set-tablevalue %]))]
    (do
      ;(println tableconfig)
      ;(.log js/console "table did mount!!!\n")
      (js/Handsontable (reagent/dom-node this) (clj->js tableconfigext)))))

(defn sampleTable [tableconfig]
  (reagent/create-class {:reagent-render      sampleTable-render
                         :component-did-mount sampleTable-did-mount}))


(defn main-panel []
  (let [name (subscribe [:name])
        tableconfig (subscribe [:tableconfig])]
    (fn []
      [:div "Hello from " @name ". Vui qua ta"
       [sampleTable @tableconfig]])))
