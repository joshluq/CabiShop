package es.joshluq.cabishop.domain.usecase

import es.joshluq.cabishop.domain.model.Product
import es.joshluq.cabishop.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

class RemoveProductUseCase @Inject constructor(
    private val repository: CartRepository,
) : UseCase<RemoveProductUseCase.Params, Unit> {

    override fun invoke(params: Params): Flow<Result<Unit>> =
        repository.removeProduct(params.product).catch { e ->
            emit(Result.failure(e))
        }

    class Params(val product: Product)
}