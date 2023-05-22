package es.joshluq.cabishop.feature.productlist

import es.joshluq.cabishop.CoroutineTestRule
import es.joshluq.cabishop.domain.model.Cart
import es.joshluq.cabishop.domain.model.Product
import es.joshluq.cabishop.domain.usecase.AddProductUseCase
import es.joshluq.cabishop.domain.usecase.GetCartUseCase
import es.joshluq.cabishop.domain.usecase.GetProductsUseCase
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
class ProductListViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    // Subject under test
    private lateinit var viewModel: ProductListViewModel

    //Mock dependencies
    @MockK
    lateinit var mockGetProductsUseCase: GetProductsUseCase

    @MockK
    lateinit var mockGetCartUseCase: GetCartUseCase

    @MockK
    lateinit var mockAddProductUseCase: AddProductUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = ProductListViewModel(
            mockGetProductsUseCase,
            mockGetCartUseCase,
            mockAddProductUseCase
        )
    }

    @Test
    fun fetchDataIntentSuccess() = runTest {
        // Assemble pre conditions
        val mockProduct = mockk<Product>()
        val mockCart = mockk<Cart>()
        val getProductResult = flowOf(Result.success(listOf(mockProduct)))
        val getCartResult = flowOf(Result.success(mockCart))

        coEvery {
            mockGetProductsUseCase(Unit)
        } returns getProductResult

        coEvery {
            mockGetCartUseCase(Unit)
        } returns getCartResult

        coEvery {
            getCartResult.first().getOrThrow().items
        } returns listOf()

        // Act
        viewModel.sendIntent(UiIntent.FetchDataIntent)

        // Verify
        verify { mockGetProductsUseCase(Unit) }
        verify { mockGetCartUseCase(Unit) }
        TestCase.assertTrue(viewModel.uiState is UiState.SuccessState)

    }

    @Test
    fun fetchDataIntentFailure() = runTest {
        // Assemble pre conditions
        val expectedResult = flowOf(Result.failure<List<Product>>(Exception()))

        coEvery {
            mockGetProductsUseCase(Unit)
        } returns expectedResult

        // Act
        viewModel.sendIntent(UiIntent.FetchDataIntent)

        // Verify
        verify { mockGetProductsUseCase(Unit) }
        TestCase.assertTrue(viewModel.uiState is UiState.ErrorState)
        // verify { mockGetCartUseCase(Unit) }
        // verify { mockAddProductUseCase(AddProductUseCase.Params(mockProduct)) }

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
}