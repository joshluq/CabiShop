package es.joshluq.cabishop.data.datasource.remote.response

import com.fasterxml.jackson.annotation.JsonProperty

class ProductListResponse {

    @JsonProperty("products")
    val product: List<ProductResponse?>? = null

}