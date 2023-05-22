package es.joshluq.cabishop.domain.usecase

import es.joshluq.cabishop.domain.model.Product
import es.joshluq.cabishop.domain.repository.ProductRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetProductsUseCaseTest {

    // Subject under test
    private lateinit var useCase: GetProductsUseCase

    //Mock dependencies
    @MockK
    lateinit var mockRepository: ProductRepository


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = GetProductsUseCase(mockRepository)
    }

    @Test
    fun getProductsUseCaseWhenSuccess() = runTest {
        // Assemble pre conditions
        val expectedResult = flowOf(Result.success(listOf<Product>()))

        coEvery {
            mockRepository.getProductList()
        } returns expectedResult

        // Act
        val result = useCase(Unit)

        // Verify
        verify { mockRepository.getProductList() }
        TestCase.assertTrue(expectedResult.first().isSuccess)
        TestCase.assertEquals(expectedResult.first(), result.first())
    }

    @Test
    fun getProductsUseCaseWhenFailure() = runTest {
        // Assemble pre conditions
        val expectedResult = flowOf(Result.failure<List<Product>>(Exception()))

        coEvery {
            mockRepository.getProductList()
        } returns expectedResult

        // Act
        useCase(Unit)

        // Verify
        verify { mockRepository.getProductList() }
        TestCase.assertTrue(expectedResult.first().isFailure)
    }
}