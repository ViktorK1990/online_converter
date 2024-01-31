package com.example.currency_convertor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import org.json.JSONObject
import java.net.URL
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spinner = findViewById<Spinner>(R.id.spinner)
        val spinner2 = findViewById<Spinner>(R.id.spinner2)
        val input_field = findViewById<EditText>(R.id.input_field)
        val result_field = findViewById<TextView>(R.id.result_field)
        val convert_btn = findViewById<Button>(R.id.convert_btn)

        val items = resources.getStringArray(R.array.spinner_items)
        val adapter = ArrayAdapter(this, R.layout.spinner_items, items)
        spinner.adapter = adapter
        spinner2.adapter = adapter

        convert_btn.setOnClickListener {
            if (input_field.text.toString().trim() == "")
                Toast.makeText(this, "Input amount!", Toast.LENGTH_LONG).show()
            else {
                val currency = spinner.selectedItem.toString().lowercase()
                val current_date = SimpleDateFormat("yyyy-MM-dd").format(Date())
                val url =
                    "https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/$current_date/currencies/$currency.json"

                val policy = ThreadPolicy.Builder().permitAll().build()
                StrictMode.setThreadPolicy(policy)
                val apiResponse = URL(url).readText()

                val data = JSONObject(apiResponse).getJSONObject(currency.toString())
                val res = data.getDouble(spinner2.selectedItem.toString().lowercase(Locale.ROOT)).toDouble()

                val result = DecimalFormat("#.###").format(res * input_field.text.toString().toDouble())

                result_field.text = result.toString()
            }
        }
    }
}