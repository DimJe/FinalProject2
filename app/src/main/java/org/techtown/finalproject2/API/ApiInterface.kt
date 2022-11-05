package org.techtown.finalproject2.API

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
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

}