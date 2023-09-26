package com.example.dobcalc

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private var tvAgeInMinutes: TextView? = null
    private  var tvSelectedDate: TextView? = null
    private var tvAgeInHours: TextView? = null
    private var tvAgeInDays: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDatePicker: Button = findViewById(R.id.btnDatePicker)
        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        tvAgeInMinutes = findViewById(R.id.tvAgeInMinutes)
        tvAgeInHours = findViewById(R.id.tvAgeInHours)
        tvAgeInDays = findViewById(R.id.tvAgeInDays)
        btnDatePicker.setOnClickListener{
            clickDatePicker()

        }
    }
    private fun clickDatePicker(){
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this,
            { _, selectedyear, selectedmonth, selecteddayofMonth ->
                Toast.makeText(this,
                    "Year was $selectedyear, month was ${selectedmonth+1}, day of month was $selecteddayofMonth",
                    Toast.LENGTH_LONG).show()
                val selectedDate = "$selecteddayofMonth/${selectedmonth+1}/$selectedyear"

                tvSelectedDate?.text = selectedDate

                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

                val theDate = sdf.parse(selectedDate)
                theDate?.let{

                    val selectedDateInMinutes = theDate.time/60000

                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                    currentDate?.let{
                        val currentDateInMinutes = currentDate.time/60000

                        val differenceInMinutes = (currentDateInMinutes - selectedDateInMinutes)

                        tvAgeInMinutes?.text = differenceInMinutes.toString()

                        val differenceInHours = (currentDateInMinutes - selectedDateInMinutes)/60

                        tvAgeInHours?.text = differenceInHours.toString()

                        val differenceInDays = differenceInHours/24

                        tvAgeInDays?.text = differenceInDays.toString()

                    }


                }

            },
            year,
            month,
            day
        )
        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
        dpd.show()

    }
}
