package org.techtown.finalproject2

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.techtown.finalproject2.API.Api
import org.techtown.finalproject2.Chat.ChatViewModel
import org.techtown.finalproject2.fragment.dashboard.DashboardViewModel
import org.techtown.finalproject2.fragment.home.HomeViewModel
import org.techtown.finalproject2.fragment.notifications.NotificationsViewModel

var module = module{
    single {Api() }
    viewModel { HomeViewModel(get())}
    viewModel { DashboardViewModel(get()) }
    viewModel { NotificationsViewModel() }
    viewModel { ChatViewModel()}
}