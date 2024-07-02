package com.jetbrains.handson.mpp.mobile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


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

        val listOfStations = listOf(Station("EDB","Edinburgh Waverley"),
            Station("KGX","Kings Cross"),
            Station("DAR","Darlington"),
            Station("YRK","York"),
            Station("NCL","Newcastle"))

        val fetcher = presenter.getFetcher()

        val departuresSpinner: Spinner = initStationSpinner(R.id.departure_spinner, listOfStations,
            fetcher::setDepartureStation)
        val arrivalsSpinner : Spinner = initStationSpinner(R.id.arrival_spinner, listOfStations,
            fetcher::setArrivalStation)

        val button : Button = findViewById(R.id.submit_button)
        button.setOnClickListener {
            val urlBuilder = Uri.Builder()
            urlBuilder.scheme("https")
                .authority("www.lner.co.uk")
                .appendPath("travel-information")
                .appendPath("travelling-now")
                .appendPath("live-train-times")
                .appendPath("depart")
                .appendPath(fetcher.getDepartureStation().getId())
                .appendPath(fetcher.getArrivalsStation().getId())
                .fragment("LiveDepResults")
            val queryUrl = urlBuilder.build().toString()
            val urlIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(queryUrl)
            )

            startActivity(urlIntent)
        }
    }

    override fun setLabel(text: String) {
        findViewById<TextView>(R.id.main_text).text = text
    }
}
