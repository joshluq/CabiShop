package es.joshluq.cabishop.data.repository

import es.joshluq.cabishop.data.datasource.remote.ApiService
import es.joshluq.cabishop.data.datasource.remote.response.ProductResponse
import es.joshluq.cabishop.domain.model.Product
import es.joshluq.cabishop.domain.repository.ProductRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Named

class ProductDataRepository @Inject constructor(
    @Named("apiService") private val apiService: ApiService
) : ProductRepository {

    override fun getProductList() = flow {
        val response = apiService.getProducts()
        if (response.isSuccessful.not()) {
            throw Exception()
        }
        val result = response.body()?.product?.filterNotNull()
            .orEmpty()
            .map(::mapProduct)
        emit(Result.success(result))
    }.catch { e ->
        emit(Result.failure(e))
    }

    private fun mapProduct(response: ProductResponse) = Product(
        type = response.type ?: throw IllegalArgumentException(),
        name = response.name.orEmpty(),
        price = response.price ?: 0.0
    )
}