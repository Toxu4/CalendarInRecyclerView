package com.example.calendarinrecyclerview

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

// Прототип!
class SmartMutableLiveData<T>(value: T) : MutableLiveData<T>(value) {

    private val wrappers = mutableListOf<ObserverWrapper<T>>()

    fun observeValueForever(valueToTrack: T, observer: Observer<in T>) {
        super.observeForever(
            ObserverWrapper(value, valueToTrack, observer)
                .apply {
                    wrappers.add(this)
                })
    }

    fun observeValue(owner: LifecycleOwner, valueToTrack: T, observer: Observer<in T>) {
        super.observe(
            owner,
            ObserverWrapper(value, valueToTrack, observer, owner)
                .apply {
                    wrappers.add(this)
                })
    }

    override fun removeObserver(observer: Observer<in T>) {
        wrappers
            .firstOrNull { it.innerObserver == observer }
            ?.let {
                wrappers.remove(it)
                super.removeObserver(it)
            }
    }

    override fun removeObservers(owner: LifecycleOwner) {
        wrappers
            .filter { it.owner == owner }
            .forEach { wrappers.remove(it) }

        super.removeObservers(owner)
    }

    private class ObserverWrapper<T>(
        private var currentValue: T?,
        private val trackValue: T,
        val innerObserver: Observer<in T>,
        val owner: LifecycleOwner? = null
    ): Observer<T> {

        init {
            if (currentValue?.equals(trackValue) != true)
            {
                innerObserver.onChanged(currentValue)
            }
        }

        override fun onChanged(newValue: T) {
            if (
                (currentValue?.equals(trackValue) == true)
                ||
                (newValue?.equals(trackValue) == true)
            ){
                innerObserver.onChanged(newValue)
            }

            currentValue = newValue
        }
    }
}