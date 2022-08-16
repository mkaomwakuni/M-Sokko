package com.mkao.m_sokko.ui.Product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mkao.m_sokko.MainActivity
import com.mkao.m_sokko.databinding.FragmentProductsBinding

class ProductFragment : Fragment() {

    private var _binding: FragmentProductsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var callingActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //initialise variables
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        callingActivity = activity as MainActivity
        return binding.root


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun upDateCart(position: Int) {

    }
}