package es.joshluq.cabishop.domain.usecase

import es.joshluq.cabishop.domain.model.Product
import es.joshluq.cabishop.domain.repository.CartRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AddProductUseCaseTest {

    // Subject under test
    private lateinit var useCase: AddProductUseCase

    //Mock dependencies
    @MockK
    lateinit var mockRepository: CartRepository


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = AddProductUseCase(mockRepository)
    }

    @Test
    fun addProductUseCaseWhenSuccess() = runTest {
        // Assemble pre conditions
        val expectedResult = flowOf(Result.success(Unit))
        val mockProduct = mockk<Product>()

        coEvery {
            mockRepository.addProduct(mockProduct)
        } returns expectedResult

        // Act
        useCase(AddProductUseCase.Params(mockProduct))

        // Verify
        verify { mockRepository.addProduct(mockProduct) }
        TestCase.assertTrue(expectedResult.first().isSuccess)
    }

    @Test
    fun addProductUseCaseWhenFailure() = runTest {
        // Assemble pre conditions
        val expectedResult = flowOf(Result.failure<Unit>(Exception()))
        val mockProduct = mockk<Product>()

        coEvery {
            mockRepository.addProduct(mockProduct)
        } returns expectedResult

        // Act
        useCase(AddProductUseCase.Params(mockProduct))

        // Verify
        verify { mockRepository.addProduct(mockProduct) }
        TestCase.assertTrue(expectedResult.first().isFailure)
    }
}