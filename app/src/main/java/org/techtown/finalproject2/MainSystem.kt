package org.techtown.finalproject2

import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.database.FirebaseDatabase
import org.koin.android.ext.android.inject
import org.techtown.finalproject2.API.Api
import org.techtown.finalproject2.databinding.ActivityMainSystemBinding

class MainSystem : AppCompatActivity() {

    private val binding: ActivityMainSystemBinding by lazy {
        ActivityMainSystemBinding.inflate(layoutInflater)
    }
    private val api : Api by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Log.d("태그", "onCreate: called")
        val navView: BottomNavigationView = binding.navView

        val db = FirebaseDatabase.getInstance().reference
        db.child("User").child(api.user?.user_NO_PK.toString()).setValue(api.user?.user_NCKNM)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main_system) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        navView
            .setupWithNavController(navController)
    }

}