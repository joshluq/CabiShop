package es.joshluq.cabishop.feature.productlist

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Badge
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.centerAlignedTopAppBarColors
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import es.joshluq.cabishop.R
import es.joshluq.cabishop.common.composable.LoaderDialog
import es.joshluq.cabishop.common.composable.SimpleDialog
import es.joshluq.cabishop.common.extension.toAmount
import es.joshluq.cabishop.common.receiver.receive
import es.joshluq.cabishop.domain.model.Product
import es.joshluq.cabishop.domain.model.ProductType
import es.joshluq.cabishop.feature.shoppingcart.ShoppingCartScreen
import es.joshluq.cabishop.ui.theme.CabiShopTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun ProductListScreen(
    navController: NavHostController = rememberNavController(),
    viewModel: ProductListViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.sendIntent(UiIntent.FetchDataIntent)
    }

    var products by remember { mutableStateOf(listOf<Product>()) }

    var cartCount by remember { mutableStateOf(0) }

    viewModel.uiState.receive<UiState, UiState.SuccessState> { state ->
        products = state.products
        cartCount = state.cartCount
    }

    viewModel.uiState.receive<UiState, UiState.LoadingState> {
        LoaderDialog()
    }

    viewModel.uiState.receive<UiState, UiState.ErrorState> {
        SimpleDialog(
            description = stringResource(R.string.productlist_error_message),
            buttonText = stringResource(R.string.productlist_error_button)
        ) {
            viewModel.sendIntent(UiIntent.FetchDataIntent)
        }
    }

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Hidden,
            skipHiddenState = false
        )
    )

    BottomSheetScaffold(
        topBar = {
            TopBarContainer(cartCount) {
                scope.launch { scaffoldState.bottomSheetState.expand() }
            }
        },
        scaffoldState = scaffoldState,
        sheetContent = {
            ShoppingCartScreen(
                navController = navController,
                bottomSheetState = scaffoldState.bottomSheetState
            )
        },
        sheetPeekHeight = 0.dp,
    ) { padding ->
        MainContainer(padding, products) { product ->
            scope.launch {
                scaffoldState.snackbarHostState.showSnackbar("${product.name} added to cart")
            }
            viewModel.sendIntent(UiIntent.AddProductIntent(product))

        }
    }
}

@Composable
private fun MainContainer(
    padding: PaddingValues,
    products: List<Product>,
    onAddProduct: (Product) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = padding,
        modifier = Modifier.fillMaxHeight()
    ) {
        items(items = products) { product ->
            ProductItem(product) { onAddProduct(product) }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TopBarContainer(cartCount: Int = 0, onClick: () -> Unit = {}) {
    CenterAlignedTopAppBar(
        modifier = Modifier.fillMaxWidth(),
        colors = centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center
            )
        },
        actions = {
            IconButton(
                modifier = Modifier.size(52.dp),
                onClick = onClick
            ) {
                Box {
                    Icon(
                        modifier = Modifier.size(36.dp),
                        painter = painterResource(id = R.drawable.ic_shopping_cart),
                        tint = MaterialTheme.colorScheme.onPrimary,
                        contentDescription = "Add to Cart"
                    )
                    if (cartCount > 0) {
                        Badge(
                            modifier = Modifier
                                .padding(0.dp)
                                .align(Alignment.TopEnd)
                                .size(20.dp)
                        ) {
                            Text(
                                text = "$cartCount",
                                fontSize = 10.sp,
                                maxLines = 1,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                }

            }

        })
}

@Composable
private fun ProductItem(product: Product, onClick: () -> Unit) {
    OutlinedCard(
        modifier = Modifier.padding(12.dp),
        border = CardDefaults.outlinedCardBorder(false)
    ) {
        val imageRes = when (product.type) {
            ProductType.VOUCHER -> R.drawable.img_voucher
            ProductType.TSHIRT -> R.drawable.img_tshirt
            ProductType.MUG -> R.drawable.img_mug
        }
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .height(180.dp),
            painter = painterResource(id = imageRes),
            contentDescription = product.name
        )
        Column(Modifier.padding(vertical = 8.dp, horizontal = 12.dp)) {
            Text(
                text = product.name,
                style = MaterialTheme.typography.titleLarge,
                minLines = 2
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = product.price.toAmount(),
                    style = MaterialTheme.typography.titleLarge
                )
                IconButton(
                    modifier = Modifier.size(32.dp),
                    onClick = onClick
                ) {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        painter = painterResource(id = R.drawable.ic_add_shopping_cart),
                        contentDescription = "Add to cart",
                        tint = MaterialTheme.colorScheme.primary
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
        MainContainer(PaddingValues(), listOf(Product(ProductType.MUG, "Test", 9.0))) {}
    }
}