package com.newfortech.app.data.repository

import com.newfortech.app.data.api.ApiService
import com.newfortech.app.data.model.Category
import com.newfortech.app.data.model.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class PostRepository(private val apiService: ApiService) {
    private val dateFormat = SimpleDateFormat(""MMMM d, yyyy"", Locale.US)
    
    suspend fun getPosts(page: Int, category: Category? = null): Result<List<Post>> = withContext(Dispatchers.IO) {
        try {
            kotlinx.coroutines.delay(800)
            Result.success(generateMockPosts(category, page))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getPost(id: String): Result<Post> = withContext(Dispatchers.IO) {
        try {
            kotlinx.coroutines.delay(500)
            Result.success(generateMockPosts().first { it.id == id })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    private fun generateMockPosts(category: Category? = null, page: Int = 1): List<Post> {
        val allPosts = listOf(
            Post(
                id = ""25527"",
                title = ""Do you hate Windows 11? Just be sure to do not fall for faux obtain websites."",
                excerpt = ""Tools faked, specialists warned. Fake Flyoobe Software Could Hide Malware."",
                content = ""Cybersecurity specialists warn about fake Windows 11 migration tools containing malware."",
                category = Category.SECURITY,
                author = ""Security Team"",
                date = dateFormat.parse(""November 7, 2025""),
                imageUrl = ""https://images.unsplash.com/photo-1581291518857-4e27b48ff24e?w=800"",
                readTime = ""3 min read"",
                url = ""https://newfortech.com/security/windows-11-fake-tools""
            ),
            Post(
                id = ""25572"",
                title = ""Grand Theft Auto 6 Delayed Again, But Will Still Ship in 2026"",
                excerpt = ""Rockstar Games confirmed another delay. GTA 6 will launch on November 19, 2026."",
                content = ""Rockstar announced GTA 6 delay to November 19, 2026 to ensure quality standards."",
                category = Category.TECH_NEWS,
                author = ""Gaming Reporter"",
                date = dateFormat.parse(""November 7, 2025""),
                imageUrl = ""https://images.unsplash.com/photo-1542751371-adc38448a05e?w=800"",
                readTime = ""2 min read"",
                url = ""https://newfortech.com/tech-news/gta-6-delayed""
            )
        )
        return allPosts.filter { category == null || it.category == category }
            .drop((page - 1) * 10).take(10)
    }
}
