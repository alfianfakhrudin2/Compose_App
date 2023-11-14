package com.example.jetshoes.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.jetshoes.R

data class Brand(
    @DrawableRes val imageCategory: Int,
    @StringRes val textCategory: Int
)

val dummyBrand = listOf(
    R.drawable.img_13 to R.string.category_all,
    R.drawable.img_14 to R.string.category_americano,
    R.drawable.img_15 to R.string.category_cappuccino,
    R.drawable.img_16 to R.string.category_espresso,
    R.drawable.img_17 to R.string.category_frappe,
    R.drawable.img_18 to R.string.category_latte,
    R.drawable.img_19 to R.string.category_macchiato,
    R.drawable.img_20 to R.string.category_mocha,
).map { Brand(it.first, it.second) }