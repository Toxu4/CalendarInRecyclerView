package com.example.calendarinrecyclerview

import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_calendar.view.*

class SecondCalendarItem constructor(private var currentDay: Int): Item() {
    private var liveCurrentDay: MutableLiveData<Int> = MutableLiveData(currentDay)

    override fun getLayout(): Int = R.layout.item_calendar

    override fun bind(viewHolder: ViewHolder, position: Int) {
        with(viewHolder.itemView) {
            recyclerView.adapter = GroupAdapter<com.xwray.groupie.ViewHolder>().apply {
                repeat(30) {
                    add(SecondDayItem(it, liveCurrentDay))
                }
            }
            recyclerView.layoutManager =
                LinearLayoutManager(
                    this.context,
                    RecyclerView.HORIZONTAL,
                    false
                )
        }
    }
}