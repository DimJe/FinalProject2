package org.techtown.finalproject2.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.techtown.finalproject2.PostData
import org.techtown.finalproject2.R

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    private val _data = MutableLiveData<ArrayList<PostData>>().apply {
        val temp = ArrayList<PostData>().apply {
            add(PostData(R.drawable.ic_launcher_foreground,"10월 8일에 종합경기장에서 게임 하실 분 구합니다!","10월 8일 종합 경기장","b5:5","22.10.08 19","종합경기장"))
            add(PostData(R.drawable.ic_launcher_foreground,"10월 8일에 종합경기장에서 게임 하실 분 구합니다!","10월 8일 종합 경기장","b3:3","22.10.08 19","종합경기장"))
            add(PostData(R.drawable.ic_launcher_foreground,"10월 8일에 종합경기장에서 게임 하실 분 구합니다!","10월 8일 종합 경기장","b5:5","22.10.08 19","종합경기장"))
            add(PostData(R.drawable.ic_launcher_foreground,"10월 8일에 종합경기장에서 게임 하실 분 구합니다!","10월 8일 종합 경기장","b3:3","22.10.08 19","종합경기장"))
            add(PostData(R.drawable.ic_launcher_foreground,"10월 8일에 종합경기장에서 게임 하실 분 구합니다!","10월 8일 종합 경기장","b5:5","22.10.08 19","종합경기장"))
            add(PostData(R.drawable.ic_launcher_foreground,"10월 8일에 종합경기장에서 게임 하실 분 구합니다!","10월 8일 종합 경기장","b3:3","22.10.08 19","종합경기장"))
            add(PostData(R.drawable.ic_launcher_foreground,"10월 8일에 종합경기장에서 게임 하실 분 구합니다!","10월 8일 종합 경기장","b5:5","22.10.08 19","종합경기장"))
            add(PostData(R.drawable.ic_launcher_foreground,"10월 8일에 종합경기장에서 게임 하실 분 구합니다!","10월 8일 종합 경기장","b3:3","22.10.08 19","종합경기장"))
            add(PostData(R.drawable.ic_launcher_foreground,"10월 8일에 종합경기장에서 게임 하실 분 구합니다!","10월 8일 종합 경기장","b5:5","22.10.08 19","종합경기장"))
            add(PostData(R.drawable.ic_launcher_foreground,"10월 8일에 종합경기장에서 게임 하실 분 구합니다!","10월 8일 종합 경기장","b3:3","22.10.08 19","종합경기장"))
            add(PostData(R.drawable.ic_launcher_foreground,"10월 8일에 종합경기장에서 게임 하실 분 구합니다!","10월 8일 종합 경기장","b5:5","22.10.08 19","종합경기장"))
            add(PostData(R.drawable.ic_launcher_foreground,"10월 8일에 종합경기장에서 게임 하실 분 구합니다!","10월 8일 종합 경기장","b3:3","22.10.08 19","종합경기장"))

        }
        value = temp

    }
    val data: LiveData<ArrayList<PostData>> = _data
    val text: LiveData<String> = _text
}