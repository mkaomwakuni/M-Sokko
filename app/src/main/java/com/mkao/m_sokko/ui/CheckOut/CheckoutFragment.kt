package com.mkao.m_sokko.ui.CheckOut

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.mkao.m_sokko.MainActivity
import com.mkao.m_sokko.ProductItem
import com.mkao.m_sokko.SokoViewModel
import com.mkao.m_sokko.databinding.FragmentCheckoutBinding
import com.mkao.m_sokko.databinding.FragmentNotificationsBinding

class CheckoutFragment : Fragment() {

    private var _binding: FragmentCheckoutBinding? = null
    private val sokoViewModel:SokoViewModel by activityViewModels()
    private lateinit var callingActivity: MainActivity

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val checkoutViewModel =
            ViewModelProvider(this).get(CheckoutViewModel::class.java)

        _binding = FragmentCheckoutBinding.inflate(inflater, container, false)
        callingActivity as  MainActivity
        val root: View = binding.root

        val textView: TextView = binding.textNotifications
        checkoutViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun revomeProduct(current: ProductItem) {

    }
}