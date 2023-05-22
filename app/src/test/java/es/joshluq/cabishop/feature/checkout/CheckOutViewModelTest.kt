package es.joshluq.cabishop.feature.checkout

import es.joshluq.cabishop.CoroutineTestRule
import es.joshluq.cabishop.domain.model.Cart
import es.joshluq.cabishop.domain.model.Item
import es.joshluq.cabishop.domain.usecase.ClearProductsUseCase
import es.joshluq.cabishop.domain.usecase.GetCartUseCase
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
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CheckOutViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    // Subject under test
    private lateinit var viewModel: CheckOutViewModel

    //Mock dependencies
    @MockK
    lateinit var mockGetCartUseCase: GetCartUseCase

    @MockK
    lateinit var mockClearProductsUseCase: ClearProductsUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = CheckOutViewModel(
            mockGetCartUseCase,
            mockClearProductsUseCase
        )
    }

    @Test
    fun fetchDataIntentSuccess() = runTest {
        // Assemble pre conditions
        val mockCart = mockk<Cart>()
        val mockItem = mockk<Item>()
        val getCartResult = flowOf(Result.success(mockCart))

        coEvery {
            mockGetCartUseCase(Unit)
        } returns getCartResult

        coEvery {
            getCartResult.first().getOrThrow().items
        } returns listOf(mockItem)

        // Act
        viewModel.sendIntent(UiIntent.FetchDataIntent)

        // Verify
        verify { mockGetCartUseCase(Unit) }
        TestCase.assertTrue(viewModel.uiState is UiState.SuccessState)
    }

    @Test
    fun payIntentIntentSuccess() = runTest {
        // Assemble pre conditions
        val getCartResult = flowOf(Result.success(Unit))

        coEvery {
            mockClearProductsUseCase(Unit)
        } returns getCartResult

        // Act
        viewModel.sendIntent(UiIntent.PayIntent)

        // Verify
        verify { mockClearProductsUseCase(Unit) }
        TestCase.assertTrue(viewModel.uiState is UiState.PaymentSuccessState)
    }
}