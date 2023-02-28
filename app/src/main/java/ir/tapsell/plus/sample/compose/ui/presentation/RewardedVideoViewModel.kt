package ir.tapsell.plus.sample.compose.ui.presentation

import android.app.Activity
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ir.tapsell.plus.AdRequestCallback
import ir.tapsell.plus.AdShowListener
import ir.tapsell.plus.TapsellPlus
import ir.tapsell.plus.model.TapsellPlusAdModel
import ir.tapsell.plus.model.TapsellPlusErrorModel
import ir.tapsellplus.sample.compose.utils.Constants


class RewardedVideoViewModel : ViewModel() {

    private var responseId: String? = null
    var isShowButtonEnabled = mutableStateOf(false)
    var log = mutableStateOf("")

    fun requestAd(activity: Activity) {
        TapsellPlus.requestRewardedVideoAd(
            activity, Constants.TAPSELL_REWARDED_VIDEO,
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
        TapsellPlus.showRewardedVideoAd(activity, responseId,
            object : AdShowListener() {
                override fun onOpened(tapsellPlusAdModel: TapsellPlusAdModel) {
                    super.onOpened(tapsellPlusAdModel)
                    addLog("onOpened")
                }

                override fun onClosed(tapsellPlusAdModel: TapsellPlusAdModel) {
                    super.onClosed(tapsellPlusAdModel)
                    addLog("onClosed")
                }

                override fun onRewarded(tapsellPlusAdModel: TapsellPlusAdModel) {
                    super.onRewarded(tapsellPlusAdModel)
                    addLog("onRewarded")
                }

                override fun onError(tapsellPlusErrorModel: TapsellPlusErrorModel) {
                    super.onError(tapsellPlusErrorModel)
                    addLog(tapsellPlusErrorModel.toString())
                }
            })
        isShowButtonEnabled.value = false
    }

    private fun addLog(message: String) {
        log.value = log.value.plus("\n").plus(message)
    }
}