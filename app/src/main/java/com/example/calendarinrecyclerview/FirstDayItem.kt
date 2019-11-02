package com.example.calendarinrecyclerview

import android.graphics.Color
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_day.view.*

class FirstDayItem(private val day: Int, private val currentDay: SmartMutableLiveData<Int>) : Item() {
    private var selected = currentDay.value == day
    private var boundViewHolder: ViewHolder? = null

    private val currentDayObserver =
        Observer<Int> { newCurrentDay ->
            selected = newCurrentDay == day

            Log.e("*", "Day $day ${if (selected) "selected" else "unselected"}")

            boundViewHolder?.let {
                updateView(it.itemView)
            }
        }

    private val clickListener = View.OnClickListener {
        currentDay.setValue(day)
    }

    override fun getLayout(): Int = R.layout.item_day

    override fun bind(viewHolder: ViewHolder, position: Int) {
        boundViewHolder = viewHolder

        currentDay.observeValueForever(day, currentDayObserver)
        viewHolder.itemView.day.setOnClickListener(clickListener)

        updateView(viewHolder.itemView)
    }

    override fun unbind(viewHolder: ViewHolder) {
        boundViewHolder = null

        currentDay.removeValueObserver(currentDayObserver)
        viewHolder.itemView.day.setOnClickListener(null)

        super.unbind(viewHolder)
    }

    private fun updateView(itemView: View) =
        with(itemView) {
            day.text = this@FirstDayItem.day.toString()
            day.setTextColor(if (selected) Color.RED else Color.BLACK)
        }
}

