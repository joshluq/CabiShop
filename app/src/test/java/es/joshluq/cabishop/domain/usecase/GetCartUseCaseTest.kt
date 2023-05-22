package es.joshluq.cabishop.domain.usecase


import es.joshluq.cabishop.domain.model.Cart
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
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetCartUseCaseTest {

    // Subject under test
    private lateinit var useCase: GetCartUseCase

    //Mock dependencies
    @MockK
    lateinit var mockRepository: CartRepository


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = GetCartUseCase(mockRepository)
    }

    @Test
    fun getCartUseCaseWhenSuccess() = runTest {
        // Assemble pre conditions
        val mockCart = mockk<Cart>()
        val expectedResult = flowOf(Result.success(mockCart))

        coEvery {
            mockRepository.getCart()
        } returns expectedResult

        // Act
        useCase(Unit)

        // Verify
        verify { mockRepository.getCart() }
        TestCase.assertTrue(expectedResult.first().isSuccess)
    }

    @Test
    fun getCartUseCaseWhenFailure() = runTest {
        // Assemble pre conditions
        val expectedResult = flowOf(Result.failure<Cart>(Exception()))

        coEvery {
            mockRepository.getCart()
        } returns expectedResult

        // Act
        useCase(Unit)

        // Verify
        verify { mockRepository.getCart() }
        TestCase.assertTrue(expectedResult.first().isFailure)
    }
}