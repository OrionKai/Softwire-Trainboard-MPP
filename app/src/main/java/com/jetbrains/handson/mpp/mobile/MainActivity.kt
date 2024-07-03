package com.jetbrains.handson.mpp.mobile

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.jetbrains.handson.mpp.mobile.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), ApplicationContract.View {

    private lateinit var binding: ActivityMainBinding

    private fun Spinner.initStationSpinner(stations: List<Station>, setter: (Station) -> Unit,
                                   context: Context) {

        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, stations.map { it.name }).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        this.adapter = adapter

        this.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedStation = stations[position]
                setter(selectedStation)
            }
        }
    }

    private fun Button.initSubmitButton(presenter: ApplicationContract.Presenter) {
        this.setOnClickListener {
            presenter.fetchLiveTrainTimes()
        }
    }

    override fun setLabel(text: String) {
        findViewById<TextView>(R.id.main_text).text = text
    }

    override fun setLiveTrainTimesInfo(text: String) {
        findViewById<TextView>(R.id.arrivals_text).text = text
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val presenter = ApplicationPresenter().apply {
            onViewTaken(this@MainActivity)
        }
        val fetcher = presenter.getFetcher()

        val stations = listOf(Station("EDB","Edinburgh Waverley"),
            Station("KGX","Kings Cross"),
            Station("DAR","Darlington"),
            Station("YRK","York"),
            Station("NCL","Newcastle"))

        binding.departureSpinner.initStationSpinner(stations, {station -> fetcher.departureStation = station},
            this)
        binding.arrivalSpinner.initStationSpinner(stations, {station -> fetcher.arrivalStation = station},
            this)

        binding.submitButton.initSubmitButton(presenter)
    }
}
