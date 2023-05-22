package es.joshluq.cabishop.domain.usecase

import es.joshluq.cabishop.domain.repository.CartRepository
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
class ClearProductsUseCaseTest {

    // Subject under test
    private lateinit var useCase: ClearProductsUseCase

    //Mock dependencies
    @MockK
    lateinit var mockRepository: CartRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = ClearProductsUseCase(mockRepository)
    }

    @Test
    fun clearProductsUseCaseWhenSuccess() = runTest {
        // Assemble pre conditions
        val expectedResult = flowOf(Result.success(Unit))

        coEvery {
            mockRepository.removeAllProducts()
        } returns expectedResult

        // Act
        useCase(Unit)

        // Verify
        verify { mockRepository.removeAllProducts() }
        TestCase.assertTrue(expectedResult.first().isSuccess)
    }

    @Test
    fun clearProductsUseCaseWhenFailure() = runTest {
        // Assemble pre conditions
        val expectedResult = flowOf(Result.failure<Unit>(Exception()))

        coEvery {
            mockRepository.removeAllProducts()
        } returns expectedResult

        // Act
        useCase(Unit)

        // Verify
        verify { mockRepository.removeAllProducts() }
        TestCase.assertTrue(expectedResult.first().isFailure)
    }

}