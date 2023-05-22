package es.joshluq.cabishop.feature.checkout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import es.joshluq.cabishop.R
import es.joshluq.cabishop.common.extension.toAmount
import es.joshluq.cabishop.common.receiver.receive
import es.joshluq.cabishop.domain.model.Item
import es.joshluq.cabishop.navigator.Destination
import es.joshluq.cabishop.ui.theme.CabiShopTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckOutScreen(
    navController: NavHostController = rememberNavController(),
    viewModel: CheckOutViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.sendIntent(UiIntent.FetchDataIntent)
    }

    var items by remember { mutableStateOf(listOf<Item>()) }

    viewModel.uiState.receive<UiState, UiState.SuccessState> { state ->
        items = state.cart.items
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        text = stringResource(id = R.string.checkout_title_screen),
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.onPrimary,
                        textAlign = TextAlign.Center
                    )
                })
        }
    ) { padding ->
        MainContent(padding, items) {
            viewModel.sendIntent(UiIntent.PayIntent)
            navController.navigate(Destination.PAYMENT_SUCCESS.name)
        }
    }
}

@Composable
private fun MainContent(padding: PaddingValues, cartItems: List<Item>, onClick: () -> Unit) {
    Column(Modifier.padding(padding)) {
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_map),
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = ""
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Ship to",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge,
            )
        }

        OutlinedCard(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            border = CardDefaults.outlinedCardBorder(false)
        ) {
            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Home",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "558 North Avenue, Elkhorn",
                    style = MaterialTheme.typography.bodyLarge,
                )
                Text(
                    text = "Nebraska",
                    style = MaterialTheme.typography.bodyLarge,
                )
                Text(
                    text = "+99 1234567890",
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_payment),
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = ""
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Payment",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge,
            )
        }

        OutlinedCard(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            border = CardDefaults.outlinedCardBorder(false)
        ) {
            Row(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_mastercard),
                    tint = Color.Unspecified,
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "•••• •••• •••• 7203",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_shopping_cart_checkout),
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = ""
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Summary",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge,
            )
        }

        val basePrice = cartItems.sumOf { item ->
            item.product.price * item.quantity
        }
        val discounts = cartItems.sumOf { item ->
            item.discount?.calculateDiscount() ?: 0.0
        }

        SummaryContainer(basePrice, discounts)


        Spacer(modifier = Modifier.weight(1f))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
                .padding(horizontal = 12.dp),
            onClick = onClick
        ) {
            Text(text = "Pay ${(basePrice - discounts).toAmount()}")
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
private fun SummaryContainer(basePrice: Double, discounts: Double) {
    OutlinedCard(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
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

@Preview(showSystemUi = true)
@Composable
fun CheckOutScreenPreview() {
    CabiShopTheme {
        MainContent(PaddingValues(), listOf()) {

        }
    }
}