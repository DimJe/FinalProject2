package org.techtown.finalproject2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.koin.android.ext.android.inject
import org.techtown.finalproject2.API.Api
import org.techtown.finalproject2.API.PostData
import org.techtown.finalproject2.Chat.ChatActivity
import org.techtown.finalproject2.Chat.ChatRoomData
import org.techtown.finalproject2.databinding.ActivityPostDetailBinding

class PostDetail : AppCompatActivity() {

    private val binding : ActivityPostDetailBinding by lazy{
        ActivityPostDetailBinding.inflate(layoutInflater)
    }
    val api : Api by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val post = intent.getSerializableExtra("post") as PostData
        binding.title.text = post.pst_NM
        binding.content.text = post.pst_CN
        binding.where.text = post.stadm_NO_FK.toString()
        binding.time.text = post.match_DT

        binding.chatStart.setOnClickListener {
            //대화방 개설
            val chatRoomKey = "${post.pstuser_NO_FK}최민규2김중원"
            val db = FirebaseDatabase.getInstance().reference
            db.child("UserRoom").child(post.pstuser_NO_FK.toString()).equalTo(null,chatRoomKey).addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(!snapshot.exists()) {
                        Log.d("태그", "onDataChange:  no")
                        db.child("RoomUser").child(chatRoomKey).push().setValue(post.pstuser_NO_FK)
                        db.child("RoomUser").child(chatRoomKey).push().setValue(2)
                        db.child("UserRoom").child(post.pstuser_NO_FK.toString()).child(chatRoomKey).setValue(ChatRoomData("",chatRoomKey,"김중원","","1","2"))
                        db.child("UserRoom").child("2").child(chatRoomKey).setValue(ChatRoomData("",chatRoomKey,"최민규","","2","1"))
                    }
                }
                override fun onCancelled(error: DatabaseError) {

                }
            })
            Log.d("태그", "onDataChange:  이게 먼저 나오면 안됨")
            Intent(this,ChatActivity::class.java).apply {
                putExtra("Me",api.user?.user_NO_PK)
                putExtra("Other",post.pstuser_NO_FK)
                putExtra("Key",chatRoomKey)
                startActivity(this)
            }
        }
    }
}