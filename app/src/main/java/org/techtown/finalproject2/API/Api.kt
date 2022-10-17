package org.techtown.finalproject2.API

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList

class Api {
    private val Owner : User? = null
    private val BASE_URL = "http://0.0.0.0"
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    private val instance by lazy {
        retrofit.create(ApiInterface::class.java)
    }
    fun getUsers(){
        val result = instance.getUsers()
        result.enqueue(object : Callback<ArrayList<User>>{
            override fun onResponse(
                call: Call<ArrayList<User>>,
                response: Response<ArrayList<User>>
            ) {
               if(response.isSuccessful){
                   Log.d("태그", "")
                   response.body()?.forEach {
                       Log.d("태그", "$it")
                   }
               }
            }

            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                Log.d("태그", "onFailure: ${t.localizedMessage}")
            }
        })
    }
    fun getUser(addr : String) : Boolean{
        val result = instance.getUser(addr)
        var flag = false
        result.enqueue(object : Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if(response.isSuccessful){
                    Log.d("태그", "onResponse:  ${response.body()}")
                    flag = response.body() != null
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d("태그", "onFailure: ${t.localizedMessage}")
            }

        })
        return flag
    }
}