package com.mkao.m_sokko.ui.CheckOut

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CheckoutViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is CheckOut Fragment"
    }
    val text: LiveData<String> = _text
}