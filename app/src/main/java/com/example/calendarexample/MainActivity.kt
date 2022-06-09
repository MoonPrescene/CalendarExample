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
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity(),
    CalendarWeekAdapter.OnItemWeekListener{

    private lateinit var binding: ActivityMainBinding
    private lateinit var calendarAdapter: CalendarAdapter
    private lateinit var selectedDate: LocalDate
    private var selectedDay: String = ""
    private var thisMonthCheck: Boolean = true
    private lateinit var calendarWeekAdapter: CalendarWeekAdapter
    private lateinit var today: LocalDate
    private var monthOfToday: Int = 0
    private var positionOfToday: Int = 0
    private val snapHelper: SnapHelper = PagerSnapHelper()
    private var month = 4

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
//        selectedDate = LocalDate.now()
//        today = LocalDate.now()
//        setMonthView()
//        setListWeekView()
//        calendarWeekAdapter.daySelected = dayFromDate(today)
//        scrollToWeek(positionOfToday)
//        binding.apply {
//            nextMonth.setOnClickListener {
//                scrollToNextMonth()
//            }
//            previousMonth.setOnClickListener {
//                scrollToPreviousMonth()
//            }
//        }

        binding.nextMonth.setOnClickListener {
            month += 1
            calendarWeekAdapter.setDayMonthYear(10, month, 2022)
        }
        setListWeekView()
        calendarWeekAdapter.setMonthYear(month, 2022)
        calendarWeekAdapter.setClickable(false)
    }

    override fun onDaySelected(date: Date) {
        binding.nextMonth.text = date.toString()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onScrollToWeekSelected(index: Int) {
        binding.recyclerViewWeek.scrollToPosition(index)
    }

//    @SuppressLint("NewApi", "NotifyDataSetChanged")
//    private fun scrollToPreviousMonth() {
//        selectedDate = selectedDate.minusMonths(1)
//        monthOfToday = monthFromDate(today)
//        thisMonthCheck = monthFromDate(selectedDate) == monthOfToday
//        setMonthView()
//        setListWeekView()
//        if (monthFromDate(selectedDate) == monthOfToday){
//            calendarWeekAdapter.daySelected = dayFromDate(today)
//            Toast.makeText(this, "Equal: $thisMonthCheck", Toast.LENGTH_SHORT).show()
//            scrollToWeek(positionOfToday)
//        }else{
//            calendarWeekAdapter.daySelected = "1"
//            Toast.makeText(this, "monthSelectedDate: monthOfToday = ${monthFromDate(selectedDate)}: $monthOfToday, $thisMonthCheck", Toast.LENGTH_SHORT).show()
//        }
//        calendarWeekAdapter.notifyDataSetChanged()
//        calendarAdapter.notifyDataSetChanged()
//    }

//    @SuppressLint("NewApi", "NotifyDataSetChanged")
//    private fun scrollToNextMonth() {
//        selectedDate = selectedDate.plusMonths(1)
//        monthOfToday = monthFromDate(today)
//        thisMonthCheck = monthFromDate(selectedDate) == monthOfToday
//        setMonthView()
//        setListWeekView()
//        if (monthFromDate(selectedDate) == monthOfToday){
//            calendarWeekAdapter.daySelected = dayFromDate(today)
//            scrollToWeek(positionOfToday)
//        }else{
//            calendarWeekAdapter.daySelected = "1"
//        }
//        calendarWeekAdapter.notifyDataSetChanged()
//        calendarAdapter.notifyDataSetChanged()
//    }
//
//    private fun setMonthView() {
//        val daysInMonth: MutableList<String> = daysInMonthArray(selectedDate)
//        calendarAdapter = CalendarAdapter(daysInMonth)
//        binding.recyclerViewMonth.apply {
//            adapter = calendarAdapter
//            layoutManager = GridLayoutManager(context, 7)
//        }
//    }
//
//
    private fun setListWeekView() {
        calendarWeekAdapter = CalendarWeekAdapter(mutableListOf(), this)
        binding.recyclerViewWeek.apply {
            adapter = calendarWeekAdapter
            layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL,
                false)
        }
        snapHelper.attachToRecyclerView(binding.recyclerViewWeek)
    }
//
//
//    @SuppressLint("NewApi")
//    private fun daysInMonthArray(date: LocalDate): MutableList<String> {
//        val dayInMonthArray = mutableListOf<String>()
//        val yearMonth = YearMonth.from(date)
//        val daysInMonth = yearMonth.lengthOfMonth()
//        val firstOfMonth = selectedDate.withDayOfMonth(1)
//        val dayOfWeek = firstOfMonth.dayOfWeek.value
//        Log.v("_MainActivity", "$dayOfWeek")
//        for (i in 1..42){
//            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek){
//                dayInMonthArray.add("")
//            }else{
//                dayInMonthArray.add(String.format("${i - dayOfWeek}"))
//            }
//        }
//
//        return checkMonth(dayInMonthArray)
//    }
//
//    private fun checkMonth(daysInMonth: MutableList<String>): MutableList<String>{
//        if (daysInMonth[35] == ""){
//            daysInMonth.removeAll(daysInMonth.subList(35, daysInMonth.size - 1))
//        }
////        daysInMonth.removeAt(0)
//
//        return daysInMonth
//    }
//
//    /*private fun<T> removeRange(list: MutableList<T>, from: Int, end: Int){
//        list.removeAll(list.subList(from, end + 1))
//    }*/
//
//    private fun convertToListWeeks(daysInMonth: MutableList<String>): MutableList<MutableList<String>>{
//        val listWeeks: MutableList<MutableList<String>> = mutableListOf()
//        val week1: MutableList<String> = mutableListOf()
//        val week2: MutableList<String> = mutableListOf()
//        val week3: MutableList<String> = mutableListOf()
//        val week4: MutableList<String> = mutableListOf()
//        val week5: MutableList<String> = mutableListOf()
//        val week6: MutableList<String> = mutableListOf()
//
//        Log.v("_MainActivity", "daysInMonth: $daysInMonth")
//
//        for (i in daysInMonth.indices){
//            if (i in 0..6){
//                week1.add(daysInMonth[i])
//            }
//            if (i in 7..13){
//                week2.add(daysInMonth[i])
//            }
//            if (i in 14..20){
//                week3.add(daysInMonth[i])
//            }
//            if (i in 21..27){
//                week4.add(daysInMonth[i])
//            }
//            if (i in 28..34){
//                week5.add(daysInMonth[i])
//            }
//            if (i in 35..41){
//                week6.add(daysInMonth[i])
//            }
//        }
//        listWeeks.add(week1)
//        listWeeks.add(week2)
//        listWeeks.add(week3)
//        listWeeks.add(week4)
//        listWeeks.add(week5)
//        listWeeks.add(week6)
//        positionOfToday = checkDayInWeek(listWeeks, dayFromDate(today))
//        return listWeeks
//    }
//
//    @SuppressLint("NewApi")
//    private fun monthYearFromDate(date: LocalDate): String{
//        val formatter = DateTimeFormatter.ofPattern("MM, yyyy")
//        return date.format(formatter)
//    }
//
//    @SuppressLint("NewApi")
//    private fun dayFromDate(date: LocalDate): String{
//        val formatter = DateTimeFormatter.ofPattern("dd")
//        return date.format(formatter)
//    }
//
//    @SuppressLint("NewApi")
//    private fun monthFromDate(date: LocalDate): Int{
//        val formatter = DateTimeFormatter.ofPattern("MM")
//        val month = date.format(formatter)
//        return month.toInt()
//    }
//
//    @SuppressLint("NewApi")
//    override fun onItemWeekClickListener(day: String) {
//        Toast.makeText(this, "${monthYearFromDate(selectedDate)}-$day", Toast.LENGTH_SHORT).show()
//        calendarWeekAdapter.daySelected = day
//        val i = day.toInt()
//        val date = selectedDate.withDayOfMonth(i)
//        val weekDayOfSelectedDay = date.dayOfWeek.value
//        binding.nextMonth.text = setDayOfWeek(weekDayOfSelectedDay, i, monthYearFromDate(selectedDate))
//    }
//
//    private fun setDayOfWeek(dayOfWeek: Int, day: Int, monthYear: String): String{
//        val strDayMonthYear = " ngày $day tháng $monthYear"
//        var strWeekDay = ""
//        when(dayOfWeek){
//            1->{
//                strWeekDay = "Thứ Hai"
//            }
//            2->{
//                strWeekDay = "Thứ Ba"
//            }
//            3->{
//                strWeekDay = "Thứ Tư"
//            }
//            4->{
//                strWeekDay = "Thứ Năm"
//            }
//            5->{
//                strWeekDay = "Thứ Sáu"
//            }
//            6->{
//                strWeekDay = "Thứ Bảy"
//            }
//            7->{
//                strWeekDay = "Chủ nhật"
//            }
//        }
//
//        return strWeekDay + strDayMonthYear
//    }
//
//    @SuppressLint("NotifyDataSetChanged")
//    private fun scrollToWeek(index: Int){
//        binding.recyclerViewWeek.scrollToPosition(index)
//        calendarWeekAdapter.notifyDataSetChanged()
//    }
//
//    private fun checkDayInWeek(month: MutableList<MutableList<String>>, today: String): Int {
//        for (week in month){
//            if (checkWeekHaveDay(week, today)){
//                return month.indexOf(week)
//            }
//        }
//        return 0
//    }
//
//    private fun checkWeekHaveDay(week: MutableList<String>, today: String): Boolean{
//        for (days in week){
//            if (today == days){
//                return true
//            }
//        }
//        return false
//    }

}