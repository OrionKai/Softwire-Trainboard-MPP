package com.jetbrains.handson.mpp.mobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView

class MainActivity : AppCompatActivity(), ApplicationContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val presenter = ApplicationPresenter()
        presenter.onViewTaken(this)

        val spinner: Spinner = findViewById(R.id.departure_spinner) // Create an ArrayAdapter using the string array and a default spinner layout.

        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var v = parent?.getItemAtPosition(position).toString()
            }
        }

        ArrayAdapter.createFromResource(this, R.array.stations, android.R.layout.simple_spinner_item
        ).also { adapter -> // Specify the layout to use when the list of choices appears.
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) // Apply the adapter to the spinner.
            spinner.adapter = adapter
        }



//        val arrival_spinner: Spinner = findViewById(R.id.arrival_spinner) // Create an ArrayAdapter using the string array and a default spinner layout.
//        ArrayAdapter.createFromResource(this, R.array.stations, android.R.layout.simple_spinner_item
//        ).also { adapter -> // Specify the layout to use when the list of choices appears.
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) // Apply the adapter to the spinner.
//            arrival_spinner.adapter = adapter
//        }



    }

    override fun setLabel(text: String) {
        findViewById<TextView>(R.id.main_text).text = text
    }



}
