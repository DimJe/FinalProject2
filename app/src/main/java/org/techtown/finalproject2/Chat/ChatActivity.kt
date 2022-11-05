package org.techtown.finalproject2.Chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import org.techtown.finalproject2.R

class ChatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

//        val db = FirebaseDatabase.getInstance().reference
//        db.child("FcmId").child("1").setValue("token1")
//        db.child("FcmId").child("2").setValue("token2")
//        db.child("User").child("2").setValue("김중원")
//        db.child("User").child("1").setValue("최민규")
//        var key = "lli"
//        db.child("User").child("2").get().addOnSuccessListener {
//            Log.d("태그", "onCreate: ${it.key}")
//            Log.d("태그", "onCreate: ${it.value.toString()}")
//        }
//        Log.d("태그", "onCreate KEy: ${key}")
    }
}