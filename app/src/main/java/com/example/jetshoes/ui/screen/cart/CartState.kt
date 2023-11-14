package com.example.jetshoes.ui.screen.cart

import com.example.jetshoes.model.OrderReward

data class CartState(
    val orderReward: List<OrderReward>,
    val totalRequiredPoint: Int
)