package org.techtown.finalproject2.fragment.dashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.techtown.finalproject2.API.Api
import org.techtown.finalproject2.Chat.ChatRoomData

class DashboardViewModel(val api : Api) : ViewModel() {

    private val db = FirebaseDatabase.getInstance().reference
    val data = MutableLiveData<ArrayList<ChatRoomData>>()
    private val temp = ArrayList<ChatRoomData>()
    fun getChatRoom(){
        db.child("UserRoom").child("2").addListenerForSingleValueEvent(object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("태그", "getRoom called ")
                for (sn in snapshot.children){
                    temp.add(sn.getValue(ChatRoomData::class.java)!!)
                }
                temp.forEach {
                    Log.d("태그", "onDataChange: ${it.me}")
                }
                data.postValue(temp)
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })

    }

}