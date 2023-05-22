package es.joshluq.cabishop.domain.usecase

import es.joshluq.cabishop.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

class ClearProductsUseCase @Inject constructor(
    private val repository: CartRepository,
) : UseCase<Unit, Unit> {

    override fun invoke(params: Unit): Flow<Result<Unit>> = repository.removeAllProducts()
        .catch { e ->
            emit(Result.failure(e))
        }

}