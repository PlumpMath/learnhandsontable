(ns learnhandsontable.db)

(def init-tableconfig {
                       :data        [
                                     ["" "Kia" "Nissan" "Toyota" "Honda"]
                                     ["2008" 0 0 0 0]
                                     ["2009" 0 0 0 0]
                                     ["2010" 0 0 0 0]]
                       :rowHeaders  false
                       :colHeaders  false
                       :contextMenu false})

(def default-db
  {:name "re-frame"
   :tableconfig nil
   })
