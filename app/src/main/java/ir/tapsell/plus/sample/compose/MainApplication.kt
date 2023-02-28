package ir.tapsell.plus.sample.compose

import android.app.Application
import android.util.Log
import ir.tapsell.plus.TapsellPlus
import ir.tapsell.plus.TapsellPlusInitListener
import ir.tapsell.plus.model.AdNetworkError
import ir.tapsell.plus.model.AdNetworks
import ir.tapsellplus.sample.compose.utils.Constants

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Initialize Tapsell SDK
        initializeTapsell()
    }

    /**
     * Initialize Tapsell SDK
     */
    private fun initializeTapsell() {
        TapsellPlus.initialize(this, Constants.TAPSELL_KEY, object : TapsellPlusInitListener {
            override fun onInitializeSuccess(adNetworks: AdNetworks) {
                Log.d("onInitializeSuccess", adNetworks.name)
            }

            override fun onInitializeFailed(
                adNetworks: AdNetworks,
                adNetworkError: AdNetworkError
            ) {
                Log.e(
                    "onInitializeFailed",
                    "ad network: " + adNetworks.name + ", error: " + adNetworkError.errorMessage
                )
            }
        })
        TapsellPlus.setGDPRConsent(this, true)
        TapsellPlus.setDebugMode(Log.DEBUG)
    }
}