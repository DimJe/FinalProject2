package org.techtown.finalproject2.Chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.techtown.finalproject2.Notification.NotificationBody
import org.techtown.finalproject2.Notification.NotificationRetrofit
import org.techtown.finalproject2.R
import org.techtown.finalproject2.adapter.ChatAdapter
import org.techtown.finalproject2.databinding.ActivityChatBinding
import org.techtown.finalproject2.databinding.DialogEstimateBinding

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

        setSupportActionBar(binding.chatName)

        meNum = intent.getStringExtra("Me").toString()
        otherNum = intent.getStringExtra("Other").toString()
        otherName = intent.getStringExtra("Name").toString()
        Key = intent.getStringExtra("Key").toString()
        chatViewModel.meCheck = intent.getBooleanExtra("meCheck",false)
        chatViewModel.otherCheck = intent.getBooleanExtra("otherCheck",false)

        chatViewModel.checkPermission(Key)
        binding.chatName.title = otherName
        chatViewModel.msgList.observe(this){
            Log.d("태그", "onCreate:chatting  observe")
            it.forEach {
                Log.d("태그", "onCreate: ${it.msg}")
            }
            adapter = ChatAdapter(it,otherName,meNum)
            adapter.setHasStableIds(true)
            binding.chatRecycler.adapter = adapter
            binding.chatRecycler.layoutManager = LinearLayoutManager(this)
            if(adapter.itemCount != 0)binding.chatRecycler.smoothScrollToPosition(adapter.itemCount-1)
            chatViewModel.updateMsg(Key)

        }
        chatViewModel.newMsg.observe(this){
            adapter.addChat(it)
            binding.chatRecycler.smoothScrollToPosition(adapter.itemCount-1)
            Log.d("태그", "onCreate new: ${it.msg}")
        }
        chatViewModel.getMsgList(Key)

        Log.d("태그", "onCreate:$meNum $otherNum $otherName $Key")

        binding.submit.setOnClickListener {
            if(binding.editMessage.text.isNotEmpty()){
                val timeStamp = System.currentTimeMillis()
                chatViewModel.submitMsg(Key, ChatData(meNum,binding.editMessage.text.toString(),timeStamp))
                chatViewModel.updateChatRoomData(Key,binding.editMessage.text.toString(),timeStamp,meNum,otherNum)
                chatViewModel.sendNotification(otherNum,binding.editMessage.text.toString())
                binding.editMessage.text.clear()


            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.menu_chat,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.sure -> {
                AlertDialog.Builder(this)
                    .setTitle("경기 종료 확인")
                    .setMessage("경기 종료에 동의하십니까?\n상대방 평가를 위해 모두가 경기 종료에 동의해야합니다.")
                    .setPositiveButton("예") { _, _ -> chatViewModel.finishGame(Key,meNum,otherNum)}
                    .setNegativeButton("아니요"){_,_ -> }
                    .show()

            }
            R.id.estimate -> {
                if(chatViewModel.meCheck!! && chatViewModel.otherCheck!!){
                    val dialogBinding = DialogEstimateBinding.inflate(layoutInflater)
                    var manner = 0
                    var skill = 0
                    dialogBinding.manner.setOnCheckedChangeListener { _, id ->
                        when(id){
                            R.id.m1 -> manner = 1
                            R.id.m2 -> manner = 2
                            R.id.m3 -> manner = 3
                            R.id.m4 -> manner = 4
                            R.id.m5 -> manner = 5
                        }
                    }
                    dialogBinding.skill.setOnCheckedChangeListener { _, id ->
                        when(id){
                            R.id.s1 -> skill = 1
                            R.id.s2 -> skill = 2
                            R.id.s3 -> skill = 3
                            R.id.s4 -> skill = 4
                            R.id.s5 -> skill = 5

                        }
                    }
                    AlertDialog.Builder(this)
                        .setView(dialogBinding.root)
                        .setTitle("상대방 평가")
                        .setPositiveButton("확인"){_,_ ->
                            if(skill == 0 && manner == 0){
                                Toast.makeText(this, "점수를 평가해주세요", Toast.LENGTH_SHORT).show()
                            }
                            else{
                                Log.d("태그", "onOptionsItemSelected: ${manner} ${skill}")
                                chatViewModel.estimateUser(Key,meNum,otherNum,manner,skill)
                            }
                        }
                        .setNegativeButton("취소"){_,_ -> }
                        .show()
                }
                else Toast.makeText(this, "아직 모두가 경기 종료에 동의하지 않았습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        return true
    }
}