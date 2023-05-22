package es.joshluq.cabishop.feature.checkout

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.joshluq.cabishop.common.receiver.receive
import es.joshluq.cabishop.domain.model.Cart
import es.joshluq.cabishop.domain.usecase.UseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class CheckOutViewModel @Inject constructor(
    @Named("getCartUseCase") private val getCartUseCase: UseCase<Unit, Cart>,
    @Named("clearProductsUseCase") private val clearProductsUseCase: UseCase<Unit, Unit>,
) : ViewModel() {

    var uiState by mutableStateOf<UiState>(UiState.SuccessState())
        private set

    fun sendIntent(intent: UiIntent) {
        intent.receive(::onFetchDataIntent)
        intent.receive(::onPayIntent)
    }

    @Suppress("UNUSED_PARAMETER")
    private fun onFetchDataIntent(intent: UiIntent.FetchDataIntent) = viewModelScope.launch {
        getCartUseCase(Unit).first().onSuccess(::onGetProductSuccess)
    }

    private fun onGetProductSuccess(cart: Cart) {
        uiState = UiState.SuccessState(cart)
    }

    @Suppress("UNUSED_PARAMETER")
    private fun onPayIntent(intent: UiIntent.PayIntent) = viewModelScope.launch {
        clearProductsUseCase(Unit).first().onSuccess {
            uiState = UiState.PaymentSuccessState
        }
    }

}