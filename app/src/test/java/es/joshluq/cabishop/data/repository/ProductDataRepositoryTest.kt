package es.joshluq.cabishop.data.repository


import es.joshluq.cabishop.data.datasource.remote.ApiService
import es.joshluq.cabishop.data.datasource.remote.response.ProductListResponse
import es.joshluq.cabishop.data.datasource.remote.response.ProductResponse
import es.joshluq.cabishop.domain.model.ProductType
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class ProductDataRepositoryTest {

    // Subject under test
    private lateinit var repository: ProductDataRepository

    //Mock dependencies
    @MockK
    lateinit var mockApiService: ApiService


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = ProductDataRepository(mockApiService)
    }

    @Test
    fun getProductListWhenSuccess() = runTest {
        // Assemble pre conditions
        val mockResponse = mockk<Response<ProductListResponse>>()
        val mockProductList = mockk<ProductListResponse>()
        val mockProduct = mockk<ProductResponse>()

        coEvery {
            mockApiService.getProducts()
        } returns mockResponse

        coEvery {
            mockResponse.isSuccessful
        } returns true

        coEvery {
            mockResponse.body()
        } returns mockProductList

        coEvery {
            mockProductList.product
        } returns listOf(mockProduct)

        coEvery {
            mockProduct.type
        } returns ProductType.MUG

        coEvery {
            mockProduct.name
        } returns "Name"

        coEvery {
            mockProduct.price
        } returns 1.0


        // Act
        val result = repository.getProductList().first()

        // Verify
        coVerify { mockApiService.getProducts() }
        TestCase.assertTrue(result.isSuccess)
        TestCase.assertTrue(result.getOrThrow().isNotEmpty())
        TestCase.assertTrue(result.getOrThrow().first().name == "Name")
        TestCase.assertTrue(result.getOrThrow().first().price == 1.0)
        TestCase.assertTrue(result.getOrThrow().first().type == ProductType.MUG)

    }

    @Test
    fun getProductListWhenFailure() = runTest {
        // Assemble pre conditions
        val mockResponse = mockk<Response<ProductListResponse>>()
        val mockProductList = mockk<ProductListResponse>()

        coEvery {
            mockResponse.isSuccessful
        } returns false

        coEvery {
            mockResponse.body()
        } returns mockProductList

        coEvery {
            mockApiService.getProducts()
        } returns mockResponse

        // Act
        val result = repository.getProductList().first()

        // Verify
        TestCase.assertTrue(result.isFailure)

    }

    @Test
    fun getProductListWhenFailureInvalidProductType() = runTest {
        // Assemble pre conditions
        val mockResponse = mockk<Response<ProductListResponse>>()
        val mockProductList = mockk<ProductListResponse>()
        val mockProduct = mockk<ProductResponse>()

        coEvery {
            mockApiService.getProducts()
        } returns mockResponse

        coEvery {
            mockResponse.isSuccessful
        } returns true

        coEvery {
            mockResponse.body()
        } returns mockProductList

        coEvery {
            mockProductList.product
        } returns listOf(mockProduct)

        coEvery {
            mockProduct.type
        } returns null


        // Act
        val result = repository.getProductList().first()

        // Verify
        TestCase.assertTrue(result.isFailure)

    }
}