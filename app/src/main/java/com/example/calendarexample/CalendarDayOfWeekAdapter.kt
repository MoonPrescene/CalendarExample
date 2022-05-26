package com.example.calendarexample

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.calendarexample.databinding.ItemDayLayoutBinding

class CalendarDayOfWeekAdapter(
   private var listDaysOfWeek: MutableList<String>,
   private val onItemDayClickListener: OnItemDayClickListener
): RecyclerView.Adapter<CalendarDayOfWeekAdapter.ViewHolder>() {
    class ViewHolder(
        private val binding: ItemDayLayoutBinding,
        private val onItemDayClickListener: OnItemDayClickListener
    ): RecyclerView.ViewHolder(binding.root){
        fun bindView(day: String) {
            binding.apply {
                textViewDay.text = day
                cardView.setOnClickListener {
                    onItemDayClickListener.onItemDayClick(day)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemDayLayoutBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.item_day_layout,
            parent,
            false
        )

        return ViewHolder(binding, onItemDayClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val day: String = listDaysOfWeek[position]
        return holder.bindView(day)
    }

    override fun getItemCount(): Int {
        return listDaysOfWeek.size
    }

    interface OnItemDayClickListener{
        fun onItemDayClick(day: String)
    }
}