package com.example.jetshoes.di

import com.example.jetshoes.data.RewardRepository


object Injection {
    fun provideRepository(): RewardRepository {
        return RewardRepository.getInstance()
    }
}