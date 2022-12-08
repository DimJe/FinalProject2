package org.techtown.finalproject2.Activity

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import org.koin.android.ext.android.inject
import org.techtown.finalproject2.API.Api
import org.techtown.finalproject2.R
import org.techtown.finalproject2.databinding.ActivityAddPostBinding
import java.util.*
import kotlin.collections.ArrayList

class AddPost : AppCompatActivity() {

    private val binding : ActivityAddPostBinding by lazy{
        ActivityAddPostBinding.inflate(layoutInflater)
    }
    private val array : ArrayList<String> = arrayListOf("경기장을 선택해주세요")
    private val api : Api by inject()
    private val calendar = Calendar.getInstance()
    var playerNumber = 0
    var match = ""
    var stadmIndex = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.calendar.setOnClickListener {

            DatePickerDialog(this,{_,_,month,day ->
                binding.inputTime.text = "${month+1}/$day"
            },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show()

        }
        api.stadmList.forEach {
            array.add(it.stadm_NM)
        }
        ArrayAdapter(this,android.R.layout.simple_spinner_item,array).also {
            arrayAdapter ->
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinner.adapter = arrayAdapter
        }
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if(p2 != 0){
                    Toast.makeText(this@AddPost, array[p2], Toast.LENGTH_SHORT).show()
                    stadmIndex = p2-1
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        binding.radio.setOnCheckedChangeListener { _, id ->
            when(id){
                R.id.football ->{
                    playerNumber = 22
                    match = "축구"
                }
                R.id.b3 ->{
                    playerNumber = 6
                    match = "농구"
                }
                R.id.b5 ->{
                    playerNumber = 10
                    match = "농구"
                }
            }
        }
        binding.submit.setOnClickListener {
            if(binding.editTitle.text.isNullOrBlank()
                || binding.editContent.text.isNullOrBlank()
                || binding.inputTime.text.isNullOrEmpty()
                || playerNumber == 0 || match == "" || stadmIndex == -1
            ){
                Toast.makeText(this, "정보를 모두 입력해주세요", Toast.LENGTH_SHORT).show()
            }
            else{
                api.registerPost(
                    binding.editTitle.text.toString(),
                    binding.editContent.text.toString(),
                    match,
                    binding.inputTime.text.toString(),
                    playerNumber,
                    api.stadmList[stadmIndex].stadm_NO_PK,
                    api.user?.user_NO_PK!!
                )
                finish()
            }
        }

    }
}