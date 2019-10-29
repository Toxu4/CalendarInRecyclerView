package com.example.calendarinrecyclerview

import android.graphics.Color
import androidx.lifecycle.MutableLiveData
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_day.view.*

class SecondDayItem(private val day: Int, val currentDay: MutableLiveData<Int>) : Item() {
    private var selected = currentDay.value == day

    override fun getLayout(): Int = R.layout.item_day

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.day.text = this.day.toString()
        viewHolder.itemView.day.setTextColor(if (selected) Color.BLUE else Color.BLACK)
    }
}