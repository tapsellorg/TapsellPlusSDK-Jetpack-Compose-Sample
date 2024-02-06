package ir.tapsell.plus.sample.compose.ui.screens

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.ui.PlayerView
import ir.tapsell.plus.sample.compose.model.PreRollContainer
import ir.tapsell.plus.sample.compose.ui.components.LogText
import ir.tapsell.plus.sample.compose.ui.presentation.PreRollViewModel
import ir.tapsell.plussample.android.R
import ir.tapsellplus.sample.compose.ui.theme.TapsellPlusComposeSampleTheme

private const val BUTTON_WIDTH = 0.5F

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreRollScreen(
    modifier: Modifier = Modifier,
    viewModel: PreRollViewModel = viewModel()
) {
    val context = LocalContext.current as Activity
    val playerView = remember { PlayerView(context) }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.destroyAd()
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "PreRoll")
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TapsellPlusPreRollView(
                modifier = modifier.wrapContentSize(),
                onUpdate = viewModel::updateAdContainer
            )

            Button(
                modifier = Modifier.fillMaxWidth(BUTTON_WIDTH),
                onClick = { viewModel.requestAd(context, playerView) }
            ) {
                Text(text = "Request Ad")
            }

            Button(
                modifier = Modifier.fillMaxWidth(BUTTON_WIDTH),
                enabled = viewModel.isShowButtonEnabled.value,
                onClick = viewModel::showVideo
            ) {
                Text(text = "Show Video")
            }

            LogText(viewModel.log.value)
        }
    }
}

@Composable
private fun TapsellPlusPreRollView(
    modifier: Modifier = Modifier,
    onUpdate: (PreRollContainer) -> Unit = {},
) {
    val context = LocalContext.current as Activity
    AndroidView(
        modifier = modifier,
        factory = {
            val view =
                LayoutInflater.from(context)
                    .inflate(R.layout.preroll_container, null, false)
            val frameLayout = view.findViewById<ViewGroup>(R.id.ad_container)
            frameLayout.also {
                onUpdate(
                    PreRollContainer.from(
                        player = it.findViewById(R.id.video_player_container),
                        companion = it.findViewById(R.id.companion_ad_slot),
                        playerView = it.findViewById(R.id.exo_player)
                    )
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreRollScreenPreview() {
    TapsellPlusComposeSampleTheme {
        PreRollScreen()
    }
}