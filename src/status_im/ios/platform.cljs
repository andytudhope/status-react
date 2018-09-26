(ns status-im.ios.platform
  (:require [status-im.react-native.js-dependencies :as rn-dependencies]))

(def fonts
  {:light            {:font-weight "100"}
   :default          {:font-weight "normal"}
   :medium           {:font-weight "normal"}
   :bold             {:font-weight "bold"}

   :toolbar-title    {:font-weight "700"}
   :toolbar-subtitle {:font-weight "normal"}
   :roboto-mono      {:font-weight "normal"}})

;; iPhone X dimensions
(def x-width 375)
(def x-height 812)

(defn iphone-x-dimensions? []
  (let [{:keys [width height]} (-> (.-Dimensions rn-dependencies/react-native)
                                   (.get "window")
                                   (js->clj :keywordize-keys true))]
    (and (= width x-width) (= height x-height))))

(def platform-specific
  {:fonts                        fonts
   :tabs                         {:tab-shadows? false}
   :chats                        {:action-button?       false
                                  :new-chat-in-toolbar? true
                                  :render-separator?    true}
   :contacts                     {:action-button?          false
                                  :new-contact-in-toolbar? true}
   :group-block-shadows?         false
   :discover                     {:uppercase-subtitles? true}
   :status-bar-default-height    (if (iphone-x-dimensions?) 0 20)})
