package com.newfortech.app.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newfortech.app.data.model.Category
import com.newfortech.app.data.model.Post
import com.newfortech.app.data.repository.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val repository = PostRepository(NewForTechApp.apiService)
    
    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts
    
    private val _loadingState = MutableStateFlow<LoadingState>(LoadingState.Success)
    val loadingState: StateFlow<LoadingState> = _loadingState
    
    fun loadPosts() {
        viewModelScope.launch {
            _loadingState.value = LoadingState.Loading
            val result = repository.getPosts(1)
            result.onSuccess { posts ->
                _posts.value = posts
                _loadingState.value = LoadingState.Success
            }.onFailure { error ->
                _loadingState.value = LoadingState.Error(error.message ?: "Unknown error")
            }
        }
    }
    
    fun refreshPosts() = loadPosts()
}

sealed class LoadingState {
    object Loading : LoadingState()
    object Success : LoadingState()
    data class Error(val message: String) : LoadingState()
}
