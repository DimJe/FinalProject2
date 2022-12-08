package org.techtown.finalproject2.API

import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import kotlin.collections.ArrayList

class Api {
    var user : User? = null
    var postDataList = MutableLiveData<ArrayList<PostData>>()
    var instrUserList = MutableLiveData<ArrayList<User>>()
    val clubDataList = ArrayList<ClubData>()
    private val BASE_URL = "http://3.38.210.48"
    val stadmList = ArrayList<Stadm>()
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
        override fun responseBodyConverter(type: Type, annotations: Array<out Annotation>, retrofit: Retrofit) = object : Converter<ResponseBody, Any?> {
            val nextResponseBodyConverter = retrofit.nextResponseBodyConverter<Any?>(converterFactory(), type, annotations)
            override fun convert(value: ResponseBody) = if (value.contentLength() != 0L) {
                try{
                    nextResponseBodyConverter.convert(value)
                }catch (e:Exception){
                    e.printStackTrace()
                    null
                }
            } else{
                null
            }
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

        val body = result.execute().body()
        if(body != null){
            flag = true
            Log.d("태그", "getUser: $body")
            user = body
        }
        return flag
    }
    fun getPost(){
        val result = instance.getPost()
        val temp = ArrayList<PostData>()
        result.enqueue(object : Callback<ArrayList<PostData>>{
            override fun onResponse(
                call: Call<ArrayList<PostData>>,
                response: Response<ArrayList<PostData>>
            ) {
                response.body()?.forEach {
                    temp.add(it)
                }
                postDataList.postValue(temp)
            }

            override fun onFailure(call: Call<ArrayList<PostData>>, t: Throwable) {
                Log.d("태그", "onFailure: ${t.localizedMessage}")
            }

        })
    }
    fun registerUser(
        name : String,
        gndr : String,
        age : Int,
        nickName : String,
        rgn : String,
        telNO : String,
        email : String,
        height : Float,
        weight : Float,
        instr : Boolean
    ) : Boolean{
        val result = instance.registerUser(name, gndr, age, nickName, rgn, telNO, email, height, weight, instr)
        val flag = result.execute().code() == 200

        return flag
    }
    fun modifyUser(
        name : String,
        gndr : String,
        age : Int,
        nickName : String,
        rgn : String,
        telNO : String,
        email : String,
        height : Float,
        weight : Float,
        instr : Boolean,
        manner : Float,
        skill : Float,
        club : Int
    ){
        val result = instance.modifyUser(name,gndr,age, nickName, rgn, telNO, email, height, weight, instr, skill, manner, club)
        result.enqueue(object : Callback<Any>{
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                Log.d("태그", "onResponse: register ${response.code()}")
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                Log.d("태그", "onFailure: register ${t.localizedMessage}")
            }

        })
    }
    fun getStadium(){
        val result = instance.getStadium()
        result.enqueue(object : Callback<ArrayList<Stadm>> {
            override fun onResponse(
                call: Call<ArrayList<Stadm>>,
                response: Response<ArrayList<Stadm>>
            ) {
                if(response.isSuccessful){
                    stadmList.clear()
                    response.body()?.forEach {
                        stadmList.add(it)
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<Stadm>>, t: Throwable) {
                Log.d("태그", "onFailure: getStadium ${t.localizedMessage}")
            }

        })
    }
    fun registerClub(
        name : String,
        sport : String,
        region : String,
        num : Int,
        email : String
    ){
        val result = instance.registerClub(name, sport, region, num, email)
        result.enqueue(object : Callback<Any>{
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                if(response.isSuccessful) {
                    Log.d("태그", "registerClub: ${response.code()}")
                    getClub()
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                Log.d("태그", "onFailure: getStadium ${t.localizedMessage}")
            }

        })
    }
    fun registerPost(name : String,
                     content : String,
                     match : String,
                     date : String,
                     playerNum : Int,
                     stadmNo : Int,
                     userNo : Int
    ){
        val result = instance.registerPost(name,content, match, date, playerNum, stadmNo, userNo)
        result.enqueue(object : Callback<Any>{
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                if(response.isSuccessful){
                    Log.d("태그", "onResponse: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                Log.d("태그", "onFailure: getStadium ${t.localizedMessage}")
            }

        })
    }
    fun setSkillScore(num : Int, score : Float){
        val result = instance.setSkillScore(num,score)
        result.enqueue(object : Callback<Any>{
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                if(response.isSuccessful){
                    Log.d("태그", "onResponse: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                Log.d("태그", "onFailure: getStadium ${t.localizedMessage}")
            }

        })
    }
    fun setMannerScore(num : Int, score : Float){
        val result = instance.setMannerScore(num,score)
        result.enqueue(object : Callback<Any>{
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                if(response.isSuccessful){
                    Log.d("태그", "onResponse: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                Log.d("태그", "onFailure: getStadium ${t.localizedMessage}")
            }

        })
    }
    fun getInstr(){
        val result = instance.getInstr()
        val temp = ArrayList<User>()
        result.enqueue(object : Callback<ArrayList<User>>{
            override fun onResponse(
                call: Call<ArrayList<User>>,
                response: Response<ArrayList<User>>
            ) {
                if(response.isSuccessful){
                    response.body()?.forEach {
                        temp.add(it)
                    }
                }
                instrUserList.postValue(temp)
            }

            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                Log.d("태그", "onFailure: getStadium ${t.localizedMessage}")
            }

        })
    }
    fun getClub(){
        val result = instance.getClub()
        result.enqueue(object : Callback<ArrayList<ClubData>>{
            override fun onResponse(
                call: Call<ArrayList<ClubData>>,
                response: Response<ArrayList<ClubData>>
            ) {
                if(response.isSuccessful){
                    clubDataList.clear()
                    response.body()?.forEach {
                        Log.d("태그", "getClub: $it")
                        clubDataList.add(it)
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<ClubData>>, t: Throwable) {
                Log.d("태그", "onFailure: getStadium ${t.localizedMessage}")
            }

        })
    }
}