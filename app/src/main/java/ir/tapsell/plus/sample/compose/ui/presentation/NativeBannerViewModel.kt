package ir.tapsell.plus.sample.compose.ui.presentation

import android.app.Activity
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ir.tapsell.plus.AdHolder
import ir.tapsell.plus.AdRequestCallback
import ir.tapsell.plus.AdShowListener
import ir.tapsell.plus.TapsellPlus
import ir.tapsell.plus.model.TapsellPlusAdModel
import ir.tapsell.plus.model.TapsellPlusErrorModel
import ir.tapsellplus.sample.compose.utils.Constants


class NativeBannerViewModel : ViewModel() {

    private var responseId: String? = null
    var isShowButtonEnabled = mutableStateOf(false)
    var log = mutableStateOf("")
    var adHolder = mutableStateOf<AdHolder?>(null)

    fun requestAd(activity: Activity) {
        TapsellPlus.requestNativeAd(
            activity, Constants.TAPSELL_NATIVE_BANNER,
            object : AdRequestCallback() {
                override fun response(tapsellPlusAdModel: TapsellPlusAdModel) {
                    super.response(tapsellPlusAdModel)
                    if (activity.isDestroyed) return
                    responseId = tapsellPlusAdModel.responseId
                    addLog("response")
                    isShowButtonEnabled.value = true
                }

                override fun error(message: String) {
                    if (activity.isDestroyed) return
                    addLog(message)
                }
            })
    }

    fun showAd(activity: Activity) {
        adHolder.value?.let { holder ->
            TapsellPlus.showNativeAd(activity, responseId, holder,
                object : AdShowListener() {
                    override fun onOpened(tapsellPlusAdModel: TapsellPlusAdModel) {
                        super.onOpened(tapsellPlusAdModel)
                        addLog("onOpened")
                    }

                    override fun onError(tapsellPlusErrorModel: TapsellPlusErrorModel) {
                        super.onError(tapsellPlusErrorModel)
                        addLog(tapsellPlusErrorModel.toString())
                    }
                })
            isShowButtonEnabled.value = false
        }

    }

    fun destroyAd(activity: Activity) {
        TapsellPlus.destroyNativeBanner(activity, responseId)
    }

    fun updateAdHolder(adHolder: AdHolder?) {
        this.adHolder.value = adHolder
    }

    private fun addLog(message: String) {
        log.value = log.value.plus("\n").plus(message)
    }
}