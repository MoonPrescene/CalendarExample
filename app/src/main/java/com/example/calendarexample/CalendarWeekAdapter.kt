package com.example.calendarexample

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calendarexample.databinding.ItemWeekLayoutBinding

class CalendarWeekAdapter(
    private var listWeek: MutableList<MutableList<String>>,
    private val onItemWeekListener: OnItemWeekListener
): RecyclerView.Adapter<CalendarWeekAdapter.ViewHolder>(){
    class ViewHolder(
        private val binding: ItemWeekLayoutBinding,
        private val onItemWeekListener: OnItemWeekListener
    ): RecyclerView.ViewHolder(binding.root), CalendarDayOfWeekAdapter.OnItemDayClickListener{

        private lateinit var calendarDayOfWeekAdapter: CalendarDayOfWeekAdapter
        private var dayInWeek: String = ""

        fun bindView(week: MutableList<String>){
            calendarDayOfWeekAdapter = CalendarDayOfWeekAdapter(week, this)
            binding.recyclerViewDayWeek.apply {
                adapter = calendarDayOfWeekAdapter
                layoutManager = LinearLayoutManager(context,
                    LinearLayoutManager.HORIZONTAL,
                    false)
            }

        }

        override fun onItemDayClick(day: String) {
            dayInWeek = day
            onItemWeekListener.onItemWeekClickListener(dayInWeek)
        }
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
        fun onItemWeekClickListener(day: String)
    }
}