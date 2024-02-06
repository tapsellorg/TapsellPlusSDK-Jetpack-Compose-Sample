package ir.tapsell.plus.sample.compose.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ir.tapsell.plus.sample.compose.navigation.Routes
import ir.tapsellplus.sample.compose.ui.theme.TapsellPlusComposeSampleTheme

private const val BUTTON_WIDTH = 0.5F

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "TapsellPlus Jetpack Compose Sample")
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Button(
                modifier = Modifier.fillMaxWidth(BUTTON_WIDTH),
                onClick = {
                    navController.navigate(Routes.REWARDED_VIDEO)
                }
            ) {
                Text(text = "Rewarded Video Ad")
            }

            Button(
                modifier = Modifier.fillMaxWidth(BUTTON_WIDTH),
                onClick = {
                    navController.navigate(Routes.INTERSTITIAL)
                }
            ) {
                Text(text = "Interstitial Ad")
            }

            Button(
                modifier = Modifier.fillMaxWidth(BUTTON_WIDTH),
                onClick = {
                    navController.navigate(Routes.STANDARD_BANNER)
                }
            ) {
                Text(text = "Standard Banner Ad")
            }

            Button(
                modifier = Modifier.fillMaxWidth(BUTTON_WIDTH),
                onClick = {
                    navController.navigate(Routes.NATIVE_BANNER)
                }
            ) {
                Text(text = "Native Banner Ad")
            }

            Button(
                modifier = Modifier.fillMaxWidth(BUTTON_WIDTH),
                onClick = {
                    navController.navigate(Routes.PRE_ROLL)
                }
            ) {
                Text(text = "PreRoll Ad")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    TapsellPlusComposeSampleTheme {
        HomeScreen()
    }
}