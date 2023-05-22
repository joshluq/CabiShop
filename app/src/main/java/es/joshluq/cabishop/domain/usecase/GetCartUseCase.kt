package es.joshluq.cabishop.domain.usecase

import es.joshluq.cabishop.domain.model.Cart
import es.joshluq.cabishop.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

class GetCartUseCase @Inject constructor(
    private val repository: CartRepository,
) : UseCase<Unit, Cart> {

    override fun invoke(params: Unit): Flow<Result<Cart>> = repository.getCart()
        .catch { e ->
            emit(Result.failure(e))
        }
}