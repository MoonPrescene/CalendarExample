package com.example.calendarexample

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calendarexample.databinding.ItemWeekLayoutBinding
import java.util.*

class CalendarWeekAdapter(
    private var listWeek: MutableList<MutableList<Int>>,
    private val onItemWeekListener: OnItemWeekListener,
    var daySelected: Int = 0,
    private var isCurrentMonth: Boolean = true,
    private var isCurrentYear: Boolean = true,
    private var currentDay: Int = 0,
    private var isClickable: Boolean = true,
    private var month: Int = 0,
    private var year: Int = 0
): RecyclerView.Adapter<CalendarWeekAdapter.ViewHolder>(){

    inner class ViewHolder(
        private val binding: ItemWeekLayoutBinding,
        private val onItemWeekListener: OnItemWeekListener
    ): RecyclerView.ViewHolder(binding.root), CalendarDayOfWeekAdapter.OnItemDayClickListener{

        private lateinit var calendarDayOfWeekAdapter: CalendarDayOfWeekAdapter
        fun bindView(daysOfWeek: MutableList<Int>){
            calendarDayOfWeekAdapter = CalendarDayOfWeekAdapter(
                daysOfWeek,
                this,
                daySelected,
                isCurrentMonth,
                isCurrentYear,
                currentDay = currentDay,
                isClickable
            )

            val linearLayoutManager = object : LinearLayoutManager(
                binding.root.context,
                HORIZONTAL,
                false
            ){
                override fun checkLayoutParams(lp: RecyclerView.LayoutParams): Boolean {
                    lp.width = width / 7
                    return true
                }
            }
            binding.recyclerViewDayWeek.apply {
                adapter = calendarDayOfWeekAdapter
                layoutManager = linearLayoutManager
            }
        }

        @SuppressLint("NotifyDataSetChanged")
        override fun onItemDayClick(day: Int) {
            if (day == 0){
                return
            }
            daySelected = day
            notifyDataSetChanged()

            onItemWeekListener.onDaySelected(
                getCurrentDateFrom(day, month, year)
            )
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setMonthYear(m: Int, y: Int){
        month = m
        year = y
        isCurrentMonth = m == getCurrentMonth()
        isCurrentYear = y == getCurrentYear()
        currentDay = getCurrentDay()
        if (isCurrentYear && isCurrentMonth){
            daySelected = currentDay
            onItemWeekListener.onScrollToWeekSelected(getCurrentWeekOfMonth() - 1)
        }else{
            daySelected = 1
            onItemWeekListener.onScrollToWeekSelected(0)
        }

        onItemWeekListener.onDaySelected(
            getCurrentDateFrom(daySelected, month, year)
        )
        listWeek = getWeekListOf(m, y)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setDayMonthYear(d: Int, m: Int, y: Int){
        month = m
        year = y
        isCurrentMonth = m == getCurrentMonth()
        isCurrentYear = y == getCurrentYear()
        currentDay = getCurrentDay()
        daySelected = d
        listWeek = getWeekListOf(m, y)

        var week = 0
        loop@ for (i in (0 until listWeek.size)) {
            if (listWeek[i].contains(d)){
                week = i
                break@loop
            }
        }
        onItemWeekListener.onScrollToWeekSelected(week)
        notifyDataSetChanged()
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setClickable(isClickable: Boolean){
        this.isClickable = isClickable
        notifyDataSetChanged()
    }

    private fun getWeekListOf(month: Int, year: Int): MutableList<MutableList<Int>>{
        val dayListInCalendar: MutableList<Int> = mutableListOf()
        val firstDate = firstDateOf(month, year)
        val firstDay = getWeekDayOf(firstDate)
        val numberDayOfMonth = getNumberDayOfMonth(month, year)

        var numberItemOfCalendar = firstDay + numberDayOfMonth
        numberItemOfCalendar = if (numberItemOfCalendar <= 28){
            28
        }else if (numberItemOfCalendar <= 35){
            35
        }else{
            42
        }

        (0 until numberItemOfCalendar).forEach {
            if (it in firstDay until numberDayOfMonth + firstDay){
                dayListInCalendar.add(it - firstDay + 1)
            }else {
                dayListInCalendar.add(0)
            }
        }

        val weekList: MutableList<MutableList<Int>> = mutableListOf()
        (1..numberItemOfCalendar / 7).forEach {
            weekList.add(
                dayListInCalendar.subList((it - 1) * 7, it * 7)
            )
        }

        return weekList
    }

    private fun getNumberDayOfMonth(month: Int, year: Int): Int{
        val numberDayOfMonthsList = listOf(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)
        val numberDayOfMonthsList2 = listOf(31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)

        return if (year % 4 == 0 && year % 100 != 0){
            numberDayOfMonthsList2[month - 1]
        }else{
            numberDayOfMonthsList[month - 1]
        }
    }

    private fun firstDateOf(month: Int, year: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month - 1)
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.set(Calendar.HOUR, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)

        return calendar.time
    }

    private fun getWeekDayOf(date: Date): Int{
        val calendar = Calendar.getInstance()
        calendar.time = date

        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        return if (dayOfWeek - 2 == -1){
            6
        }else{
            dayOfWeek - 2
        }
    }

    private fun getCurrentMonth(): Int {
        val calendar = Calendar.getInstance()
        return calendar.get(Calendar.MONTH) + 1
    }

    private fun getCurrentYear(): Int {
        val calendar = Calendar.getInstance()
        return calendar.get(Calendar.YEAR)
    }

    private fun getCurrentDay(): Int {
        val calendar = Calendar.getInstance()
        return calendar.get(Calendar.DAY_OF_MONTH)
    }

    private fun getCurrentWeekOfMonth(): Int{
        val calendar = Calendar.getInstance()
        return calendar.get(Calendar.WEEK_OF_MONTH)
    }

    private fun getCurrentDateFrom(day: Int, month: Int, year: Int): Date{
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month - 1)
        calendar.set(Calendar.DAY_OF_MONTH, day)
        calendar.set(Calendar.HOUR, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)

        return calendar.time
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemWeekLayoutBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.item_week_layout,
            parent,
            false
        )

        return ViewHolder(binding, onItemWeekListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val week = listWeek[position]
        return holder.bindView(week)
    }

    override fun getItemCount(): Int {
        return listWeek.size
    }

    interface OnItemWeekListener{
        fun onScrollToWeekSelected(index: Int)
        fun onDaySelected(date: Date)
    }
}