package es.joshluq.cabishop.feature.shoppingcart

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.joshluq.cabishop.common.receiver.receive
import es.joshluq.cabishop.domain.model.Cart
import es.joshluq.cabishop.domain.usecase.AddProductUseCase
import es.joshluq.cabishop.domain.usecase.RemoveProductUseCase
import es.joshluq.cabishop.domain.usecase.UseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class ShoppingCartViewModel @Inject constructor(
    @Named("getCartUseCase") private val getCartUseCase: UseCase<Unit, Cart>,
    @Named("addProductUseCase") private val addProductUseCase: UseCase<AddProductUseCase.Params, Unit>,
    @Named("removeProductUseCase") private val removeProductUseCase: UseCase<RemoveProductUseCase.Params, Unit>
) : ViewModel() {

    var uiState by mutableStateOf<UiState>(UiState.SuccessState())
        private set

    fun sendIntent(intent: UiIntent) {
        intent.receive(::onFetchDataIntent)
        intent.receive(::onAddProductIntent)
        intent.receive(::onRemoveProductIntent)
    }

    @Suppress("UNUSED_PARAMETER")
    private fun onFetchDataIntent(intent: UiIntent.FetchDataIntent) = viewModelScope.launch {
        getCartUseCase(Unit).collect { result ->
            result.onSuccess(::onGetProductSuccess)
        }
    }

    private fun onGetProductSuccess(cart: Cart) {
        uiState = UiState.SuccessState(cart)
    }

    private fun onAddProductIntent(intent: UiIntent.AddProductIntent) = viewModelScope.launch {
        val params = AddProductUseCase.Params(intent.product)
        addProductUseCase(params).first()
    }

    private fun onRemoveProductIntent(intent: UiIntent.RemoveProductIntent) =
        viewModelScope.launch {
            val params = RemoveProductUseCase.Params(intent.product)
            removeProductUseCase(params).first()
        }

}