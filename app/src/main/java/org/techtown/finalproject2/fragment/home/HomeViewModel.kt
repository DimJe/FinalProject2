package org.techtown.finalproject2.fragment.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.techtown.finalproject2.API.Api
import org.techtown.finalproject2.API.PostData

class HomeViewModel(val api : Api) : ViewModel() {


    private val _data = MutableLiveData<ArrayList<PostData>>().apply {
        val temp = ArrayList<PostData>().apply {
            add(PostData(1,1,"10월 8일에 종합경기장에서 bas 하실 분 구합니다!","10월 8일 종합 경기장","basketball","22.10.08 19",6,1))
            add(PostData(1,1,"10월 8일에 종합경기장에서 축구 하실 분 구합니다!","10월 8일 종합 경기장","basketball","22.10.08 19",10,1))
            add(PostData(1,1,"10월 8일에 종합경기장에서 농구 하실 분 구합니다!","10월 8일 종합 경기장","basketball","22.10.08 19",6,1))
            add(PostData(1,1,"10월 8일에 종합경기장에서 축구 하실 분 구합니다!","10월 8일 종합 경기장","basketball","22.10.08 19",10,1))
            add(PostData(1,1,"10월 8일에 종합경기장에서 농구 하실 분 구합니다!","10월 8일 종합 경기장","basketball","22.10.08 19",6,1))
            add(PostData(1,1,"10월 8일에 종합경기장에서 축구 하실 분 구합니다!","10월 8일 종합 경기장","basketball","22.10.08 19",6,1))
            add(PostData(1,1,"10월 8일에 종합경기장에서 게임 하실 분 구합니다!","10월 8일 종합 경기장","basketball","22.10.08 19",10,1))
            add(PostData(1,1,"10월 8일에 종합경기장에서 게임 하실 분 구합니다!","10월 8일 종합 경기장","basketball","22.10.08 19",6,1))
            add(PostData(1,1,"10월 8일에 종합경기장에서 게임 하실 분 구합니다!","10월 8일 종합 경기장","basketball","22.10.08 19",10,1))
            add(PostData(1,1,"10월 8일에 종합경기장에서 게임 하실 분 구합니다!","10월 8일 종합 경기장","basketball","22.10.08 19",6,1))
            add(PostData(1,1,"10월 8일에 종합경기장에서 게임 하실 분 구합니다!","10월 8일 종합 경기장","basketball","22.10.08 19",10,1))
            add(PostData(1,1,"10월 8일에 종합경기장에서 게임 하실 분 구합니다!","10월 8일 종합 경기장","basketball","22.10.08 19",6,1))

        }
        value = temp

    }
    val data: LiveData<ArrayList<PostData>> = _data
}