package com.mkao.m_sokko.ui.Product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.mkao.m_sokko.Currency
import com.mkao.m_sokko.MainActivity
import com.mkao.m_sokko.ProductItem
import com.mkao.m_sokko.R

class ProductAdapter (private val activity: MainActivity,private val fragment: ProductFragment):
RecyclerView.Adapter<ProductAdapter.ProductViewHolder>(){
    //will store the products object
    var products = mutableListOf<ProductItem>()

    //handling change in prices of currency
    var currency: Currency? =null

    //inner class Will initialise product xml layouts an store references of its widgets
    inner class ProductViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        internal var rProductImage = itemView.findViewById<View>(R.id.productImage) as ImageView
        internal var rProductName = itemView.findViewById<View>(R.id.productName) as TextView
        internal var rProductPrice = itemView.findViewById<View>(R.id.productPrice) as TextView
        internal var rAddToCart = itemView.findViewById<View>(R.id.addToCart) as ImageButton

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        //assigning adapter to use item Layout
      return ProductViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false))
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
       val current = products[position]
        Glide.with((activity))
            .load(current.image)
            .transition(DrawableTransitionOptions.withCrossFade())
            .centerCrop()
            .override(600,600)
            .into(holder.rProductImage)
        holder.rProductName.text = current.name

        //price of the product
        val price = if (currency?.exchangeRate ==null) current.priceofItem //price
        else current.priceofItem * currency?.exchangeRate!!
        holder.rProductPrice.text = activity.resources.getString(R.string.product_price,currency?.symbol,String.format("%.2f",price))

        if (current.inCart){
            holder.rAddToCart.setBackgroundColor(ContextCompat.getColor(activity,android.R.color.holo_green_light))
        }
        holder.rAddToCart.setOnClickListener {
            fragment.upDateCart(position)
        }
    }

    override fun getItemCount() = products.size
}