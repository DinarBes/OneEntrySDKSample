package com.example.oneentrysdksample.model

import com.example.oneentry.model.products.OneEntryProduct

data class CategoryModel(
    val id: Int,
    val products: List<OneEntryProduct>
)
