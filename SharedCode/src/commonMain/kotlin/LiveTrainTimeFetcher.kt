package com.jetbrains.handson.mpp.mobile


class LiveTrainTimeFetcher {
    private lateinit var departureStation : Station
    private lateinit var arrivalStation : Station

    fun setDepartureStation(station : Station) {
        this.departureStation = station
    }

    fun setArrivalStation(station : Station) {
        this.arrivalStation = station
    }

    fun getLiveTrainsURL() : String {
        return "https://www.lner.co.uk/travel-information/travelling-now/live-train-times/depart/${this.departureStation.getId()}/${this.arrivalStation.getId()}#LiveDepResults"
    }
}