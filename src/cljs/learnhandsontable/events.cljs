(ns learnhandsontable.events
    (:require [re-frame.core :as re-frame]
              [learnhandsontable.db :as mydb]))

(re-frame/reg-event-db
 :initialize-db
 (fn  [_ _]
   mydb/default-db))


(re-frame/reg-event-db
  :set-tablevalue
  (fn [db [_ changeData]]
    (let [rowIdx (first (first changeData))
          colIdx (second (first changeData))
          oldVal (nth (first changeData) 2)
          newVal (nth (first changeData) 3)
          dataTable (get-in db [:tableconfig :data] (:data mydb/init-tableconfig))
          newDataTable (assoc-in dataTable [rowIdx colIdx] (js/parseInt newVal))
          newtablecofig (assoc-in mydb/init-tableconfig [:data] newDataTable)
          newdb (assoc-in db [:tableconfig] newtablecofig)]
      ;(println rowIdx)
      ;(println colIdx)
      ;(println oldVal)
      ;(println newVal)
      ;(println tableconfig)
      ;(println dataTable)
      ;(println newDataTable)
      ;(println newtablecofig)
      ;(println newdb)
      newdb)))
