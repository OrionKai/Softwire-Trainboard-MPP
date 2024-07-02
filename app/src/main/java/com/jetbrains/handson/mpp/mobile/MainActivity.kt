package com.jetbrains.handson.mpp.mobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView

class MainActivity : AppCompatActivity(), ApplicationContract.View {

    fun initStationSpinner(spinnerId: Int, listOfStations: List<Station>, setter: (Station) -> Unit): Spinner {
        val spinner: Spinner = findViewById(spinnerId) // Create an ArrayAdapter using the string array and a default spinner layout.

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listOfStations.map { it.getName() })

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = adapter

        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedStation = listOfStations[position]
                setter(selectedStation)
            }
        }

        return spinner
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val presenter = ApplicationPresenter()
        presenter.onViewTaken(this)

        val fetcher = LiveTrainTimeFetcher()

        val listOfStations = listOf(Station("EDB","Edinburgh Waverley"),
            Station("KGX","Kings Cross"),
            Station("DAR","Darlington"),
            Station("YRK","York"),
            Station("NCL","Newcastle"))

        val departuresSpinner: Spinner = initStationSpinner(R.id.departure_spinner, listOfStations,
            fetcher::setDepartureStation)
        val arrivalsSpinner : Spinner = initStationSpinner(R.id.arrival_spinner, listOfStations,
            fetcher::setArrivalStation)
    }

    override fun setLabel(text: String) {
        findViewById<TextView>(R.id.main_text).text = text
    }
}
