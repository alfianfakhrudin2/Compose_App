package com.example.jetshoes.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jetshoes.ui.screen.cart.CartViewModel
import com.example.jetshoes.ui.screen.detail.DetailRewardViewModel
import com.example.jetshoes.ui.screen.home.HomeViewModel
import com.example.jetshoes.data.RewardRepository

class ViewModelFactory(private val repository: RewardRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailRewardViewModel::class.java)) {
            return DetailRewardViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            return CartViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}