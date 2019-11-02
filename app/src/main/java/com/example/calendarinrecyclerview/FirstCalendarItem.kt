package com.example.calendarinrecyclerview

import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_calendar.view.*

class FirstCalendarItem constructor(private var currentDay: Int) : Item() {
    private var liveCurrentDay: SmartMutableLiveData<Int> = SmartMutableLiveData(currentDay)

    override fun getLayout(): Int = R.layout.item_calendar

    override fun bind(viewHolder: ViewHolder, position: Int) {
        with(viewHolder.itemView) {
            recyclerView.adapter =
                GroupAdapter<com.xwray.groupie.ViewHolder>()
                    .apply {
                        repeat(30) { add(FirstDayItem(it, liveCurrentDay)) }
                    }
            recyclerView.layoutManager =
                androidx.recyclerview.widget.LinearLayoutManager(
                    this.context,
                    androidx.recyclerview.widget.RecyclerView.HORIZONTAL,
                    false
                )
        }
    }
}