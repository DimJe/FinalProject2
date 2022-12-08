package org.techtown.finalproject2.fragment.notifications

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.techtown.finalproject2.Activity.ClubInfo
import org.techtown.finalproject2.Activity.InstrUser
import org.techtown.finalproject2.Activity.UserInfo
import org.techtown.finalproject2.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    private val binding get() = _binding!!
    private val notificationsViewModel : NotificationsViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        initView()

        return binding.root
    }
    private fun initView(){
        binding.userName.text = notificationsViewModel.api.user?.user_NCKNM
        binding.modifyUser.setOnClickListener {
            Intent(requireContext(), UserInfo::class.java).apply { startActivity(this) }
        }
        binding.club.setOnClickListener {
            Intent(requireContext(),ClubInfo::class.java).apply { startActivity(this) }
        }
        binding.instructor.setOnClickListener {
            Intent(requireContext(),InstrUser::class.java).apply { startActivity(this) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}