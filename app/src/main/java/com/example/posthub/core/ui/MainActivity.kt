package com.example.posthub.core.ui

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.posthub.R
import com.example.posthub.domain.AuthRepository
import com.example.posthub.features.authorization.AuthorizationFragment
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

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logout) logout()
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?) : Boolean {
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
        navController.navigate(R.id.authorizationFragment)
        textMessage(getString(R.string.you_have_left_your_account))
        navController.popBackStack(R.id.authorizationFragment, false)
    }
}
