package ir.tapsell.plus.sample.compose.ui.screens

import android.app.Activity
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import ir.tapsell.plus.sample.compose.ui.components.LogText
import ir.tapsell.plus.sample.compose.ui.presentation.InterstitialViewModel
import ir.tapsellplus.sample.compose.ui.theme.TapsellPlusComposeSampleTheme

private const val BUTTON_WIDTH = 0.5F

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InterstitialScreen(
    modifier: Modifier = Modifier,
    viewModel: InterstitialViewModel = viewModel()
) {
    val context = LocalContext.current as Activity

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Interstitial")
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
                onClick = { viewModel.requestAd(context) }
            ) {
                Text(text = "Request Ad")
            }

            Button(
                modifier = Modifier.fillMaxWidth(BUTTON_WIDTH),
                enabled = viewModel.isShowButtonEnabled.value,
                onClick = { viewModel.showAd(context) }
            ) {
                Text(text = "Show Ad")
            }

            LogText(viewModel.log.value)
        }
    }


}

@Preview(showBackground = true)
@Composable
fun InterstitialScreenPreview() {
    TapsellPlusComposeSampleTheme {
        InterstitialScreen()
    }
}