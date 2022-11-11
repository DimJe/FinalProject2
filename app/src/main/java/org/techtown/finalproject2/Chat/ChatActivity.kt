package org.techtown.finalproject2.Chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.techtown.finalproject2.adapter.ChatAdapter
import org.techtown.finalproject2.databinding.ActivityChatBinding

class ChatActivity : AppCompatActivity() {
    private val binding : ActivityChatBinding by lazy {
        ActivityChatBinding.inflate(layoutInflater)
    }
    private lateinit var meNum :String
    private lateinit var otherNum : String
    private lateinit var otherName : String
    private lateinit var Key : String
    private val chatViewModel : ChatViewModel by viewModel()
    lateinit var adapter: ChatAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        meNum = intent.getStringExtra("Me").toString()
        otherNum = intent.getStringExtra("Other").toString()
        otherName = intent.getStringExtra("Name").toString()
        Key = intent.getStringExtra("Key").toString()

        binding.chatName.text = otherName

        chatViewModel.msgList.observe(this){
            Log.d("태그", "onCreate:chatting  observe")
            it.forEach {
                Log.d("태그", "onCreate: ${it.msg}")
            }
            adapter = ChatAdapter(it,otherName,meNum)
            binding.chatRecycler.adapter = adapter
            binding.chatRecycler.layoutManager = LinearLayoutManager(this)
            chatViewModel.updateMsg(Key)

        }
        chatViewModel.newMsg.observe(this){
            adapter.addChat(it)
            Log.d("태그", "onCreate new: ${it.msg}")
        }
        chatViewModel.getMsgList(Key)

        Log.d("태그", "onCreate:$meNum $otherNum $otherName $Key")

        binding.submit.setOnClickListener {
            if(binding.editMessage.text.isNotEmpty()){
                chatViewModel.submitMsg(Key, ChatData(meNum,binding.editMessage.text.toString(),"timeStamp"))
            }
        }

    }
}