package org.techtown.finalproject2.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.techtown.finalproject2.Chat.ChatRoomData
import org.techtown.finalproject2.databinding.RecyclerChatroomItemBinding
import java.text.SimpleDateFormat

class ChatRoomAdapter(var data : ArrayList<ChatRoomData>) : RecyclerView.Adapter<ChatRoomAdapter.ViewHolder>() {
    interface OnItemClickListener{
        fun onItemClick(b: RecyclerChatroomItemBinding, room: ChatRoomData, positon : Int)
    }

    private var listener : OnItemClickListener? = null
    fun setOnItemClickListener(listener : OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerChatroomItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(private val binding: RecyclerChatroomItemBinding) : RecyclerView.ViewHolder(binding.root){

        @SuppressLint("SetTextI18n")
        fun bind(item : ChatRoomData){

            @SuppressLint("SimpleDateFormat")
            val sdf = SimpleDateFormat("MM-dd hh:mm")

            binding.chatRoomName.text = item.chatName
            binding.lastMessage.text = item.lastMessage
            binding.timeStamp.text = sdf.format(item.timeStamp)
            val pos = adapterPosition
            if(pos!= RecyclerView.NO_POSITION)
            {
                itemView.setOnClickListener {
                    listener?.onItemClick(binding,item,pos)
                }
            }

        }
    }
}