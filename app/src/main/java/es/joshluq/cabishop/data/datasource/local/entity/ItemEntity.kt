package es.joshluq.cabishop.data.datasource.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "productItem_table")
data class ItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "product_type") val productType: String,
    @ColumnInfo(name = "product_name") val productName: String,
    @ColumnInfo(name = "product_price")  val productPrice: Double,
    val quantity: Int
)