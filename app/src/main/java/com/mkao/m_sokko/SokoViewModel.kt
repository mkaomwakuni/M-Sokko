package com.mkao.m_sokko

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.math.BigDecimal
import java.math.RoundingMode

class SokoViewModel:ViewModel() {
    //This viewModel stores the list of items and makes them accessible throughout the app
    var products = MutableLiveData<List<ProductItem>>()
    var currency = MutableLiveData<Currency>()
    var orderTotal = MutableLiveData(0.00)

    fun calculateOrderSum() {
        val basket = products.value?.filter {
            p -> p.inCart
        }?: listOf()
        var total = 0.00
        for (p in basket) total += p.priceofItem
        if (currency.value!=null)
            total*= currency.value?.exchangeRate?:1.00

        orderTotal.value = BigDecimal(total).setScale(2,RoundingMode.HALF_EVEN).toDouble()
    }
}