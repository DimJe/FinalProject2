package org.techtown.finalproject2.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.techtown.finalproject2.PostData
import org.techtown.finalproject2.databinding.RecyclerViewItemBinding

class ListAdapter(private val data : ArrayList<PostData>) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(private val binding: RecyclerViewItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item : PostData){
            binding.image.setImageResource(item.img)
            binding.name.text = item.title
            binding.info.text = item.content
            binding.tag1.text = item.tag1
            binding.tag2.text = item.tag2
            binding.tag3.text = item.tag3

        }
    }
}