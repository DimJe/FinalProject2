package org.techtown.finalproject2.API

import retrofit2.Call
import retrofit2.http.*
import java.util.ArrayList

interface ApiInterface {

    @GET("/user")
    fun getUsers() : Call<ArrayList<User>>

    @GET("/user/{user_EML_ADDR}")
    fun getUser(
        @Path("user_EML_ADDR") addr : String
    ) : Call<User>

    @GET("/board")
    fun getPost() : Call<ArrayList<PostData>>

    @POST("/user/register")
    @FormUrlEncoded
    fun registerUser(
        @Field("USER_NM") name : String,
        @Field("USER_GNDR") gndr : String,
        @Field("USER_AGE") age : Int,
        @Field("USER_NCKNM") nickName : String,
        @Field("USER_RGN") rgn : String,
        @Field("USER_TELNO") telNO : String,
        @Field("USER_EML_ADDR") email : String,
        @Field("USER_HGT") height : Float,
        @Field("USER_WGT") weight : Float,
        @Field("INSTR_USER_YN") instr : Boolean
    ) : Call<Any>

    @POST("/board/register")
    @FormUrlEncoded
    fun registerPost(
        @Field("PST_NM") name : String,
        @Field("PST_CN") content : String,
        @Field("MATCH_EVT") match : String,
        @Field("MATCH_DT") date : String,
        @Field("MATCH_NOP") playerNum : Int,
        @Field("STADM_NO_FK") stadmNo : Int,
        @Field("PSTUSER_NO_FK") userNo : Int
    ) : Call<Any>

    @GET("/stadium")
    fun getStadium() : Call<ArrayList<Stadm>>

}