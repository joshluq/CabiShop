package es.joshluq.cabishop.data.datasource.remote

import es.joshluq.cabishop.data.datasource.remote.response.ProductListResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("Products.json")
    suspend fun getProducts(): Response<ProductListResponse>

}