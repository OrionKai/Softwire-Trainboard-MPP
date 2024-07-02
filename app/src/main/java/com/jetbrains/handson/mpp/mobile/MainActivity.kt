package com.jetbrains.handson.mpp.mobile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Browser
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date


class MainActivity : AppCompatActivity(), ApplicationContract.View {

    private final val AUTH_API_KEY = "amogus"

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
//
//    fun initFetchButton(buttonId: Int)

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
            val dateTimeFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            val currentDateTimeString = dateTimeFormatter.format(Date())
            val urlBuilder = Uri.Builder()
            urlBuilder.scheme("https")
                .authority("mobile-api-softwire2.lner.co.uk")
                .appendPath("v1")
                .appendPath("fares")
                .appendQueryParameter("originStation", fetcher.getDepartureStation().getId())
                .appendQueryParameter("destinationStation", fetcher.getArrivalsStation().getId())
                .appendQueryParameter("outboundDateTime", currentDateTimeString)
                .appendQueryParameter("numberOfChildren", "2")
                .appendQueryParameter("numberOfAdults", "2")
            val queryUrl = urlBuilder.build().toString()
            val urlIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(queryUrl)
            )
            val bundle = Bundle()
            bundle.putString("x-api-key", AUTH_API_KEY)
            urlIntent.putExtra(Browser.EXTRA_HEADERS, bundle)

            startActivity(urlIntent)
        }
    }

    override fun setLabel(text: String) {
        findViewById<TextView>(R.id.main_text).text = text
    }
}
