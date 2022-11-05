package org.techtown.finalproject2.API

import android.util.Log
import okhttp3.ResponseBody
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.util.ArrayList

class Api {
    var user : User? = null
    private val BASE_URL = "http://3.38.149.150"
    private val stadmList = ArrayList<Stadm>()
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(nullOnEmptyConverterFactory)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    private val instance by lazy {
        retrofit.create(ApiInterface::class.java)
    }
    private val nullOnEmptyConverterFactory = object : Converter.Factory() {
        fun converterFactory() = this
        override fun responseBodyConverter(
            type: Type,
            annotations: Array<out Annotation>,
            retrofit: Retrofit
        ): Converter<ResponseBody, *>? {
            return super.responseBodyConverter(type, annotations, retrofit)
        }
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
                    user = response.body()
                    flag = response.body() != null
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d("태그", "onFailure: ${t.localizedMessage}")
            }

        })
        return flag
    }
    fun getPost(){
        val result = instance.getPost()
        result.enqueue(object : Callback<ArrayList<PostData>>{
            override fun onResponse(
                call: Call<ArrayList<PostData>>,
                response: Response<ArrayList<PostData>>
            ) {
                response.body()?.forEach {
                    Log.d("태그", "onResponse:$it ")
                }
            }

            override fun onFailure(call: Call<ArrayList<PostData>>, t: Throwable) {
                Log.d("태그", "onFailure: ${t.localizedMessage}")
            }

        })
    }
}