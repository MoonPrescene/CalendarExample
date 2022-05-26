package com.example.calendarexample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.calendarexample.databinding.ItemCalendarLayoutBinding

class CalendarAdapter(
    private val listDayOfWeek: MutableList<String>
): RecyclerView.Adapter<CalendarAdapter.ViewHolder>() {
    class ViewHolder(
        private val binding: ItemCalendarLayoutBinding
    ): RecyclerView.ViewHolder(binding.root){
        fun bindView(day: String) {
            binding.apply {
                if (day == "") {
                    cardView.visibility = View.GONE
                }else{
                    textViewDay.text = day
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemCalendarLayoutBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.item_calendar_layout,
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val day: String = listDayOfWeek[position]
        return holder.bindView(day)
    }

    override fun getItemCount(): Int {
        return listDayOfWeek.size
    }
}