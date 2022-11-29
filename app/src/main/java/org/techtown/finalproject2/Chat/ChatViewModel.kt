package org.techtown.finalproject2.Chat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*

class ChatViewModel : ViewModel() {

    val db : DatabaseReference = FirebaseDatabase.getInstance().reference
    val temp = ArrayList<ChatData>()
    val _msgList = MutableLiveData<ArrayList<ChatData>>()
    val newMsg = MutableLiveData<ChatData>()
    val msgList : LiveData<ArrayList<ChatData>>
        get() = _msgList

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
}