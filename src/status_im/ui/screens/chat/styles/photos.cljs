(ns status-im.ui.screens.chat.styles.photos
  (:require [status-im.ui.components.colors :as colors]))

(def default-size 36)

(defn radius [size] (/ size 2))

(defn photo-container [size]
  {:position      :relative
   :border-radius (radius size)})

(defn photo-border [size]
  {:position      :absolute
   :width         size
   :height        size
   :opacity       0.4
   :border-color  colors/gray
   :border-width  1
   :border-radius (radius size)})

(defn photo [size]
  {:border-radius (radius size)
   :width         size
   :height        size})
