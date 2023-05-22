package es.joshluq.cabishop.feature.shoppingcart

import es.joshluq.cabishop.CoroutineTestRule
import es.joshluq.cabishop.domain.model.Cart
import es.joshluq.cabishop.domain.model.Item
import es.joshluq.cabishop.domain.model.Product
import es.joshluq.cabishop.domain.usecase.AddProductUseCase
import es.joshluq.cabishop.domain.usecase.GetCartUseCase
import es.joshluq.cabishop.domain.usecase.RemoveProductUseCase
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
class ShoppingCartViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    // Subject under test
    private lateinit var viewModel: ShoppingCartViewModel

    //Mock dependencies
    @MockK
    lateinit var mockGetCartUseCase: GetCartUseCase

    @MockK
    lateinit var mockAddProductUseCase: AddProductUseCase

    @MockK
    lateinit var mockRemoveProductUseCase: RemoveProductUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = ShoppingCartViewModel(
            mockGetCartUseCase,
            mockAddProductUseCase,
            mockRemoveProductUseCase
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
    fun addProductIntentSuccess() = runTest {
        // Assemble pre conditions
        val expectedResult = flowOf(Result.success(Unit))
        val mockProduct = mockk<Product>()

        coEvery {
            mockAddProductUseCase(AddProductUseCase.Params(mockProduct))
        } returns expectedResult

        // Act
        viewModel.sendIntent(UiIntent.AddProductIntent(mockProduct))

        // Verify
        verify { mockAddProductUseCase(any()) }

    }

    @Test
    fun removeProductIntentSuccess() = runTest {
        // Assemble pre conditions
        val expectedResult = flowOf(Result.success(Unit))
        val mockProduct = mockk<Product>()

        coEvery {
            mockRemoveProductUseCase(RemoveProductUseCase.Params(mockProduct))
        } returns expectedResult

        // Act
        viewModel.sendIntent(UiIntent.RemoveProductIntent(mockProduct))

        // Verify
        verify { mockRemoveProductUseCase(any()) }
    }


}