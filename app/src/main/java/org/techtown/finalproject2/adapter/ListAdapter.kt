package org.techtown.finalproject2.adapter


import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import org.techtown.finalproject2.API.PostData
import org.techtown.finalproject2.R
import org.techtown.finalproject2.databinding.RecyclerViewItemBinding

class ListAdapter(var data : ArrayList<PostData>) : RecyclerView.Adapter<ListAdapter.ViewHolder>(),Filterable {

    interface OnItemClickListener{
        fun onItemClick(b:RecyclerViewItemBinding, post: PostData, positon : Int)
    }

    private var listener : OnItemClickListener? = null
    var filteredData = ArrayList<PostData>()
    var itemFilter = ItemFilter()
    fun setOnItemClickListener(listener : OnItemClickListener) {
        this.listener = listener
    }

    init {
        filteredData.addAll(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(filteredData[position])
    }

    override fun getItemCount(): Int = filteredData.size

    override fun getFilter(): Filter {
        return itemFilter
    }

    inner class ViewHolder(private val binding: RecyclerViewItemBinding) : RecyclerView.ViewHolder(binding.root){

        @SuppressLint("SetTextI18n")
        fun bind(item : PostData){
            binding.image.setImageResource(R.drawable.ic_launcher_foreground)
            binding.title.text = item.pst_NM
            binding.where.text = if(item.stadm_NO_FK==1) "흥업 체육관" else "무실 체육관"
            binding.time.text = item.match_DT
            val pos = adapterPosition
            if(pos!= RecyclerView.NO_POSITION)
            {
                itemView.setOnClickListener {
                    listener?.onItemClick(binding,item,pos)
                }
            }

        }
    }
    inner class ItemFilter : Filter(){

        override fun performFiltering(p0: CharSequence?): FilterResults {
            val filterString = p0.toString()
            val results = FilterResults()

            val filteredList = ArrayList<PostData>()
            if(filterString.isEmpty() || filterString.isBlank()){

                results.values = data
                results.count = data.size

                return results
            }
            else{
                for(post in data){
                    if(post.pst_NM.trim().contains(filterString)){
                        filteredList.add(post)
                    }
                }
                results.values = filteredList
                results.count = filteredList.size

                return results
            }
        }

        @SuppressLint("NotifyDataSetChanged")
        override fun publishResults(p0: CharSequence?, p1: FilterResults) {

            filteredData.clear()
            filteredData.addAll(p1.values as ArrayList<PostData>)
            notifyDataSetChanged()
        }
    }
}