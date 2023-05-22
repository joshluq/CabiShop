package es.joshluq.cabishop.domain.model

class DiscountVoucher(private val quantity: Int, private val price: Double) : Discount {

    private val freePerProduct = 2

    override fun calculateDiscount() = quantity.div(freePerProduct).times(price)
}