package es.joshluq.cabishop.feature.productlist

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.centerAlignedTopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import es.joshluq.cabishop.R
import es.joshluq.cabishop.ui.theme.CabiShopTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen() {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                colors = centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        text = "CabiShop",
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.onPrimary,
                        textAlign = TextAlign.Center
                    )
                },
                actions = {
                    IconButton(
                        modifier = Modifier.size(32.dp),
                        onClick = { /*TODO*/ }) {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            painter = painterResource(id = R.drawable.ic_shopping_cart),
                            tint = MaterialTheme.colorScheme.onPrimary,
                            contentDescription = "Añadir al carrito"
                        )
                    }
                })
        }
    ) { padding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2), // it basically tells no. of cells in a row
            contentPadding = padding,
            modifier = Modifier.fillMaxHeight()
        ) {
            items(3) {
                ProductItem()
            }
        }
    }


}

@Composable
private fun ProductItem() {
    OutlinedCard(
        modifier = Modifier.padding(12.dp),
        border = CardDefaults.outlinedCardBorder()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)

        ) {}
        Column(Modifier.padding(vertical = 8.dp, horizontal = 12.dp)) {
            Text(
                text = "Taza",
                style = MaterialTheme.typography.titleLarge
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "11,00 €",
                    style = MaterialTheme.typography.titleLarge
                )
                IconButton(
                    modifier = Modifier.size(32.dp),
                    onClick = { /*TODO*/ }) {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        painter = painterResource(id = R.drawable.ic_add_shopping_cart),
                        contentDescription = "Añadir al carrito"
                    )
                }
            }
        }
    }
}

@Preview(
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
internal fun ProductListScreenPreview() {
    CabiShopTheme {
        ProductListScreen()
    }
}