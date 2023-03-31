package com.patronus

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Pramod on 3/19/23.
 */
@AndroidEntryPoint
class MainActivity : MyBaseActivity() {

    var navController: NavController? = null
    var navigationBar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupNavigation()
    }

    override fun init(savedInstanceState: Bundle?) {

    }

    override fun getResourceId(): Int {
        return R.layout.activity_main
    }

    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navigation_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        navigationBar = findViewById<Toolbar>(R.id.navigationBar)
        val appBarConfiguration = AppBarConfiguration(navController!!.graph)
        findViewById<Toolbar>(R.id.navigationBar).setupWithNavController(navController!!, appBarConfiguration)

        setSupportActionBar(navigationBar)
    }
}