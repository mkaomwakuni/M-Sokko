package com.mkao.m_sokko

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SokoViewModel:ViewModel() {
    //This viewModel stores the list of items and makes them accessible throughout the app
    var products = MutableLiveData<List<ProductItem>>()
    var currency = MutableLiveData<Currency>()

    fun calculateOrderSum() {
        TODO("Not yet implemented")
    }
}