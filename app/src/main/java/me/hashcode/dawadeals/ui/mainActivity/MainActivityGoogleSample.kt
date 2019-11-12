package me.hashcode.dawadeals.ui.mainActivity;

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import customNavigator.setupWithNavController
import me.hashcode.dawadeals.R
import me.hashcode.dawadeals.ui.base.BaseActivity

//
//public class MainActivityGoogleSample {
//}

/**
 * An activity that inflates a layout that has a [BottomNavigationView].
 */
class MainActivityGoogleSample : BaseActivity() {
        override fun saveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun initVaribles() {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun initViewModels() {
               // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        private var currentNavController: LiveData<NavController>? = null

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_google_sample)
        if (savedInstanceState == null) {
        setupBottomNavigationBar()
        } // Else, need to wait for onRestoreInstanceState
        }

        override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        // Now that BottomNavigationBar has restored its instance state
        // and its selectedItemId, we can proceed with setting up the
        // BottomNavigationBar with Navigation
        setupBottomNavigationBar()
        }

/**
 * Called on first creation and when restoring state.
 */
private fun setupBottomNavigationBar() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)

        val navGraphIds = listOf(R.navigation.mobile_navigation/*, R.navigation.list, R.navigation.form*/)

        // Setup the bottom navigation view with a list of navigation graphs
        val controller = bottomNavigationView.setupWithNavController(
        navGraphIds = navGraphIds,
        fragmentManager = supportFragmentManager,
        containerId = R.id.nav_host_container,
        intent = intent
        )

        // Whenever the selected controller changes, setup the action bar.
        controller.observe(this, Observer { navController ->
//        setupActionBarWithNavController(navController)
        })
        currentNavController = controller
        }

        override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
        }
        }