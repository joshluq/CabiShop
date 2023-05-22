package es.joshluq.cabishop.domain.repository

import es.joshluq.cabishop.domain.model.Cart
import es.joshluq.cabishop.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface CartRepository {

    fun getCart(): Flow<Result<Cart>>

    fun addProduct(product: Product): Flow<Result<Unit>>

    fun removeProduct(product: Product): Flow<Result<Unit>>

    fun removeAllProducts(): Flow<Result<Unit>>
}