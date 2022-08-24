package com.mkao.m_sokko.ui.Product

import android.icu.number.Precision.currency
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.mkao.m_sokko.MainActivity
import com.mkao.m_sokko.SokoViewModel
import com.mkao.m_sokko.databinding.FragmentProductsBinding

class ProductFragment : Fragment() {

    private var _binding: FragmentProductsBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    //integrating products fragment with recyclerview
    private val sokoViewModel:SokoViewModel by activityViewModels ()
    private lateinit var productAdapter: ProductAdapter

    private lateinit var callingActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        //initialise variables
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        callingActivity = activity as MainActivity
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productAdapter = ProductAdapter(callingActivity,this)
        binding.recyclerViewProducts.layoutManager = LinearLayoutManager(activity)
        binding.recyclerViewProducts.itemAnimator = DefaultItemAnimator()
        binding.recyclerViewProducts.adapter = productAdapter

        productAdapter.products = sokoViewModel.products.value?.toMutableList()?: mutableListOf()
        productAdapter.notifyItemRangeInserted(0,productAdapter.products.size)

        sokoViewModel.currency.observe((viewLifecycleOwner,{currency ->
            currency?.let {
                binding.recyclerViewProducts.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
                //DETECT THE SELECTED CURRENCY
                if (ProductAdapter.currency== null || it.symbol!=productAdapter.currency?.symbol){
                    productAdapter.currency = it
                    productAdapter.notifyItemRangeInserted(0,productAdapter.itemCount)
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun upDateCart(position: Int) {
        val products = productAdapter.products.toMutableList()
        products[position].inCart
        productAdapter.products//Call notifyItemChanged to update cart
        productAdapter.notifyItemChanged(position)
        sokoViewModel.products.value = products
        sokoViewModel.calculateOrderSum()
    }
}