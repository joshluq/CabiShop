package es.joshluq.cabishop.data.repository

import es.joshluq.cabishop.data.datasource.local.dao.CartDao
import es.joshluq.cabishop.data.datasource.local.entity.ItemEntity
import es.joshluq.cabishop.domain.model.Cart
import es.joshluq.cabishop.domain.model.DiscountTshirt
import es.joshluq.cabishop.domain.model.DiscountVoucher
import es.joshluq.cabishop.domain.model.Item
import es.joshluq.cabishop.domain.model.Product
import es.joshluq.cabishop.domain.model.ProductType
import es.joshluq.cabishop.domain.repository.CartRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CartDataRepository @Inject constructor(
    private val cartDao: CartDao
) : CartRepository {

    override fun getCart() = cartDao.getAllItems().map { list ->
        Result.success(Cart(list.map(::mapItem)))
    }

    override fun addProduct(product: Product) = flow {
        val list = cartDao.getAllItems().first()
        val item = list.firstOrNull { it.productType == product.type.name }
        if (item == null) {
            cartDao.insertItem(
                ItemEntity(
                    productType = product.type.name,
                    productName = product.name,
                    productPrice = product.price,
                    quantity = 1
                )
            )
        } else {
            cartDao.updateItem(item.copy(quantity = item.quantity + 1))
        }
        emit(Result.success(Unit))
    }

    override fun removeProduct(product: Product) = flow {
        val list = cartDao.getAllItems().first()
        val item = list.firstOrNull { it.productType == product.type.name }
        if (item != null) {
            if (item.quantity > 1) {
                cartDao.updateItem(item.copy(quantity = item.quantity - 1))
            } else {
                cartDao.deleteItem(item)
            }
        }
        emit(Result.success(Unit))
    }

    override fun removeAllProducts() = flow {
        cartDao.clearItems()
        emit(Result.success(Unit))
    }

    private fun mapItem(entity: ItemEntity): Item {
        val type = ProductType.valueOf(entity.productType)
        return Item(
            product = Product(
                type = type,
                name = entity.productName,
                price = entity.productPrice
            ),
            quantity = entity.quantity,
            discount = when (type) {
                ProductType.VOUCHER -> DiscountVoucher(entity.quantity, entity.productPrice)
                ProductType.TSHIRT -> DiscountTshirt(entity.quantity)
                ProductType.MUG -> null
            }
        )
    }


}