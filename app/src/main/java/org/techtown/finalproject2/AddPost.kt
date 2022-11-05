package org.techtown.finalproject2

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
import org.techtown.finalproject2.databinding.ActivityAddPostBinding
import java.util.*
import kotlin.collections.ArrayList

class AddPost : AppCompatActivity() {

    private val binding : ActivityAddPostBinding by lazy{
        ActivityAddPostBinding.inflate(layoutInflater)
    }
    private val array : ArrayList<String> = arrayListOf("경기장을 선택해주세요","흥업 체육관","무실 체육관","종합 경기장","단계 체육관")
    private val api : Api by inject()
    private val calendar = Calendar.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.calendar.setOnClickListener {

            DatePickerDialog(this,{_,_,month,day ->
                binding.inputTime.text = "${month+1}/$day"
            },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show()

        }
        ArrayAdapter(this,android.R.layout.simple_spinner_item,array).also {
            arrayAdapter ->
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinner.adapter = arrayAdapter
        }
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if(array[p2]!="경기장을 선택해주세요"){
                    Toast.makeText(this@AddPost, array[p2], Toast.LENGTH_SHORT).show()
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

    }
}