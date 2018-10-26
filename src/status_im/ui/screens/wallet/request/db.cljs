(ns status-im.ui.screens.wallet.request.db
  (:require [cljs.spec.alpha :as spec]
            [status-im.utils.money :as money]))

(spec/def ::amount (spec/nilable money/valid?))
(spec/def ::amount-error (spec/nilable string?))
(spec/def ::amount-text (spec/nilable string?))
(spec/def ::symbol (spec/nilable keyword?))

(spec/def :wallet/request-transaction (spec/keys :opt-un [::amount ::amount-error ::amount-text ::symbol]))
