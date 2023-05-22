package es.joshluq.cabishop.domain.model

import junit.framework.TestCase
import org.junit.Test

class DiscountTshirtTest {

    // Subject under test
    private lateinit var discount: DiscountTshirt

    @Test
    fun calculateDiscountSuccess() {

        // Assemble pre conditions
        val expected = 4.0
        discount = DiscountTshirt(4)

        // Act
        val result = discount.calculateDiscount()

        // Verify
        TestCase.assertEquals(expected, result)
    }

    @Test
    fun calculateDiscountFailure() {

        // Assemble pre conditions
        val expected = 0.0
        discount = DiscountTshirt(1)

        // Act
        val result = discount.calculateDiscount()

        // Verify
        TestCase.assertEquals(expected, result)
    }


}