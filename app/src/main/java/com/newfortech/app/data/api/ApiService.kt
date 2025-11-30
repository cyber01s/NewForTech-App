package com.newfortech.app.data.api

import com.newfortech.app.data.model.Post
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET(""/wp-json/wp/v2/posts"")
    suspend fun getPosts(
        @Query(""page"") page: Int = 1,
        @Query(""per_page"") perPage: Int = 10,
        @Query(""categories"") categoryId: Int? = null
    ): Response<List<Post>>
    
    @GET(""/wp-json/wp/v2/posts/{id}"")
    suspend fun getPost(@Path(""id"") id: String): Response<Post>
}

data class CategoryResponse(
    val id: Int,
    val name: String,
    val slug: String
)
