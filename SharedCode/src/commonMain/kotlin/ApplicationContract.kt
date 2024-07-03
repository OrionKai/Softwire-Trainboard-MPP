package com.jetbrains.handson.mpp.mobile

import kotlinx.coroutines.CoroutineScope

interface ApplicationContract {
    interface View {
        fun setLabel(text: String)
        fun setLiveTrainTimesInfo(text: String)
    }

    abstract class Presenter: CoroutineScope {
        abstract fun onViewTaken(view: View)
        abstract fun getFetcher(): LiveTrainTimeFetcher
        abstract fun fetchLiveTrainTimes()
    }
}
