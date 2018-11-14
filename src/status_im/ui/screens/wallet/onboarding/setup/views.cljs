(ns status-im.ui.screens.wallet.onboarding.setup.views
  (:require-macros [status-im.utils.views :as views])
  (:require [clojure.string :as string]
            [re-frame.core :as re-frame]
            [taoensso.timbre :as log]
            [status-im.i18n :as i18n]
            [status-im.react-native.resources :as resources]
            [status-im.ui.components.react :as react]
            [status-im.ui.components.styles :as components.styles]
            [status-im.ui.components.colors :as colors]
            [status-im.ui.screens.wallet.components.views :as wallet.components]
            [status-im.ui.screens.wallet.onboarding.setup.styles :as styles]
            [status-im.ui.components.bottom-buttons.view :as bottom-buttons]
            [status-im.ui.components.button.view :as button]
            [status-im.utils.utils :as utils]
            [status-im.ui.components.toolbar.actions :as actions]
            [status-im.ui.components.status-bar.view :as status-bar]))

(defn signing-word [word first?]
  [react/view (cond-> styles/signing-word
                first? (dissoc
                        :border-left-color
                        :border-left-width))
   [react/text {:style           styles/signing-word-text
                :number-of-lines 1}
    word]])

(defn display-confirmation [modal?]
  (utils/show-confirmation
   {:title   (i18n/label :t/wallet-set-up-confirm-title)
    :content (i18n/label :t/wallet-set-up-confirm-description)
    :cancel-button-text (i18n/label :t/see-it-again)
    :confirm-button-text (i18n/label :t/got-it)
    :on-accept #(re-frame/dispatch [:accounts.ui/wallet-set-up-confirmed modal?])}))

(defn info-circle []
  ;; keeping styles inline here as we are going to
  ;; want to move this somewhere or create an image
  ;; to accomplish this  
  [react/view {:style {:width 20
                       :height 20
                       :border-width 2
                       :border-color colors/white
                       :align-items :center
                       :background-color colors/blue
                       :border-radius 10}}
   [react/text {:style {:color colors/white
                        :font-size 12
                        :text-align :center
                        :font-weight :bold}} "i"]])

(defn info-bubble [text]
  ;; keeping styles inline here as we are going to
  ;; want to move this somewhere for reuse
  [react/view
   {:style {:border-color "rgba(255,255,255,0.6)"
            :border-width 1
            :border-radius 8
            :padding-top 15
            :padding-bottom 15
            :padding-left 20
            :padding-right 20
            :align-items :center}}
   [react/view {:style {:align-items :center
                        :position :absolute
                        :top -10
                        :width 34
                        :background-color colors/blue}}
    (info-circle)]
   [react/text
    {:style {:color "rgba(255,255,255,0.6)"
             :text-align :center
             :font-size 15
             :line-height 22}}
    text]])

(views/defview onboarding-panel [modal?]
  (views/letsubs [{:keys [signing-phrase]} [:account/account]]
    (let [signing-words  (string/split signing-phrase #" ")
          container      (if modal? react/view wallet.components/simple-screen)
          container-opts (if modal? components.styles/flex {:avoid-keyboard? true})]
      [container container-opts
       [wallet.components/toolbar
        {}
        (actions/back-white #(re-frame/dispatch [:wallet-setup-navigate-back]))
        (i18n/label :t/wallet-set-up-title)]
       [react/view {:style styles/border-top-justify}
        [react/view] ;; crappy way to vertically center things
        [react/view {:style {:padding-left 36 :padding-right 36}}
         [react/view {:style styles/signing-phrase}
          (map
           (fn [word first?]
             ^{:key (str "signing-word-" word)}
             [signing-word word first?])
           signing-words
           (cons true (repeat false)))]
         [react/view {:style styles/explanation-container}
          [react/text {:style styles/super-safe-text}
           (i18n/label :t/wallet-set-up-safe-transactions-title)]
          [react/text
           {:style styles/super-safe-explainer-text}
           (i18n/label :t/wallet-set-up-signing-explainer)]
          (info-bubble
           (i18n/label :t/wallet-set-up-signing-explainer-warning))]]
        [react/view {:style styles/bottom-button-container}
         [button/button {:on-press            (partial display-confirmation modal?)
                         :text-style          styles/got-it-button-text
                         :accessibility-label :done-button}
          (i18n/label :t/got-it)
          nil]]]])))

(views/defview screen []
  [onboarding-panel false])

(views/defview modal []
  [react/view styles/modal
   [status-bar/status-bar {:type :modal-wallet}]
   [onboarding-panel true]])
