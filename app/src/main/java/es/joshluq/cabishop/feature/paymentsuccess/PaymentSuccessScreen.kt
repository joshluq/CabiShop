package es.joshluq.cabishop.feature.paymentsuccess

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import es.joshluq.cabishop.R
import es.joshluq.cabishop.navigator.Destination
import es.joshluq.cabishop.ui.theme.CabiShopTheme

@Composable
fun PaymentSuccessScreen(
    navController: NavHostController = rememberNavController()
) {
    BackHandler(true) {}
    Scaffold(containerColor = MaterialTheme.colorScheme.primary) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_success),
                tint = Color.Unspecified,
                contentDescription = null
            )

            Text(
                text = "Sucsess",
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Thank you for shopping using CabiShop",
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.onPrimary,
                    contentColor = MaterialTheme.colorScheme.primary
                ),
                onClick = {
                    navController.popBackStack(Destination.PRODUCT_LIST.name, false)
                }) {
                Text(text = "Back to Store")
            }

        }
    }
}


@Preview
@Composable
fun PaymentSuccessScreenPreview() {
    CabiShopTheme {
        PaymentSuccessScreen()
    }
}