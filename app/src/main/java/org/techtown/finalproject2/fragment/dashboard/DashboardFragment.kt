package org.techtown.finalproject2.fragment.dashboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout.VERTICAL
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.techtown.finalproject2.Chat.ChatActivity
import org.techtown.finalproject2.Chat.ChatRoomData
import org.techtown.finalproject2.adapter.ChatRoomAdapter
import org.techtown.finalproject2.databinding.FragmentDashboardBinding
import org.techtown.finalproject2.databinding.RecyclerChatroomItemBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter : ChatRoomAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel : DashboardViewModel by viewModel()
        Log.d("태그", "onCreateView:called ")

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        dashboardViewModel.data.observe(viewLifecycleOwner){
            Log.d("태그", "onCreateView: observed")
            it.forEach {
                Log.d("태그", "onCreateView:${it.chatName} ")
            }
            adapter = ChatRoomAdapter(it)
            adapter.setOnItemClickListener(object : ChatRoomAdapter.OnItemClickListener{
                override fun onItemClick(
                    b: RecyclerChatroomItemBinding,
                    room: ChatRoomData,
                    positon: Int
                ) {
                    Intent(context,ChatActivity::class.java).apply {
                        putExtra("Me",room.me)
                        putExtra("Other",room.other)
                        putExtra("Name",room.chatName)
                        putExtra("Key",room.chatId)
                        putExtra("meCheck",room.meCheck)
                        putExtra("otherCheck",room.otherCheck)
                    }.run { startActivity(this) }
                }
            })
            adapter.setOnItemLongClickListener(object : ChatRoomAdapter.OnItemLongClick{
                override fun onLongClick(
                    b: RecyclerChatroomItemBinding,
                    room: ChatRoomData,
                    positon: Int
                ) {
                    Log.d("태그", "onLongClick: long")
                    AlertDialog.Builder(requireContext())
                        .setTitle("채팅방 퇴장")
                        .setMessage("채팅방을 나가시겠습니까?")
                        .setPositiveButton("예"){_,_ ->
                            dashboardViewModel.deleteChatRoom(room.me,room.other,room.chatId)
                        }
                        .show()
                }

            })
            binding.recycler.adapter = adapter
            binding.recycler.layoutManager = LinearLayoutManager(context)
            if(binding.recycler.itemDecorationCount == 0) binding.recycler.addItemDecoration(DividerItemDecoration(context,VERTICAL))
        }

        dashboardViewModel.getChatRoom()

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}