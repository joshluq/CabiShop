package es.joshluq.cabishop.feature.shoppingcart

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import es.joshluq.cabishop.R
import es.joshluq.cabishop.common.extension.toAmount
import es.joshluq.cabishop.common.receiver.receive
import es.joshluq.cabishop.domain.model.Item
import es.joshluq.cabishop.domain.model.Product
import es.joshluq.cabishop.domain.model.ProductType
import es.joshluq.cabishop.navigator.Destination
import es.joshluq.cabishop.ui.theme.CabiShopTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingCartScreen(
    navController: NavHostController = rememberNavController(),
    viewModel: ShoppingCartViewModel = hiltViewModel(),
    bottomSheetState: SheetState
) {

    LaunchedEffect(Unit) {
        viewModel.sendIntent(UiIntent.FetchDataIntent)
    }

    var items by remember { mutableStateOf(listOf<Item>()) }
    val scope = rememberCoroutineScope()

    viewModel.uiState.receive<UiState, UiState.SuccessState> { state ->
        items = state.cart.items
    }

    MainContainer(
        cartItems = items,
        onNavigateToCheckOut = {
            scope.launch {
                bottomSheetState.hide()
                navController.navigate(Destination.CHECK_OUT.name)
            }
        },
        onAddProduct = { product ->
            viewModel.sendIntent(UiIntent.AddProductIntent(product))
        },
        onRemoveProduct = { product ->
            viewModel.sendIntent(UiIntent.RemoveProductIntent(product))
        })
}

@Composable
private fun MainContainer(
    cartItems: List<Item>,
    onNavigateToCheckOut: () -> Unit = {},
    onAddProduct: (Product) -> Unit = {},
    onRemoveProduct: (Product) -> Unit = {}
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        item {
            TitleContainer()
        }

        item {
            Spacer(modifier = Modifier.height(12.dp))
            Divider()
            Spacer(modifier = Modifier.height(12.dp))
        }

        if (cartItems.isEmpty()) {
            item {
                EmptyList()
            }
        } else {
            items(items = cartItems, itemContent = { cartItem ->
                CartItemContainer(
                    cartItem = cartItem,
                    onAddProduct = onAddProduct,
                    onRemoveProduct = onRemoveProduct
                )
                Spacer(modifier = Modifier.height(8.dp))
            })

            item {
                Spacer(modifier = Modifier.height(12.dp))
                Divider()
                Spacer(modifier = Modifier.height(12.dp))
            }

            val basePrice = cartItems.sumOf { item ->
                item.product.price * item.quantity
            }
            val discounts = cartItems.sumOf { item ->
                item.discount?.calculateDiscount() ?: 0.0
            }

            item {
                SummaryContainer(basePrice, discounts)
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp),
                    onClick = onNavigateToCheckOut
                ) {
                    Text(text = stringResource(R.string.shoppingcart_checkout_button))
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
        }

    }
}

@Composable
private fun TitleContainer() {
    Text(
        text = stringResource(R.string.shoppingcart_title_screen),
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.headlineSmall,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun CartItemContainer(
    cartItem: Item,
    onAddProduct: (Product) -> Unit = {},
    onRemoveProduct: (Product) -> Unit = {}
) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp),
        border = CardDefaults.outlinedCardBorder(false)
    ) {
        Row {
            val imageRes = when (cartItem.product.type) {
                ProductType.VOUCHER -> R.drawable.img_voucher
                ProductType.TSHIRT -> R.drawable.img_tshirt
                ProductType.MUG -> R.drawable.img_mug
            }
            Image(
                modifier = Modifier
                    .background(Color.White)
                    .size(110.dp),
                painter = painterResource(id = imageRes),
                contentDescription = ""
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 12.dp)
            ) {
                Text(
                    text = cartItem.product.name,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val price = cartItem.product.price * cartItem.quantity.toDouble()
                    val discount = cartItem.discount?.calculateDiscount() ?: 0.0

                    if (discount > 0.0) {
                        Text(
                            text = price.toAmount(),
                            style = MaterialTheme.typography.bodySmall.copy(textDecoration = TextDecoration.LineThrough),
                            color = Color.LightGray
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    Text(
                        text = price.minus(cartItem.discount?.calculateDiscount() ?: 0.0)
                            .toAmount(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    OutlinedCard(
                        border = CardDefaults.outlinedCardBorder(false)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            IconButton(
                                modifier = Modifier.size(16.dp),
                                onClick = {
                                    onRemoveProduct(cartItem.product)
                                }) {
                                Icon(
                                    modifier = Modifier.size(16.dp),
                                    painter = painterResource(id = R.drawable.ic_remove),
                                    tint = MaterialTheme.colorScheme.primary,
                                    contentDescription = "Remove one item"
                                )
                            }
                            Text(
                                text = "${cartItem.quantity}",
                                modifier = Modifier.width(36.dp),
                                style = MaterialTheme.typography.bodyMedium
                                    .copy(textAlign = TextAlign.Center)
                                    .copy(color = MaterialTheme.colorScheme.onBackground),
                            )

                            IconButton(
                                modifier = Modifier.size(16.dp),
                                onClick = {
                                    onAddProduct(cartItem.product)
                                }) {
                                Icon(
                                    modifier = Modifier.size(16.dp),
                                    painter = painterResource(id = R.drawable.ic_add),
                                    tint = MaterialTheme.colorScheme.primary,
                                    contentDescription = "Add one item"
                                )
                            }
                        }
                    }
                }


            }
        }
    }
}

@Composable
private fun SummaryContainer(basePrice: Double, discounts: Double) {
    OutlinedCard(
        modifier = Modifier.fillMaxWidth(),
        border = CardDefaults.outlinedCardBorder(false)
    ) {

        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.shoppingcart_baseprice_label),
                style = MaterialTheme.typography.bodyLarge,
            )
            Text(
                text = basePrice.toAmount(),
                style = MaterialTheme.typography.bodyLarge,
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.shoppingcart_discounts_label),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "-${discounts.toAmount()}",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.shoppingcart_totalprice_label),
                style = MaterialTheme.typography.bodyLarge,
            )
            Text(
                text = (basePrice - discounts).toAmount(),
                style = MaterialTheme.typography.bodyLarge,
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun EmptyList() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(36.dp))
        Icon(
            modifier = Modifier.size(136.dp),
            painter = painterResource(id = R.drawable.ic_empty_cart),
            tint = Color.Unspecified,
            contentDescription = stringResource(R.string.shoppingcart_emptylist_label)
        )
        Text(
            text = stringResource(R.string.shoppingcart_emptylist_label),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

@Composable
@Preview(
    showSystemUi = true,
)
fun ShoppingCartScreenPreview() {
    CabiShopTheme {
        MainContainer(listOf())
    }
}