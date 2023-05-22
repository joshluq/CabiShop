package es.joshluq.cabishop.domain.usecase

import es.joshluq.cabishop.domain.model.Product
import es.joshluq.cabishop.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val repository: ProductRepository,
) : UseCase<Unit, List<@JvmSuppressWildcards Product>> {

    override fun invoke(params: Unit): Flow<Result<List<Product>>> = repository.getProductList()
        .catch { e ->
            emit(Result.failure(e))
        }
}