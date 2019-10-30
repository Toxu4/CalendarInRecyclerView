package com.example.calendarinrecyclerview

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_calendar.view.*

class SecondCalendarItem constructor(private var currentDay: Int) : Item(), ISelectionNotifier, ISelectionCallback {

    private var selectionListeners = mutableMapOf<Int, ISelectionListener>()

    override fun getLayout(): Int = R.layout.item_calendar

    override fun bind(viewHolder: ViewHolder, position: Int) {
        with(viewHolder.itemView) {
            recyclerView.adapter = GroupAdapter<com.xwray.groupie.ViewHolder>()
                .apply {
                    repeat(30) {
                        add(
                            SecondDayItem(
                                it,
                                it == currentDay,
                                this@SecondCalendarItem,
                                this@SecondCalendarItem
                            )
                        )
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

    override fun registerSelectionListener(day: Int, listener: ISelectionListener) {
        selectionListeners[day] = listener
    }

    override fun unregisterSelectionListener(day: Int) {
        selectionListeners.remove(day)
    }

    override fun onSelected(day: Int) {
        selectionListeners[currentDay]?.onSelection(false)
        currentDay = day
        selectionListeners[currentDay]?.onSelection(true)
    }
}