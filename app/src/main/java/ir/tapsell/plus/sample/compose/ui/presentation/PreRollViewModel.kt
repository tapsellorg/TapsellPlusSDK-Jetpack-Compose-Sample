package ir.tapsell.plus.sample.compose.ui.presentation

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaItem.AdsConfiguration
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.ui.PlayerView
import com.google.ads.interactivemedia.v3.api.AdErrorEvent
import com.google.ads.interactivemedia.v3.api.AdEvent
import ir.tapsell.plus.TapsellPlus
import ir.tapsell.plus.VastRequestListener
import ir.tapsell.plus.sample.compose.model.PreRollContainer
import ir.tapsell.sdk.preroll.TapsellPrerollAd
import ir.tapsell.sdk.preroll.ima.ImaAdsLoader
import ir.tapsellplus.sample.compose.utils.Constants

class PreRollViewModel : ViewModel() {

    var log = mutableStateOf("")

    private var ad: TapsellPrerollAd? = null
    private var adTag: String? = null
    var isShowButtonEnabled = mutableStateOf(false)
    private var adViewContainer = mutableStateOf<PreRollContainer?>(null)
    var adsLoader = mutableStateOf<ImaAdsLoader?>(null)
    private var player: ExoPlayer? = null

    fun requestAd(
        activity: Activity,
        playerView: PlayerView,
    ) {
        addLog("requestAd")
        ad?.let {
            it.destroy()
            releasePlayer()
        }
        TapsellPlus.getVastTag(Constants.TAPSELL_VAST_PREROLL)?.let { tag ->
            adTag = tag
            addLog("ad tag loaded successfully")
            Log.d(TAG, "requestAd: ad tag: $tag")
        }
        adViewContainer.value?.let { adContainer ->
            TapsellPlus.requestVastAd(
                activity,
                playerView,
                SAMPLE_VIDEO_URL,
                adContainer.player,
                adContainer.companion,
                object : VastRequestListener {
                    override fun onAdsLoaderCreated(adsLoader: ImaAdsLoader) {
                        adsLoader.release()
                        this@PreRollViewModel.adsLoader.value = adsLoader
                        initializePlayer(activity)
                        isShowButtonEnabled.value = true
                        addLog("onAdsLoaderCreated")
                    }

                    override fun onAdEvent(adEvent: AdEvent?) {
                        addLog("event: ${adEvent?.type}")
                    }

                    override fun onAdError(errorEvent: AdErrorEvent?) {
                        addLog(errorEvent?.error?.localizedMessage.orEmpty())
                        isShowButtonEnabled.value = false
                    }
                }).also { preRollAd -> ad = preRollAd }
        } ?: addLog("AdViewContainer is null")
    }

    fun showVideo() {
        addLog("showVideo")
        val mediaItem = MediaItem.Builder().setUri(Uri.parse(SAMPLE_VIDEO_URL)).apply {
            // bind ad tag
            adTag?.let { tag ->
                setAdsConfiguration(AdsConfiguration.Builder(Uri.parse(tag)).build()).build()
            }
        }.build()

        player?.apply {
            setMediaItem(mediaItem)
            prepare()
        }
        isShowButtonEnabled.value = false
    }

    fun initializePlayer(context: Context) {
        if (adsLoader.value == null) {
            addLog("initializePlayer failed: adsLoader is null")
            return
        }
        if (adViewContainer.value == null) {
            addLog("initializePlayer failed: adViewContainer is null")
            return
        }
        adViewContainer.value?.playerView?.let { playerView ->
            // Set up the factory for media sources, passing the ads loader and ad view providers.
            val mediaSourceFactory = DefaultMediaSourceFactory(context)
                .setLocalAdInsertionComponents({ _ -> adsLoader.value }, playerView)

            // Create an ExoPlayer and set it as the player for content and ads.
            player = ExoPlayer.Builder(context)
                .setMediaSourceFactory(mediaSourceFactory)
                .build()
            playerView.player = player
            adsLoader.value?.setPlayer(player)

            player?.playWhenReady = true
        }
    }

    private fun releasePlayer() {
        addLog("releasePlayer")
        adViewContainer.value?.playerView?.player = null
        player?.release()
        player = null
    }

    fun destroyAd() {
        ad?.destroy()
        releasePlayer()
    }

    fun updateAdContainer(container: PreRollContainer) {
        adViewContainer.value = container
    }

    private fun addLog(message: String) {
        log.value = buildString {
            append(message)
            appendLine()
            append(log.value)
        }
    }


    companion object {
        private const val TAG = "PreRollViewModel"
        private const val SAMPLE_VIDEO_URL =
            "https://storage.backtory.com/tapsell-server/sdk/VASTContentVideo.mp4"
    }
}
