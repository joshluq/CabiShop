package es.joshluq.cabishop.feature.shoppingcart

import es.joshluq.cabishop.domain.model.Cart
import es.joshluq.cabishop.domain.model.Product

sealed interface UiIntent {

    object FetchDataIntent : UiIntent

    class AddProductIntent(val product: Product) : UiIntent

    class RemoveProductIntent(val product: Product) : UiIntent

}

sealed interface UiState {

    data class SuccessState(val cart: Cart = Cart(listOf())) : UiState

}