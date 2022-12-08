package org.techtown.finalproject2.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.koin.android.ext.android.inject
import org.techtown.finalproject2.API.Api
import org.techtown.finalproject2.API.User
import org.techtown.finalproject2.Chat.ChatActivity
import org.techtown.finalproject2.Chat.ChatRoomData
import org.techtown.finalproject2.adapter.InstrAdapter
import org.techtown.finalproject2.databinding.ActivityInstrUserBinding
import org.techtown.finalproject2.databinding.RecyclerInstrItemBinding

class InstrUser : AppCompatActivity() {

    private val binding : ActivityInstrUserBinding by lazy {
        ActivityInstrUserBinding.inflate(layoutInflater)
    }

    private val api : Api by inject()
    private var instrUser : User? = null
    val db = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
    }
    private fun initView(){
        api.instrUserList.observe(this) {
            InstrAdapter(it).apply {
                binding.recycler.adapter = this
                this.setOnItemClickListener(object : InstrAdapter.OnItemClickListener{
                    override fun onItemClick(b: RecyclerInstrItemBinding, user: User, positon: Int) {
                        Log.d("태그", "onItemClick: call")
                        binding.select.text = "선택한 강사 : ${user.user_NCKNM}"
                        instrUser = user

                    }
                })
                binding.recycler.layoutManager = LinearLayoutManager(this@InstrUser)
            }
        }
        api.getInstr()
        binding.chatStart.setOnClickListener {
            if(instrUser == null){
                Toast.makeText(this, "강사님을 선택해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(instrUser?.user_NO_PK == api.user?.user_NO_PK){
                Toast.makeText(this, "본인입니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val chatRoomKey = "${instrUser!!.user_NO_PK}${instrUser!!.user_NCKNM}(강사)${api.user?.user_NO_PK}${api.user?.user_NCKNM}(수강생)"
            db.child("UserRoom").child(instrUser!!.user_NO_PK.toString()).equalTo(null,chatRoomKey).addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(!snapshot.exists()) {
                        Log.d("태그", "onDataChange:  no")
                        db.child("RoomUser").child(chatRoomKey).push().setValue(instrUser!!.user_NO_PK)
                        db.child("RoomUser").child(chatRoomKey).push().setValue(api.user?.user_NO_PK)
                        db.child("UserRoom").child(instrUser!!.user_NO_PK.toString()).child(chatRoomKey).setValue(
                            ChatRoomData("",chatRoomKey,"${api.user?.user_NCKNM!!} (수강생)",0.0,instrUser!!.user_NO_PK.toString(),api.user?.user_NO_PK.toString())
                        )
                        db.child("UserRoom").child(api.user?.user_NO_PK.toString()).child(chatRoomKey).setValue(
                            ChatRoomData("",chatRoomKey,"${instrUser!!.user_NCKNM} (강사)",0.0,api.user?.user_NO_PK.toString(),instrUser!!.user_NO_PK.toString())
                        )
                    }
                }
                override fun onCancelled(error: DatabaseError) {

                }
            })
            Intent(this, ChatActivity::class.java).apply {
                putExtra("Me",api.user!!.user_NO_PK.toString())
                putExtra("Other",instrUser!!.user_NO_PK.toString())
                putExtra("Key",chatRoomKey)
                putExtra("Name","${instrUser!!.user_NCKNM} (강사)")
                startActivity(this)
            }
            finish()
        }
    }
}