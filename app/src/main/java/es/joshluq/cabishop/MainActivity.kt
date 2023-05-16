package es.joshluq.cabishop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import es.joshluq.cabishop.feature.productlist.ProductListScreen
import es.joshluq.cabishop.ui.theme.CabiShopTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CabiShopTheme {
                ProductListScreen()
            }
        }
    }
}