package com.jetbrains.handson.mpp.mobile

class LiveTrainTimeFetcher {
    private lateinit var departureStation : Station
    private lateinit var arrivalStation : Station

    fun setDepartureStation(station : Station) {
        this.departureStation = station
    }

    fun getDepartureStation(): Station {
        return departureStation
    }

    fun setArrivalStation(station : Station) {
        this.arrivalStation = station
    }

    fun getArrivalsStation(): Station {
        return arrivalStation
    }
}