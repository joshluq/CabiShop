package es.joshluq.cabishop.navigator

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import es.joshluq.cabishop.feature.checkout.CheckOutScreen
import es.joshluq.cabishop.feature.paymentsuccess.PaymentSuccessScreen
import es.joshluq.cabishop.feature.productlist.ProductListScreen

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {

    NavHost(
        navController = navController,
        startDestination = Destination.PRODUCT_LIST.name,
        modifier = modifier
    ) {
        composable(Destination.PRODUCT_LIST.name) {
            ProductListScreen(navController)
        }
        composable(Destination.CHECK_OUT.name) {
            CheckOutScreen(navController)
        }
        composable(Destination.PAYMENT_SUCCESS.name) {
            PaymentSuccessScreen(navController)
        }
    }
}

