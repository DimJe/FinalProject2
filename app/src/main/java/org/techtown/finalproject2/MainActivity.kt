package org.techtown.finalproject2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import org.techtown.finalproject2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()

    }

    private fun initView(){
        binding.loginButton.setOnClickListener {
            if(binding.userId.text.isNotEmpty() && binding.userPassword.text.isNotEmpty()){
                // TODO: 로그인시도

            }
            else{
                Toast.makeText(this, "아이디와 패스워드를 입력해주세요", Toast.LENGTH_SHORT).show()
            }
        }
        binding.makeUser.setOnClickListener {
            Log.d("태그", "initView: 회원가입 클릭")
            // TODO: 회원가입 실행
        }
    }
}