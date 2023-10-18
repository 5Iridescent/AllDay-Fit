package com.example.alldayfit.calendar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.alldayfit.R
import com.example.alldayfit.databinding.CalendarActivityBinding


class CalendarActivity : AppCompatActivity() {

    private lateinit var binding: CalendarActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CalendarActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.calendarFrame, CalendarFragment())
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}