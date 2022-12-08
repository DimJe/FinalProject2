package org.techtown.finalproject2.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject
import org.techtown.finalproject2.API.Api
import org.techtown.finalproject2.R
import org.techtown.finalproject2.databinding.ActivityRegisterUserBinding


class RegisterUser : AppCompatActivity() {

    private val binding : ActivityRegisterUserBinding by lazy {
        ActivityRegisterUserBinding.inflate(layoutInflater)
    }
    val item = listOf<String>("지역을 선택해주세요","서울","경기","인천","강원","경북","경남","충북","충남","전북","전남","제주")
    lateinit var email : String
    lateinit var name : String
    var gender = ""
    var region = ""
    val api : Api by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        ArrayAdapter(this,android.R.layout.simple_spinner_item,item).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.regionSpinner.adapter = it
        }
        binding.regionSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                region = if(p2 == 0) "" else item[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}

        }
        UserApiClient.instance.me { user, error ->
            email = user?.kakaoAccount?.email.toString()
            name = user?.kakaoAccount?.profile?.nickname!!
            Log.d("태그", "카카오 계정 ${email}\n${name} ")
        }
        binding.gender.setOnCheckedChangeListener { _, id ->
            gender = if(id == R.id.f) "여" else "남"
        }
        binding.next.setOnClickListener {

            if(region == "" || gender == ""
                ||binding.editAge.text.isNullOrBlank()
                || binding.editHeight.text.isNullOrBlank()
                || binding.editNick.text.isNullOrBlank()
                || binding.editPhone.text.isNullOrBlank()
                || binding.editWeight.text.isNullOrBlank()) Toast.makeText(this, "정보를 모두 입력해주세요.", Toast.LENGTH_SHORT).show()
            else{
                CoroutineScope(Dispatchers.IO).launch {
                    val deferred : Deferred<Boolean> = async {
                        api.registerUser(
                            name,
                            gender,
                            binding.editAge.text.toString().toInt(),
                            binding.editNick.text.toString(),
                            region,
                            binding.editPhone.text.toString(),
                            email,
                            binding.editHeight.text.toString().toFloat(),
                            binding.editWeight.text.toString().toFloat(),
                            false
                        )
                    }
                    val temp = deferred.await()
                    if(temp) {
                        api.getUser(email)
                        runOnUiThread { Toast.makeText(this@RegisterUser, "등록에 성공했습니다.", Toast.LENGTH_SHORT).show() }
                        Intent(this@RegisterUser,MainSystem::class.java).apply { startActivity(this) }
                        finish()
                    }
                    else {
                        runOnUiThread{ Toast.makeText(this@RegisterUser, "닉네임이 중복입니다.", Toast.LENGTH_SHORT).show()}
                    }
                }
            }
        }
    }
}


