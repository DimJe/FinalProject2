package org.techtown.finalproject2.fragment.home

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.techtown.finalproject2.API.PostData
import org.techtown.finalproject2.AddPost
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
    private lateinit var adapter : ListAdapter
    private val searchFlag = false
    private val homeViewModel : HomeViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        homeViewModel.api.getStadium()
        homeViewModel.api.getPost()
        val recycler: RecyclerView = binding.recycler
        homeViewModel.api.postDataList.observe(viewLifecycleOwner) {
            Log.d("태그", "post: observed")
            adapter = ListAdapter(it)
            adapter.setStadmList(homeViewModel.api.stadmList)
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
            if(recycler.itemDecorationCount==0) recycler.addItemDecoration(VerticalItemDecorator(30))


        }
        initView()

        return root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    class VerticalItemDecorator(private val divHeight : Int):RecyclerView.ItemDecoration(){
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
    private fun initView(){
        binding.bar.toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.add -> {
                    Log.d("태그", "add 클릭")
                    Intent(context,AddPost::class.java).run { startActivity(this) }

                }
                R.id.filter -> {
                    Log.d("태그", "필터 클릭")
                    binding.linear.visibility = if(binding.linear.visibility==View.GONE) View.VISIBLE else View.GONE
                }
            }
            return@setOnMenuItemClickListener true
        }

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextChange(p0: String): Boolean {
                adapter.filter.filter(p0)
                return false
            }

            override fun onQueryTextSubmit(p0: String): Boolean {
                return false
            }
        })
        binding.refresh.setOnRefreshListener {
            binding.refresh.isRefreshing = false
            homeViewModel.api.getPost()
        }
    }
}