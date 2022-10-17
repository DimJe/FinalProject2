package org.techtown.finalproject2.adapter


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.techtown.finalproject2.API.PostData
import org.techtown.finalproject2.databinding.RecyclerViewItemBinding

class ListAdapter(private val data : ArrayList<PostData>) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    interface OnItemClickListener{
        fun onItemClick(b:RecyclerViewItemBinding, post: PostData, positon : Int)
    }

    private var listener : OnItemClickListener? = null
    fun setOnItemClickListener(listener : OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(private val binding: RecyclerViewItemBinding) : RecyclerView.ViewHolder(binding.root){

        @SuppressLint("SetTextI18n")
        fun bind(item : PostData){
            binding.image.setImageResource(item.img)
            binding.title.text = item.title
            binding.where.text = "장소 : ${item.where}"
            binding.time.text = when(item.type){
                "b3:3" -> "경기 및 시간 : ${item.date}시(농구 3 : 3)"
                "b5:5" -> "경기 및 시간 : ${item.date}시(농구 5 : 5)"
                else -> "잘못 된 입력"
            }
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