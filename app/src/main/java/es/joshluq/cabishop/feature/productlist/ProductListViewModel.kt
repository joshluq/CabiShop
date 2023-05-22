package es.joshluq.cabishop.feature.productlist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.joshluq.cabishop.common.receiver.receive
import es.joshluq.cabishop.domain.model.Cart
import es.joshluq.cabishop.domain.model.Product
import es.joshluq.cabishop.domain.usecase.AddProductUseCase
import es.joshluq.cabishop.domain.usecase.UseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class ProductListViewModel @Inject constructor(
    @Named("getProductsUseCase") private val getProductsUseCase: UseCase<Unit, List<Product>>,
    @Named("getCartUseCase") private val getCartUseCase: UseCase<Unit, Cart>,
    @Named("addProductUseCase") private val addProductUseCase: UseCase<AddProductUseCase.Params, Unit>
) : ViewModel() {

    var uiState by mutableStateOf<UiState>(UiState.LoadingState)
        private set

    private var currentProducts = listOf<Product>()

    fun sendIntent(intent: UiIntent) {
        intent.receive(::onFetchDataIntent)
        intent.receive(::onAddProductIntent)
    }

    @Suppress("UNUSED_PARAMETER")
    private fun onFetchDataIntent(intent: UiIntent.FetchDataIntent) = viewModelScope.launch {
        if (currentProducts.isNotEmpty()) return@launch
        uiState = UiState.LoadingState
        getProductsUseCase(Unit).collect { result ->
            result.onSuccess(::onGetProductSuccess).onFailure(::onGetProductFailure)
        }

    }

    private fun getCart() = viewModelScope.launch {
        getCartUseCase(Unit).collect { cart ->
            val quantity = cart.getOrNull()?.items.orEmpty().sumOf { it.quantity }
            uiState = UiState.SuccessState(
                currentProducts,
                quantity
            )
        }
    }


    private fun onGetProductSuccess(products: List<Product>) {
        currentProducts = products
        getCart()
    }

    @Suppress("UNUSED_PARAMETER")
    private fun onGetProductFailure(throwable: Throwable) {
        uiState = UiState.ErrorState
    }

    private fun onAddProductIntent(intent: UiIntent.AddProductIntent) = viewModelScope.launch {
        val params = AddProductUseCase.Params(intent.product)
        addProductUseCase(params).first()
    }


}