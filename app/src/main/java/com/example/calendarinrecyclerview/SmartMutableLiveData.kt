package com.example.calendarinrecyclerview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

// Прототип!
class SmartMutableLiveData<T>(value: T) : MutableLiveData<T>(value) {

    private val innerLiveData = mutableMapOf<T, MutableLiveData<T>>()

    fun observeValueForever(valueToTrack: T, observer: Observer<in T>) {
        innerLiveData
            .getOrPut(valueToTrack, { MutableLiveData<T>(value) })
            .observeForever(observer)
    }

    fun removeValueObserver(observer: Observer<in T>) {
        innerLiveData.forEach {
            it.value.removeObserver(observer)
        }
    }

    override fun setValue(value: T) {

        innerLiveData[this.value]?.value = value

        super.setValue(value)

        innerLiveData[this.value]?.value = value
    }
}