package org.techtown.finalproject2.Activity

import android.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.techtown.finalproject2.API.Api
import org.techtown.finalproject2.databinding.ActivityModifyUserBinding

class ModifyUser : AppCompatActivity() {

    private val binding : ActivityModifyUserBinding by lazy{
        ActivityModifyUserBinding.inflate(layoutInflater)
    }
    val item = listOf<String>("지역을 선택해주세요","서울","경기","인천","강원","경북","경남","충북","충남","전북","전남","제주")
    var region = ""
    val api : Api by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()

    }
    private fun initView(){
        ArrayAdapter(this, R.layout.simple_spinner_item,item).also {
            it.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
            binding.modifyRegion.adapter = it
        }
        binding.modifyRegion.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                region = if(p2 == 0) "" else item[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}

        }
        binding.modifyPhone.setText(api.user?.user_TELNO.toString())
        binding.modifyAge.setText(api.user?.user_AGE.toString())
        binding.modifyHeight.setText(api.user?.user_HGT.toString())
        binding.modifyWeight.setText(api.user?.user_WGT.toString())
        binding.modify.setOnClickListener {
            val phone = binding.modifyPhone.text.toString()
            val age = binding.modifyAge.text.toString().toIntOrNull()
            val height = binding.modifyHeight.text.toString().toFloatOrNull()
            val weight = binding.modifyWeight.text.toString().toFloatOrNull()
            val user = api.user!!
            Log.d("태그", "수정:$phone $age $height $weight $region ")
            if(phone.isNotBlank() && age != null && height != null && weight != null && region != ""){

                CoroutineScope(Dispatchers.IO).launch {
                    api.modifyUser(user.user_NM,user.user_GNDR,age,user.user_NCKNM,region,phone,user.user_EML_ADDR,height,weight,user.instr_USER_YN,user.user_MNR_SCR,user.user_SKL_SCR,user.club_NO)
                    delay(1000)
                    if(api.getUser(user.user_EML_ADDR)) finish()
                }

            }else Toast.makeText(this, "올바른 입력인지 확인해주세요", Toast.LENGTH_SHORT).show()
        }
    }

}