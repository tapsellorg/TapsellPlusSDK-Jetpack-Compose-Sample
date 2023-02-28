package ir.tapsell.plus.sample.compose.ui.presentation

import android.app.Activity
import android.view.ViewGroup
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ir.tapsell.plus.AdRequestCallback
import ir.tapsell.plus.AdShowListener
import ir.tapsell.plus.TapsellPlus
import ir.tapsell.plus.TapsellPlusBannerType
import ir.tapsell.plus.model.TapsellPlusAdModel
import ir.tapsell.plus.model.TapsellPlusErrorModel
import ir.tapsellplus.sample.compose.utils.Constants


class StandardBannerViewModel : ViewModel() {

    private var responseId: String? = null
    var isShowButtonEnabled = mutableStateOf(false)
    var log = mutableStateOf("")
    private var standardBannerContainer = mutableStateOf<ViewGroup?>(null)

    fun requestAd(
        activity: Activity,
        bannerType: TapsellPlusBannerType = TapsellPlusBannerType.BANNER_320x50
    ) {
        TapsellPlus.requestStandardBannerAd(
            activity, Constants.TAPSELL_STANDARD_BANNER, bannerType,
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
        standardBannerContainer.value?.let { container ->
            TapsellPlus.showStandardBannerAd(activity, responseId, container,
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

    fun updateStandardBannerContainer(viewGroup: ViewGroup) {
        standardBannerContainer.value = viewGroup
    }

    private fun addLog(message: String) {
        log.value = log.value.plus("\n").plus(message)
    }
}