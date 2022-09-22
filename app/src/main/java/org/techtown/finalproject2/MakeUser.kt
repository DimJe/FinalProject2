package org.techtown.finalproject2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import org.techtown.finalproject2.databinding.ActivityMainBinding
import org.techtown.finalproject2.databinding.ActivityMakeUserBinding
import org.techtown.finalproject2.makeUser.MakeUser1
import org.techtown.finalproject2.makeUser.MakeUser2
import org.techtown.finalproject2.makeUser.MakeUser3

class MakeUser : AppCompatActivity() {

    private val binding : ActivityMakeUserBinding by lazy {
        ActivityMakeUserBinding.inflate(layoutInflater)
    }
    var flag = 0
    private val transaction = supportFragmentManager.beginTransaction()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        transaction.add(R.id.container, MakeUser1())
        transaction.addToBackStack(null)
        transaction.commit()

        binding.next.setOnClickListener {

            if(flag==2){
                //
            }
            else {
                switchFragment()
                Log.d("태그", "switchFragment")
            }
        }

    }

    private fun switchFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        when(flag){
            0 -> {
                transaction.add(R.id.container, MakeUser2())
                flag = 1
            }
            1 -> {
                transaction.replace(R.id.container, MakeUser3())
                flag = 2
            }
        }
        transaction.addToBackStack(null)
        transaction.commit()

    }

}