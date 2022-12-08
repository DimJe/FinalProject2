package org.techtown.finalproject2.Chat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.techtown.finalproject2.API.Api
import org.techtown.finalproject2.Notification.NotificationBody
import org.techtown.finalproject2.Notification.NotificationRetrofit

class ChatViewModel(val api : Api) : ViewModel() {

    val db : DatabaseReference = FirebaseDatabase.getInstance().reference
    val temp = ArrayList<ChatData>()
    val _msgList = MutableLiveData<ArrayList<ChatData>>()
    val newMsg = MutableLiveData<ChatData>()
    val msgList : LiveData<ArrayList<ChatData>>
        get() = _msgList
    val notificationRetrofit = NotificationRetrofit()

    var meCheck : Boolean? = false
    var otherCheck  : Boolean? = false
    fun checkPermission(key: String){
        db.child("UserRoom").child(api.user?.user_NO_PK.toString()).child(key).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val temp = snapshot.getValue(ChatRoomData::class.java)
                    meCheck = temp?.meCheck
                    otherCheck = temp?.otherCheck
                    if(temp?.otherEstimate == true) {
                        CoroutineScope(Dispatchers.IO).launch {
                            api.getUser(api.user?.user_EML_ADDR.toString())
                        }
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }
    fun getMsgList(key : String){
        db.child("Message").child(key).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(sn in snapshot.children){
                    temp.add(sn.getValue(ChatData::class.java)!!)
                }
                _msgList.postValue(temp)
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }
    fun submitMsg(key:String,msg:ChatData){
        db.child("Message").child(key).push().setValue(msg)
    }
    fun updateMsg(key:String){
        db.child("Message").child(key).limitToLast(1).addChildEventListener(object : ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val msg = snapshot.getValue(ChatData::class.java)
                if(temp.isEmpty() || temp.last().timeStamp != msg!!.timeStamp) newMsg.postValue(snapshot.getValue(ChatData::class.java))
            }
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}
        })
    }
    fun updateChatRoomData(key:String, msg:String, timeStamp : Long, me:String, other:String){
        db.child("UserRoom").child(me).child(key).child("lastMessage").setValue(msg)
        db.child("UserRoom").child(me).child(key).child("timeStamp").setValue(timeStamp)
        db.child("UserRoom").child(other).child(key).child("lastMessage").setValue(msg)
        db.child("UserRoom").child(other).child(key).child("timeStamp").setValue(timeStamp)
    }
    fun finishGame(key:String,me:String,other: String){
        db.child("UserRoom").child(me).child(key).child("meCheck").setValue(true)
        db.child("UserRoom").child(other).child(key).child("otherCheck").setValue(true)
        //db.child("UserRoom").child(me).child(key).child("otherCheck").setValue(true)
        //db.child("UserRoom").child(other).child(key).child("meCheck").setValue(true)
    }
    fun estimateUser(key: String,meNum : String,otherNum : String,mannerScore : Int, skillScore : Int){

        Log.d("태그", "estimateUser: called")
        api.setSkillScore(otherNum.toInt(),skillScore.toFloat())
        api.setMannerScore(otherNum.toInt(),mannerScore.toFloat())
        db.child("UserRoom").child(meNum).child(key).child("meEstimate").setValue(true)
        db.child("UserRoom").child(otherNum).child(key).child("otherEstimate").setValue(true)
    }
    fun sendNotification(other : String,msg : String){
        db.child("FcmId").child(other).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val token = snapshot.getValue<String>()
                val data = NotificationBody.NotificationData(api.user!!.user_NCKNM,"id",msg)
                notificationRetrofit.sendNotification(NotificationBody(token!!,data))
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}