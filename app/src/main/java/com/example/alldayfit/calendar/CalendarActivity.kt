package com.example.alldayfit.calendar

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.alldayfit.R
import com.example.alldayfit.calendar.adapter.CalendarViewOptionsAdapter
import com.example.alldayfit.databinding.CalendarActivityBinding


class CalendarActivity : AppCompatActivity() {

    internal lateinit var binding: CalendarActivityBinding

    private val examplesAdapter = CalendarViewOptionsAdapter {
        val fragment = it.createView()
        val anim = it.animation
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(anim.enter, anim.exit, anim.popEnter, anim.popExit)
            .add(R.id.homeContainer, fragment, fragment.javaClass.simpleName)
            .addToBackStack(fragment.javaClass.simpleName)
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CalendarActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.activityToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.examplesRecyclerview.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = examplesAdapter
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> onBackPressed().let { true }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

//        val fragmentTransaction = supportFragmentManager.beginTransaction()
//        fragmentTransaction.replace(R.id.calendarFrame, CalendarFragment())
//        fragmentTransaction.addToBackStack(null)
//        fragmentTransaction.commit()
