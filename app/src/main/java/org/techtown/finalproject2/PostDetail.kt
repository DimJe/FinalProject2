package org.techtown.finalproject2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
    val db = FirebaseDatabase.getInstance().reference
    var postUserName = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val post = intent.getSerializableExtra("post") as PostData
        binding.title.text = post.pst_NM
        binding.content.text = post.pst_CN
        binding.where.text = post.stadm_NO_FK.toString()
        binding.time.text = post.match_DT

        db.child("User").child(post.pstuser_NO_FK.toString()).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                postUserName = snapshot.value.toString()
                Log.d("태그", "onDataChange:  ${postUserName}")
            }

            override fun onCancelled(error: DatabaseError) {}
        })

        binding.chatStart.setOnClickListener {

            if(post.pstuser_NO_FK == api.user?.user_NO_PK){
                Toast.makeText(this, "본인의 게시물입니다.", Toast.LENGTH_SHORT).show()
            }

            //대화방 개설
            val chatRoomKey = "${post.pstuser_NO_FK}${postUserName}${api.user?.user_NO_PK}${api.user?.user_NCKNM}"
            db.child("UserRoom").child(post.pstuser_NO_FK.toString()).equalTo(null,chatRoomKey).addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(!snapshot.exists()) {
                        Log.d("태그", "onDataChange:  no")
                        db.child("RoomUser").child(chatRoomKey).push().setValue(post.pstuser_NO_FK)
                        db.child("RoomUser").child(chatRoomKey).push().setValue(api.user?.user_NO_PK)
                        db.child("UserRoom").child(post.pstuser_NO_FK.toString()).child(chatRoomKey).setValue(ChatRoomData("",chatRoomKey,api.user?.user_NCKNM!!,"",post.pstuser_NO_FK.toString(),api.user?.user_NO_PK.toString()))
                        db.child("UserRoom").child(api.user?.user_NO_PK.toString()).child(chatRoomKey).setValue(ChatRoomData("",chatRoomKey,postUserName,"",api.user?.user_NO_PK.toString(),post.pstuser_NO_FK.toString()))
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