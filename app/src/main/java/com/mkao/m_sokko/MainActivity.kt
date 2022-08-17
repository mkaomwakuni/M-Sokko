package com.mkao.m_sokko

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mkao.m_sokko.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val sokoViewModel:SokoViewModel by viewModels() //viewModel Initialised

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM)
        getSupportActionBar()?.setCustomView(R.layout.actionbarcustom)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController by lazy {
            val navHostFragment = supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
            navHostFragment.navController
        }
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_product, R.id.navigation_dashboard, R.id.navigation_checkout
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val carrots = ProductItem(R.drawable.carrot,"Carrorts",20.00)
        val sukumaiki = ProductItem(R.drawable.sukumaiki,"Sukumaiki",10.0)
        val grapes = ProductItem(R.drawable.strawberry,"Grapes",10.00)
        val items = listOf(carrots,sukumaiki,grapes)
        sokoViewModel.products.value =items
    }
}