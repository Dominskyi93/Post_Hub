package com.example.posthub.core.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.example.posthub.R
import com.example.posthub.core.ui.fragments.AuthorizationFragment
import com.example.posthub.domain.AuthRepository
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar

    @Inject
    lateinit var authRepository: AuthRepository
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        checkInternetConnection()
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    private fun checkInternetConnection() {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.activeNetwork?.let {
            connectivityManager.getNetworkCapabilities(it)
        }
        if (networkCapabilities == null ||
            !networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        ) {
            showSnackBarNoInternet()
        }
    }

    private fun showSnackBarNoInternet() {
        val rootView = findViewById<View>(android.R.id.content)
        val snackBar = Snackbar.make(
            rootView,
            getString(R.string.check_connection),
            Snackbar.LENGTH_INDEFINITE
        )
        snackBar.setAction(getString(R.string.ok)) {}
        snackBar.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logout) logout()
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        super.onPrepareOptionsMenu(menu)
        val currentFragment = this.supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        menu?.findItem(R.id.logout)?.isVisible = currentFragment !is AuthorizationFragment
        return true
    }

    private fun textMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun logout() {
        authRepository.signOut()
        textMessage(getString(R.string.you_have_left_your_account))
        val navOptions = NavOptions.Builder().setPopUpTo(R.id.authorizationFragment, true).build()
        navController.navigate(R.id.authorizationFragment, null, navOptions)
    }
}