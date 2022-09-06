package com.mkao.m_sokko

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.TextHttpResponseHandler
import com.mkao.m_sokko.databinding.ActivityMainBinding
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val sokoViewModel: SokoViewModel by viewModels() //viewModel Initialised
    private val defaultCurrency = "GBP"
    private var exchangeData: JSONObject? = null
    private var selectedCurrency: Currency? = null
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM)
        getSupportActionBar()?.setCustomView(R.layout.actionbarcustom)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        getCurrencyData()
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

        val carrots = ProductItem(R.drawable.carrot, "Carrorts", 20.00)
        val sukumaiki = ProductItem(R.drawable.sukumaiki, "Sukumaiki", 10.0)
        val grapes = ProductItem(R.drawable.strawberry, "Grapes", 10.00)
        val items = listOf(carrots, sukumaiki, grapes)
        sokoViewModel.products.value = items
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (exchangeData ==null){
            Toast.makeText(this,resources.getString(R.string.exchange_data_unavailable),Toast.LENGTH_SHORT).show()
            getCurrencyData()
            }else {
                when(item.itemId){
                    //each currency is set here
                    R.id.currency_Kshs -> setCurrency("KSHS")
                    R.id.currency_TZ -> setCurrency("TKSHS")
                }
        }
        when(item.itemId){
            R.id.abt -> {
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getCurrencyData(): JSONObject? {
        val client = AsyncHttpClient()
        //paste your api key here
        client.get("http.example.com $defaultCurrency", object : TextHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseString: String?
            ) {
                exchangeData = JSONObject(responseString)
                val currencyPreference =
                    sharedPreferences.getString("currency", defaultCurrency) ?: defaultCurrency
                setCurrency(currencyPreference)
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseString: String?,
                throwable: Throwable?
            ) {
                Toast.makeText(
                    this@MainActivity,
                    resources.getString(R.string.exchange_data_unavailable),
                    Toast.LENGTH_SHORT
                ).show()
                setCurrency(defaultCurrency)
            }
        })
        return null
    }

    private fun setCurrency(defaultCurrency: String) {
        val exchangeRate = exchangeData?.getJSONObject("conversion_rates")?.getDouble(isoCode)
        //def base currency here
        var currency = Currency(defaultCurrency,"Ksh",null)
        if (exchangeRate!=null){
            when(isoCode){
                //define each additional currency for the store
                "Kshs" -> currency = Currency(isoCode,"Kshs",exchangeRate)
                "USD" -> currency = Currency(isoCode,"$",exchangeRate)
            }
        }
        sharedPreferences.edit().apply{
            putString("currency",isoCode)
            apply()
        }
        selectedCurrency=currency
        sokoViewModel.currency.value = currency
        sokoViewModel.calculateOrderSum()
    }

    fun intiatePay() {
        TODO("Not yet implemented")
    }
}