package org.techtown.finalproject2.adapter

import android.annotation.SuppressLint
import android.text.Layout
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import org.techtown.finalproject2.Chat.ChatData
import org.techtown.finalproject2.R
import org.techtown.finalproject2.databinding.RecyclerChatItemBinding
import java.text.SimpleDateFormat

class ChatAdapter(val chatDataList:ArrayList<ChatData>, val Other : String,val Me : String) : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerChatItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(chatDataList[position])
    }

    override fun getItemCount(): Int = chatDataList.size

    @SuppressLint("NotifyDataSetChanged")
    fun addChat(chatData: ChatData){
        chatDataList.add(chatData)
        notifyItemChanged(itemCount-1)
    }
    fun getLastMsg() : ChatData = chatDataList.last()


    inner class ViewHolder(private val binding: RecyclerChatItemBinding) : RecyclerView.ViewHolder(binding.root){

        @SuppressLint("SimpleDateFormat")
        val sdf = SimpleDateFormat("hh:mm")

        @SuppressLint("SetTextI18n")
        fun bind(item : ChatData){
            when (item.user) {
                Me -> {
                    Log.d("태그", "bind: called")
                    binding.linear.setHorizontalGravity(Gravity.END)
                    binding.chatMsg.text = item.msg
                    binding.chatMsg.setBackgroundResource(R.drawable.chat_bubble_me)
                    binding.chatTime.text = sdf.format(item.timeStamp)

                }
                else -> {
                    binding.linear.setHorizontalGravity(Gravity.START)
                    binding.chatMsg.text = item.msg
                    binding.chatMsg.setBackgroundResource(R.drawable.chat_bubble_you)
                    binding.chatTime.text = sdf.format(item.timeStamp)
                }
            }
        }
    }
}