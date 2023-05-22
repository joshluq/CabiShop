package es.joshluq.cabishop.domain.model

import junit.framework.TestCase
import org.junit.Test

class DiscountVoucherTest {

    // Subject under test
    private lateinit var discount: DiscountVoucher

    @Test
    fun calculateDiscountSuccess() {

        // Assemble pre conditions
        val expected = 10.0
        discount = DiscountVoucher(4, 5.0)

        // Act
        val result = discount.calculateDiscount()

        // Verify
        TestCase.assertEquals(expected, result)
    }

    @Test
    fun calculateDiscountFailure() {

        // Assemble pre conditions
        val expected = 0.0
        discount = DiscountVoucher(1, 5.0)

        // Act
        val result = discount.calculateDiscount()

        // Verify
        TestCase.assertEquals(expected, result)
    }

}