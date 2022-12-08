package org.techtown.finalproject2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.techtown.finalproject2.API.User
import org.techtown.finalproject2.databinding.RecyclerInstrItemBinding

class InstrAdapter(val instrData : ArrayList<User>) : RecyclerView.Adapter<InstrAdapter.ViewHolder>() {


    interface OnItemClickListener{
        fun onItemClick(b: RecyclerInstrItemBinding, user : User, positon : Int)
    }
    private var listener : OnItemClickListener? = null

    fun setOnItemClickListener(listener : OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
        = ViewHolder(RecyclerInstrItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(instrData[position])
    }

    override fun getItemCount(): Int = instrData.size


    inner class ViewHolder(private val binding : RecyclerInstrItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: User){
            "${item.user_NCKNM}   (${item.user_GNDR}/${item.user_AGE})".apply {
                binding.profile.text = this
            }
            binding.region.text = item.user_RGN
            itemView.setOnClickListener {
                listener?.onItemClick(binding,item,adapterPosition)
            }
        }
    }
}