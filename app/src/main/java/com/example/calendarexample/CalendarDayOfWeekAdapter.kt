package com.example.calendarexample

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.calendarexample.databinding.ItemDayLayoutBinding

class CalendarDayOfWeekAdapter(
   private var listDaysOfWeek: MutableList<Int>,
   private val onItemDayClickListener: OnItemDayClickListener,
   private val daySelected: Int = 0,
   private val isCurrentMonth: Boolean = false,
   private val isCurrentYear: Boolean = false,
   private val currentDay: Int = 0,
   private val isClickable: Boolean = true
): RecyclerView.Adapter<CalendarDayOfWeekAdapter.ViewHolder>() {
    inner class ViewHolder(
        private val binding: ItemDayLayoutBinding,
        private val onItemDayClickListener: OnItemDayClickListener
    ): RecyclerView.ViewHolder(binding.root){
        @SuppressLint("NotifyDataSetChanged")
        fun bindView(day: Int) {
            binding.apply {
                if (day != 0){
                    textViewDay.text = "$day"
                }else{
                    textViewDay.text = ""
                }

                if (isCurrentMonth && isCurrentYear && currentDay != 0 && currentDay == day){
                    cardViewParent.setCardBackgroundColor(
                        ContextCompat.getColor(binding.root.context, R.color.todayColor)
                    )
                }

                root.setOnClickListener {
                    if (isClickable){
                        notifyDataSetChanged()
                        onItemDayClickListener.onItemDayClick(day)
                    }
                }

                if (daySelected == day && daySelected != 0){
                    cardViewChild.setCardBackgroundColor(
                        ContextCompat.getColor(binding.root.context, R.color.selectedDayColor)
                    )
                }else{
                    cardViewChild.setCardBackgroundColor(
                        ContextCompat.getColor(binding.root.context, R.color.white)
                    )
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
        val day: Int = listDaysOfWeek[position]
        return holder.bindView(day)
    }

    override fun getItemCount(): Int {
        return listDaysOfWeek.size
    }

    interface OnItemDayClickListener{
        fun onItemDayClick(day: Int)
    }
}