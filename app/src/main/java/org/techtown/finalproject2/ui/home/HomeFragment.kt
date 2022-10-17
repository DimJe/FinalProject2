package org.techtown.finalproject2.ui.home

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.techtown.finalproject2.API.PostData
import org.techtown.finalproject2.PostDetail
import org.techtown.finalproject2.R
import org.techtown.finalproject2.adapter.ListAdapter
import org.techtown.finalproject2.databinding.FragmentHomeBinding
import org.techtown.finalproject2.databinding.RecyclerViewItemBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recycler: RecyclerView = binding.recycler
        homeViewModel.data.observe(viewLifecycleOwner) {
            val adapter = ListAdapter(it)
            adapter.setOnItemClickListener(object : ListAdapter.OnItemClickListener{
                override fun onItemClick(b: RecyclerViewItemBinding, post: PostData, positon: Int) {
                    Log.d("태그", "onItemClick: call")
                    Intent(context,PostDetail::class.java).apply {
                        putExtra("post",post)
                    }.run { startActivity(this) }
                }
            })
            recycler.adapter = adapter
            recycler.layoutManager = LinearLayoutManager(context)
            recycler.addItemDecoration(VerticalItemDecorator(30))
        }
        binding.bar.toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.add -> {
                    Log.d("태그", "add 클릭")//Post add

                }
                R.id.filter -> {
                    Log.d("태그", "필터 클릭")
                }
            }
            return@setOnMenuItemClickListener true
        }
        return root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    inner class VerticalItemDecorator(private val divHeight : Int):RecyclerView.ItemDecoration(){
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            outRect.top = divHeight
            outRect.bottom = divHeight
        }
    }
}