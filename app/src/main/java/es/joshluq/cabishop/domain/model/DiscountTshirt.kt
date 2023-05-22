package es.joshluq.cabishop.domain.model

class DiscountTshirt(private val quantity: Int) : Discount {

    private val discountPerProduct = 1.0

    override fun calculateDiscount() = when {
        quantity > 2 -> (discountPerProduct * quantity.toDouble())
        else -> 0.0
    }

}