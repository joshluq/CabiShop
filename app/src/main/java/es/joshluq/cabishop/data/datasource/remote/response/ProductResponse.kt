package es.joshluq.cabishop.data.datasource.remote.response

import com.fasterxml.jackson.annotation.JsonProperty
import es.joshluq.cabishop.domain.model.ProductType

class ProductResponse {

    @JsonProperty("code")
    val type: ProductType? = null

    @JsonProperty("name")
    val name: String? = null

    @JsonProperty("price")
    val price: Double? = null
}