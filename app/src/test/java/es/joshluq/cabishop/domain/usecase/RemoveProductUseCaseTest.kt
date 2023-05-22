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
class RemoveProductUseCaseTest {

    // Subject under test
    private lateinit var useCase: RemoveProductUseCase

    //Mock dependencies
    @MockK
    lateinit var mockRepository: CartRepository


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = RemoveProductUseCase(mockRepository)
    }

    @Test
    fun removeProductUseCaseWhenSuccess() = runTest {
        // Assemble pre conditions
        val expectedResult = flowOf(Result.success(Unit))
        val mockProduct = mockk<Product>()

        coEvery {
            mockRepository.removeProduct(mockProduct)
        } returns expectedResult

        // Act
        useCase(RemoveProductUseCase.Params(mockProduct))

        // Verify
        verify { mockRepository.removeProduct(mockProduct) }
        TestCase.assertTrue(expectedResult.first().isSuccess)
    }

    @Test
    fun removeProductUseCaseWhenFailure() = runTest {
        // Assemble pre conditions
        val expectedResult = flowOf(Result.failure<Unit>(Exception()))
        val mockProduct = mockk<Product>()

        coEvery {
            mockRepository.removeProduct(mockProduct)
        } returns expectedResult

        // Act
        useCase(RemoveProductUseCase.Params(mockProduct))

        // Verify
        verify { mockRepository.removeProduct(mockProduct) }
        TestCase.assertTrue(expectedResult.first().isFailure)
    }

}