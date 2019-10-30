package com.example.calendarinrecyclerview

import android.graphics.Color
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_day.view.*

interface ISelectionListener {
    fun onSelection(selected: Boolean)
}

interface ISelectionNotifier {
    fun registerSelectionListener(day: Int, listener: ISelectionListener)
    fun unregisterSelectionListener(day: Int)
}

interface ISelectionCallback {
    fun onSelected(day: Int)
}

class SecondDayItem(
    private val day: Int,
    private var selected: Boolean,
    private val selectionNotifier: ISelectionNotifier,
    private val selectionCallback: ISelectionCallback
) : Item() {
    private var boundViewHolder : ViewHolder? = null

    private val selectionListener = object: ISelectionListener {
        override fun onSelection(selected: Boolean) {
            val wasSelected = this@SecondDayItem.selected
            this@SecondDayItem.selected = selected

            boundViewHolder?.let {
                if (wasSelected != selected){
                    updateView(it.itemView)
                }
            }
        }
    }

    private val clickListener = View.OnClickListener { selectionCallback.onSelected(day) }

    override fun getLayout(): Int = R.layout.item_day

    override fun bind(viewHolder: ViewHolder, position: Int) {
        boundViewHolder = viewHolder

        selectionNotifier.registerSelectionListener(day, selectionListener)
        viewHolder.itemView.day.setOnClickListener(clickListener)

        updateView(viewHolder.itemView)
    }

    override fun unbind(viewHolder: ViewHolder) {
        boundViewHolder = null

        selectionNotifier.unregisterSelectionListener(day)
        viewHolder.itemView.day.setOnClickListener(null)

        super.unbind(viewHolder)
        super.unbind(viewHolder)
    }

    private fun updateView(itemView: View) {
        with(itemView){
            day.text = this@SecondDayItem.day.toString()
            day.setTextColor(if (selected) Color.BLUE else Color.BLACK)
        }
    }
}