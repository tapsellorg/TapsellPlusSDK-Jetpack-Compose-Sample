package ir.tapsell.plus.sample.compose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ir.tapsell.plus.sample.compose.ui.screens.HomeScreen
import ir.tapsell.plus.sample.compose.ui.screens.InterstitialScreen
import ir.tapsell.plus.sample.compose.ui.screens.NativeBannerScreen
import ir.tapsell.plus.sample.compose.ui.screens.PreRollScreen
import ir.tapsell.plus.sample.compose.ui.screens.RewardedVideoScreen
import ir.tapsell.plus.sample.compose.ui.screens.StandardBannerScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Routes.Home
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Routes.Home) {
            HomeScreen(navController = navController)
        }

        composable(Routes.REWARDED_VIDEO) {
            RewardedVideoScreen()
        }

        composable(Routes.INTERSTITIAL) {
            InterstitialScreen()
        }

        composable(Routes.NATIVE_BANNER) {
            NativeBannerScreen()
        }

        composable(Routes.STANDARD_BANNER) {
            StandardBannerScreen()
        }

        composable(Routes.PRE_ROLL) {
            PreRollScreen()
        }
    }
}