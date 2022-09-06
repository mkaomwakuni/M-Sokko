package com.mkao.m_sokko

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.mkao.m_sokko.ui.CheckOut.CheckoutFragment

class CheckoutAdapter(private val activity: MainActivity, private  val fragment: CheckoutFragment ) : RecyclerView.Adapter<CheckoutAdapter.ProductsViewHolder>() {
    var product = mutableListOf<ProductItem>()
    var currency:Currency? = null

    inner class ProductsViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        internal var iProductImage =itemView.findViewById<View>(R.id.productImage) as ImageView
        internal var iProductName = itemView.findViewById<View>(R.id.productName) as TextView
        internal var iProductPrice = itemView.findViewById<View>(R.id.productPrice) as TextView
        internal var iremoveFrombasket = itemView.findViewById<View>(R.id.removeFromBasketButton) as ImageButton
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
       return ProductsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.basketeditem,parent,false))
        }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val current = product[position]
        Glide.with(activity)
            .load(current.image)
            .transition(DrawableTransitionOptions.withCrossFade())
            .centerCrop()
            .override(600,600)
            .into(holder.iProductImage)
        holder.iProductName.text = current.name
        val price = if (currency?.exchangeRate ==null)
            current.priceofItem
        else current.priceofItem*currency?.exchangeRate!!

        holder.iProductPrice.text = activity.resources.getString(R.string.product_price,currency?.symbol,String.format("%.2f",price))
        holder.iremoveFrombasket.setOnClickListener{
            with(fragment) { removeProduct(current) }
        }

    }

    override fun getItemCount() = product.size
}