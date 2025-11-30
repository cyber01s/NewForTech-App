package com.newfortech.app.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Post(
    val id: String,
    val title: String,
    val excerpt: String,
    val content: String,
    val category: Category,
    val author: String,
    val date: Date,
    val imageUrl: String?,
    val readTime: String,
    val url: String
) : Parcelable

enum class Category {
    SECURITY,
    TECH_NEWS,
    TECH_TALK,
    OPINIONS
}
