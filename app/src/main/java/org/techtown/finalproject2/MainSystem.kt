package org.techtown.finalproject2

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import org.techtown.finalproject2.databinding.ActivityMainSystemBinding
import org.techtown.finalproject2.makeUser.MakeUser1
import org.techtown.finalproject2.ui.home.HomeFragment

class MainSystem : AppCompatActivity() {

    private val binding: ActivityMainSystemBinding by lazy {
        ActivityMainSystemBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

//        val navController = findNavController(R.id.nav_host_fragment_activity_main_system)
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
//            )
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main_system) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        navView
            .setupWithNavController(navController)
    }
}