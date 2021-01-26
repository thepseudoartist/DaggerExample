package com.daggerexample.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.daggerexample.BaseActivity
import com.daggerexample.R
import com.google.android.material.navigation.NavigationView

private const val TAG = "MainActivity"

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val drawerLayout: DrawerLayout by lazy {
        findViewById(R.id.drawer_layout)
    }

    private val navigationView: NavigationView by lazy {
        findViewById(R.id.nav_view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Toast.makeText(this, TAG, Toast.LENGTH_LONG).show()
        init()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                sessionManager.logout()
                return true
            }
            android.R.id.home -> {
                return if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                } else false
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_profile -> {
                val navOptions = NavOptions.Builder().setPopUpTo(R.id.main, true).build()

                Navigation.findNavController(this, R.id.nav_host_fragment)
                    .navigate(R.id.profile_screen, null, navOptions)
            }

            R.id.nav_posts -> if (isValidDestination(R.id.post_screen)) Navigation.findNavController(
                this,
                R.id.nav_host_fragment
            ).navigate(R.id.post_screen)
        }

        item.isChecked = true
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(
            Navigation.findNavController(this, R.id.nav_host_fragment),
            drawerLayout
        )
    }

    private fun init() {
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        NavigationUI.setupWithNavController(navigationView, navController)
        navigationView.setNavigationItemSelectedListener(this)
    }

    private fun isValidDestination(destination: Int): Boolean {
        return destination != Navigation.findNavController(
            this,
            R.id.nav_host_fragment
        ).currentDestination?.id
    }
}
