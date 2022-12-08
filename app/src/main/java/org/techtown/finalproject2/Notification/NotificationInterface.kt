package org.techtown.finalproject2.Notification

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface NotificationInterface {

    @POST("fcm/send")
    fun sendNotification(
        @Header("Authorization") key : String,
        @Header("Content-Type") contentType : String,
        @Body notification : NotificationBody
    ) : Call<Any>
}