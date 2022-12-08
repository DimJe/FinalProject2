package org.techtown.finalproject2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.techtown.finalproject2.API.ClubData
import org.techtown.finalproject2.API.User
import org.techtown.finalproject2.databinding.RecyclerClubItemBinding
import org.techtown.finalproject2.databinding.RecyclerInstrItemBinding

class ClubAdapter(val clubData : ArrayList<ClubData>) : RecyclerView.Adapter<ClubAdapter.ViewHolder>() {


    interface OnItemClickListener{
        fun onItemClick(b: RecyclerClubItemBinding, club : ClubData, positon : Int)
    }
    private var listener : OnItemClickListener? = null

    fun setOnItemClickListener(listener : OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
        = ViewHolder(RecyclerClubItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(clubData[position])
    }

    override fun getItemCount(): Int  = clubData.size
    inner class ViewHolder(private val binding : RecyclerClubItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: ClubData){

            binding.clubName.text = item.club_NM
            binding.maxNum.text = item.club_MAX_NOP.toString()
            binding.region.text = item.club_RGN
            binding.sport.text = item.spt_EVT
            itemView.setOnClickListener {
                listener?.onItemClick(binding,item,adapterPosition)
            }
        }
    }
}