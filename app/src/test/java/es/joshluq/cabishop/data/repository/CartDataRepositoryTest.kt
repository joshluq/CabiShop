package es.joshluq.cabishop.data.repository

import es.joshluq.cabishop.data.datasource.local.dao.CartDao
import es.joshluq.cabishop.data.datasource.local.entity.ItemEntity
import es.joshluq.cabishop.domain.model.Product
import es.joshluq.cabishop.domain.model.ProductType
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class CartDataRepositoryTest {

    // Subject under test
    private lateinit var repository: CartDataRepository

    //Mock dependencies
    @MockK
    lateinit var mockCartDao: CartDao


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = CartDataRepository(mockCartDao)
    }

    @Test
    fun getCart() = runTest {
        // Assemble pre conditions
        val mockEntity = mockk<ItemEntity>()

        coEvery {
            mockCartDao.getAllItems()
        } returns flowOf(listOf(mockEntity))

        coEvery {
            mockEntity.id
        } returns 0

        coEvery {
            mockEntity.quantity
        } returns 1

        coEvery {
            mockEntity.productPrice
        } returns 2.0

        coEvery {
            mockEntity.productType
        } returns "VOUCHER"

        coEvery {
            mockEntity.productName
        } returns "name"

        // Act
        val result = repository.getCart().first()

        // Verify
        verify { mockCartDao.getAllItems() }
        TestCase.assertTrue(result.isSuccess)
        TestCase.assertTrue(result.getOrThrow().items.isNotEmpty())
        TestCase.assertTrue(result.getOrThrow().items.first().quantity == 1)
        TestCase.assertTrue(result.getOrThrow().items.first().product.price == 2.0)
        TestCase.assertTrue(result.getOrThrow().items.first().product.name == "name")
        TestCase.assertTrue(result.getOrThrow().items.first().product.type == ProductType.VOUCHER)

    }

    @Test
    fun addProductUpdate() = runTest {
        // Assemble pre conditions
        val mockEntity = mockk<ItemEntity>()

        val mockProduct = mockk<Product>()

        every {
            mockCartDao.getAllItems()
        } returns flowOf(listOf(mockEntity))

        every {
            mockEntity.id
        } returns 0

        every {
            mockEntity.quantity
        } returns 1

        every {
            mockEntity.productPrice
        } returns 2.0

        every {
            mockEntity.productType
        } returns "VOUCHER"

        every {
            mockEntity.productName
        } returns "name"

        every {
            mockProduct.type
        } returns ProductType.VOUCHER

        every {
            mockEntity.copy(quantity = 2)
        } returns mockEntity

        coEvery {
            mockCartDao.updateItem(mockEntity)
        } returns Unit

        // Act
        val result = repository.addProduct(mockProduct).first()

        // Verify
        verify { mockCartDao.getAllItems() }
        coVerify { mockCartDao.updateItem(mockEntity) }
        TestCase.assertTrue(result.isSuccess)
    }

    @Test
    fun addProductInsert() = runTest {
        // Assemble pre conditions
        val mockEntity = mockk<ItemEntity>()

        val mockProduct = mockk<Product>()

        every {
            mockEntity.productPrice
        } returns 2.0

        every {
            mockEntity.productType
        } returns "VOUCHER"

        every {
            mockEntity.productName
        } returns "name"

        every {
            mockEntity.quantity
        } returns 1

        every {
            mockCartDao.getAllItems()
        } returns flowOf(listOf())

        every {
            mockProduct.type
        } returns ProductType.VOUCHER

        every {
            mockProduct.name
        } returns "name"

        every {
            mockProduct.price
        } returns 2.0

        coEvery {
            mockCartDao.insertItem(mockEntity)
        } returns Unit

        // Act
        val result = repository.addProduct(mockProduct).first()

        // Verify
        verify { mockCartDao.getAllItems() }
        coVerify { mockCartDao.insertItem(mockEntity) }
        TestCase.assertTrue(result.isSuccess)
    }

    @Test
    fun removeProductUpdate() = runTest {
        // Assemble pre conditions
        val mockEntity = mockk<ItemEntity>()

        val mockProduct = mockk<Product>()

        every {
            mockCartDao.getAllItems()
        } returns flowOf(listOf(mockEntity))

        every {
            mockEntity.id
        } returns 0

        every {
            mockEntity.quantity
        } returns 2

        every {
            mockEntity.productPrice
        } returns 2.0

        every {
            mockEntity.productType
        } returns "VOUCHER"

        every {
            mockEntity.productName
        } returns "name"

        every {
            mockProduct.type
        } returns ProductType.VOUCHER

        every {
            mockEntity.copy(quantity = 1)
        } returns mockEntity

        coEvery {
            mockCartDao.updateItem(mockEntity)
        } returns Unit

        // Act
        val result = repository.removeProduct(mockProduct).first()

        // Verify
        verify { mockCartDao.getAllItems() }
        coVerify { mockCartDao.updateItem(mockEntity) }
        TestCase.assertTrue(result.isSuccess)
    }

    @Test
    fun removeProductRemove() = runTest {
        // Assemble pre conditions
        val mockEntity = mockk<ItemEntity>()

        val mockProduct = mockk<Product>()

        every {
            mockCartDao.getAllItems()
        } returns flowOf(listOf(mockEntity))

        every {
            mockEntity.id
        } returns 0

        every {
            mockEntity.quantity
        } returns 1

        every {
            mockEntity.productPrice
        } returns 2.0

        every {
            mockEntity.productType
        } returns "VOUCHER"

        every {
            mockEntity.productName
        } returns "name"

        every {
            mockProduct.type
        } returns ProductType.VOUCHER

        coEvery {
            mockCartDao.deleteItem(mockEntity)
        } returns Unit

        // Act
        val result = repository.removeProduct(mockProduct).first()

        // Verify
        verify { mockCartDao.getAllItems() }
        coVerify { mockCartDao.deleteItem(mockEntity) }
        TestCase.assertTrue(result.isSuccess)
    }

    @Test
    fun removeAllProducts() = runTest {
        // Assemble pre conditions
        coEvery {
            mockCartDao.clearItems()
        } returns Unit

        // Act
        val result = repository.removeAllProducts().first()

        // Verify
        verify { mockCartDao.clearItems() }
        TestCase.assertTrue(result.isSuccess)
    }
}