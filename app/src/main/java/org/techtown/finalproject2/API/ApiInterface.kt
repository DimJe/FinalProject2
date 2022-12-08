package org.techtown.finalproject2.API

import retrofit2.Call
import retrofit2.http.*
import kotlin.collections.ArrayList

interface ApiInterface {

    @GET("/user")
    fun getUsers() : Call<ArrayList<User>>

    @GET("/user/{user_EML_ADDR}")
    fun getUser(
        @Path("user_EML_ADDR") addr : String
    ) : Call<User>

    @GET("/club")
    fun getClub() : Call<ArrayList<ClubData>>

    @GET("/board")
    fun getPost() : Call<ArrayList<PostData>>

    @GET("/user/instructor")
    fun getInstr() : Call<ArrayList<User>>

    @POST("/club/register")
    @FormUrlEncoded
    fun registerClub(
        @Field("CLUB_NM") name : String,
        @Field("SPT_EVT") sport : String,
        @Field("CLUB_RGN") region : String,
        @Field("CLUB_MAX_NOP") num : Int,
        @Field("LDR_EMLADDR_FK") email : String
    ) : Call<Any>

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

    @PUT("/user/modify/mannerscore")
    @FormUrlEncoded
    fun setMannerScore(
        @Field("USER_NO_PK") num : Int,
        @Field("USER_MNR_SCR") score : Float
    ) : Call<Any>

    @PUT("/user/modify/skillscore")
    @FormUrlEncoded
    fun setSkillScore(
        @Field("USER_NO_PK") num : Int,
        @Field("USER_SKL_SCR") score : Float
    ) : Call<Any>

    @PUT("/user/modify")
    @FormUrlEncoded
    fun modifyUser(
        @Field("USER_NM") name : String,
        @Field("USER_GNDR") gndr : String,
        @Field("USER_AGE") age : Int,
        @Field("USER_NCKNM") nickName : String,
        @Field("USER_RGN") rgn : String,
        @Field("USER_TELNO") telNO : String,
        @Field("USER_EML_ADDR") email : String,
        @Field("USER_HGT") height : Float,
        @Field("USER_WGT") weight : Float,
        @Field("INSTR_USER_YN") instr : Boolean,
        @Field("USER_SKL_SCR") skill : Float,
        @Field("USER_MNR_SCR") manner : Float,
        @Field("CLUB_NO") club : Int
    ) : Call<Any>

}