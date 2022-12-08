package org.techtown.finalproject2.Notification

import android.util.Log
import org.techtown.finalproject2.API.ApiInterface
import org.techtown.finalproject2.API.Stadm
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NotificationRetrofit {
    private val BASE_URL = "https://fcm.googleapis.com"
    private val serverKey = "AAAA8u60oqk:APA91bGuOW5Keu3KwmSF7MkKIb2MW5WlACt6RIPTI-TX19jZpmL5ITCdMg8W62ytrO0TWN1NFvWdLjhP3yAOul90OSIY-4SJODl6a_1EcrpV_EDVAiECznfhCD9MSqNleULJEPYDMvli"
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    private val instance by lazy {
        retrofit.create(NotificationInterface::class.java)
    }
    fun sendNotification(data : NotificationBody){
        val result = instance.sendNotification("key=$serverKey","application/json",data)
        result.enqueue(object : Callback<Any>{
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                Log.d("태그", "푸시알림: ${response.code()} ${response.body()} ")
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                Log.d("태그", "onFailure: register ${t.localizedMessage}")
            }

        })
    }
}