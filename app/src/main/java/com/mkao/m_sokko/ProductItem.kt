package com.mkao.m_sokko

data class ProductItem(
    var image:Int,
    var name:String,
    var priceofItem:Double,
    var inCart:Boolean = false
)
