package com.jetbrains.handson.mpp.mobile


class LiveTrainTimeFetcher {
    lateinit var departureStation : Station
    lateinit var arrivalStation : Station

    fun getLiveTrainsURL() : String {
        return "https://www.lner.co.uk/travel-information/travelling-now/live-train-times/depart/${this.departureStation.id}/${this.arrivalStation.id}#LiveDepResults"
    }
}