package es.joshluq.cabishop.feature.checkout

import es.joshluq.cabishop.domain.model.Cart
import es.joshluq.cabishop.domain.model.Product

sealed interface UiIntent {

    object FetchDataIntent : UiIntent

    object PayIntent : UiIntent

}

sealed interface UiState {

    data class SuccessState(val cart: Cart = Cart(listOf())) : UiState

    object PaymentSuccessState : UiState

}