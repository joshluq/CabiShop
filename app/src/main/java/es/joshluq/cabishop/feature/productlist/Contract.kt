package es.joshluq.cabishop.feature.productlist

import es.joshluq.cabishop.domain.model.Product

sealed interface UiIntent {

    object FetchDataIntent : UiIntent

    class AddProductIntent(val product: Product) : UiIntent

}

sealed interface UiState {

    object LoadingState : UiState

    data class SuccessState(val products: List<Product>, val cartCount: Int = 0) : UiState

    object ErrorState : UiState

}