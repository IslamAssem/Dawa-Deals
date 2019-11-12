package me.hashcode.dawadeals.ui.mainActivity;

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import butterknife.BindView
import com.google.android.material.bottomnavigation.BottomNavigationView
import me.hashcode.dawadeals.R
import me.hashcode.dawadeals.ui.NavigationSetup
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
        }

        override fun initViews() {
                super.initViews()
                setupBottomNavigationBar()
        }

        fun hideBottom(hasFocus: Boolean) {
                if (hasFocus)
                        findViewById<BottomNavigationView>(R.id.bottom_nav)!!.visibility = View.GONE
                else
                        findViewById<BottomNavigationView>(R.id.bottom_nav)!!.visibility = View.VISIBLE

        }
/**
 * Called on first creation and when restoring state.
 */
private fun setupBottomNavigationBar() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)

        val navGraphIds = listOf(
                R.navigation.home_navigation,R.navigation.category_navigation,
                R.navigation.trades_navigation,
                R.navigation.wallet_navigation,R.navigation.account_navigation
                /*, R.navigation.list, R.navigation.form*/)

        // Setup the bottom navigation view with a list of navigation graphs
        var nav = NavigationSetup(
                bottomNavigationView,supportFragmentManager,R.id.nav_host_container,intent
        )
        nav.navGraphIds =navGraphIds
        nav.setupWithNavController()
        // Whenever the selected controller changes, setup the action bar.
        nav.selectedNavController.observe(this, Observer { navController ->
//        setupActionBarWithNavController(navController)
        })
        currentNavController = nav.selectedNavController
        }

        override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
        }
        }