package org.techtown.finalproject2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.techtown.finalproject2.databinding.ActivityMainBinding
import org.techtown.finalproject2.databinding.ActivityMakeUserBinding

class MakeUser : AppCompatActivity() {

    private val binding : ActivityMakeUserBinding by lazy {
        ActivityMakeUserBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


    }
}