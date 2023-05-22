package es.joshluq.cabishop.domain.repository

import es.joshluq.cabishop.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    fun getProductList(): Flow<Result<List<Product>>>

}