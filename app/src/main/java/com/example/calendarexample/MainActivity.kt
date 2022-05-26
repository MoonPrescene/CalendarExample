package com.example.calendarexample

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.*
import com.example.calendarexample.databinding.ActivityMainBinding
import java.time.LocalDate
import java.time.YearMonth

class MainActivity : AppCompatActivity(), CalendarWeekAdapter.OnItemWeekListener{

    private lateinit var binding: ActivityMainBinding
    private lateinit var calendarAdapter: CalendarAdapter
    //private lateinit var calendarWeekAdapter: CalendarWeekAdapter
    //private lateinit var calendarDayOfWeekAdapter: CalendarDayOfWeekAdapter
    private lateinit var selectedDate: LocalDate
    private var selectedDay: String = ""
    private val snapHelper: SnapHelper = PagerSnapHelper()

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        selectedDate = LocalDate.now()
        setMonthView()
        //setListWeekView()
        //setWeekView()
        binding.apply {
            nextMonth.setOnClickListener {
                scrollToNextMonth()
            }
            previousMonth.setOnClickListener {
                scrollToPreviousMonth()
            }
        }
    }

    @SuppressLint("NewApi", "NotifyDataSetChanged")
    private fun scrollToPreviousMonth() {
        selectedDate = selectedDate.minusMonths(1)
        Toast.makeText(this@MainActivity, "$selectedDate", Toast.LENGTH_SHORT).show()
        setMonthView()
        //setListWeekView()
        //setWeekView()
        calendarAdapter.notifyDataSetChanged()
        //calendarDayOfWeekAdapter.notifyDataSetChanged()
        //calendarWeekAdapter.notifyDataSetChanged()
    }

    @SuppressLint("NewApi", "NotifyDataSetChanged")
    private fun scrollToNextMonth() {
        selectedDate = selectedDate.plusMonths(1)
        Toast.makeText(this@MainActivity, "$selectedDate", Toast.LENGTH_SHORT).show()
        setMonthView()
        //setListWeekView()
        //setWeekView()
        calendarAdapter.notifyDataSetChanged()
        //calendarDayOfWeekAdapter.notifyDataSetChanged()
        //calendarWeekAdapter.notifyDataSetChanged()
    }

    private fun setMonthView() {
        val daysInMonth: MutableList<String> = daysInMonthArray(selectedDate)
        calendarAdapter = CalendarAdapter(daysInMonth)
        binding.recyclerViewMonth.apply {
            adapter = calendarAdapter
            layoutManager = GridLayoutManager(context, 7)
        }
    }

    /*private fun setWeekView() {
        val daysInMonth: MutableList<String> = daysInMonthArray(selectedDate)
        Log.v("_MainActivity", "daysInMonth: $daysInMonth")
        calendarDayOfWeekAdapter = CalendarDayOfWeekAdapter(daysInMonth, this)
        binding.recyclerViewWeek.apply {
            adapter = calendarDayOfWeekAdapter
            layoutManager = LinearLayoutPagerManager(context, LinearLayoutManager.HORIZONTAL, false, 7)
        }
        snapHelper.attachToRecyclerView(binding.recyclerViewWeek)
    }*/

   /* private fun setListWeekView() {
        val daysInMonth: MutableList<String> = daysInMonthArray(selectedDate)
        calendarWeekAdapter = CalendarWeekAdapter(convertToListWeeks(daysInMonth), this)
        binding.recyclerViewWeek.apply {
            adapter = calendarWeekAdapter
            layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL,
                false)
        }
        snapHelper.attachToRecyclerView(binding.recyclerViewWeek)
    }*/


    @SuppressLint("NewApi")
    private fun daysInMonthArray(date: LocalDate): MutableList<String> {
        val dayInMonthArray = mutableListOf<String>()
        val yearMonth = YearMonth.from(date)
        val daysInMonth = yearMonth.lengthOfMonth()
        val firstOfMonth = selectedDate.withDayOfMonth(1)
        val dayOfWeek = firstOfMonth.dayOfWeek.value
        Log.v("_MainActivity", "$dayOfWeek")
        for (i in 1..42){
            Log.v("_MainActivity", "$firstOfMonth")
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek){
                dayInMonthArray.add("")
            }else{
                dayInMonthArray.add(String.format("${i - dayOfWeek}"))
            }
        }
        checkMonth(dayInMonthArray)
        return dayInMonthArray
    }

    private fun checkMonth(daysInMonth: MutableList<String>){
        if (daysInMonth[35] == ""){
           daysInMonth.removeAt(35)
        }
        daysInMonth.removeAt(0)
    }

   /* private fun convertToListWeeks(daysInMonth: MutableList<String>): MutableList<MutableList<String>>{
        val listWeeks: MutableList<MutableList<String>> = mutableListOf()
        val week1: MutableList<String> = mutableListOf()
        val week2: MutableList<String> = mutableListOf()
        val week3: MutableList<String> = mutableListOf()
        val week4: MutableList<String> = mutableListOf()
        val week5: MutableList<String> = mutableListOf()
        val week6: MutableList<String> = mutableListOf()

        Log.v("_MainActivity", "daysInMonth: $daysInMonth")

        for (i in daysInMonth.indices){
            if (i in 0..6){
                week1.add(daysInMonth[i])
            }
            if (i in 7..13){
                week2.add(daysInMonth[i])
            }
            if (i in 14..20){
                week3.add(daysInMonth[i])
            }
            if (i in 21..27){
                week4.add(daysInMonth[i])
            }
            if (i in 28..34){
                week5.add(daysInMonth[i])
            }
            if (i in 35..41){
                week6.add(daysInMonth[i])
            }
        }
        listWeeks.add(week1)
        listWeeks.add(week2)
        listWeeks.add(week3)
        listWeeks.add(week4)
        listWeeks.add(week5)
        listWeeks.add(week6)
        Log.v("_MainActivity", "listWeeks: $listWeeks")
        return listWeeks
    }*/

    override fun onItemWeekClickListener(day: String) {
        selectedDay = day
        Toast.makeText(this, "Day selected: $selectedDay", Toast.LENGTH_SHORT).show()
    }


}