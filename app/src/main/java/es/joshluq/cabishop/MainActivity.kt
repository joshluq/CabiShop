package es.joshluq.cabishop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import es.joshluq.cabishop.navigator.NavGraph
import es.joshluq.cabishop.ui.theme.CabiShopTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CabiShopTheme {
                NavGraph()
            }
        }
    }
}