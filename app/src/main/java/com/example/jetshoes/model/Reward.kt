package com.example.jetshoes.model

data class Reward(
    val id: Long,
    val image: Int,
    val title: String,
    val size: Int,
    val color: String,
    val description: String,
    val requiredPoint: Int,
)