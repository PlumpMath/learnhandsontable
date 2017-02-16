(ns learnhandsontable.core)


(def m {:a 3})
(def mystring "toi ten la ten ten ten")

(reduce + [1 2 3 4 5])
(reduce conj #{} [:a :b :c])

(m :b 0)

(def a [0 1 2 3 4])
(defn assistF [a]
  (let [tmpmap {}]
    (-> tmpmap
        (assoc :i (str a))
        (assoc :h 2)
        (assoc :w 2)
        (assoc :x (* 2 a))
        (assoc :y 0))))

(defn getOutput [a]
  (let [la (count a)]
    (mapv assistF a)))


(getOutput a)


(reduce #(assoc %1 %2 (inc (%1 %2 0)))
        {}
        (re-seq #"\w+" mystring))

(re-seq #"\w+" mystring)

(defn supportF
 [primes number]
 (if (some zero? (map (partial mod number) primes))
   primes
   (conj primes number)))

(reduce
 (fn [primes number]
   (if (some zero? (map (partial mod number) primes))
     primes 
     (conj primes number)))
 [2]
 (take 1000 (iterate inc 3)))

(reduce
 supportF
 [2]
 (take 1000 (iterate inc 3)))


(def init-tableconfig {
                       :colHeaders ["" "Kia" "Nissian" "Toyota" "Honda"]
                       :data        [
                                     ;;["" "Kia" "Nissan" "Toyota" "Honda"]
                                     ["2008" 0 0 0 0]
                                     ["2009" 0 0 0 0]
                                     ["2010" 0 0 0 0]]
                       :rowHeaders  false
                       :contextMenu true})

(def changeDatas [[1 2 0 5] [1 3 0 7]])
(defn updatetable [tableconfig changeData]
  (let [rowIdx (first changeData)
        colIdx (second changeData)
        oldVal (nth changeData 2)
        newVal (nth changeData 3)
        dataTable (get-in tableconfig [:data] (:data init-tableconfig))
        newDataTable (assoc-in dataTable [rowIdx colIdx] newVal)
        tableconfig (assoc-in tableconfig [:data] newDataTable)]
    tableconfig))

(defn testF [changeDatas]
  (let [tableconfig init-tableconfig
        newtableconfig (reduce updatetable tableconfig changeDatas)]
    (println tableconfig)
    newtableconfig))

(testF changeDatas)

(def tmptableconfig init-tableconfig)
(reduce updatetable tmptableconfig changeDatas)














