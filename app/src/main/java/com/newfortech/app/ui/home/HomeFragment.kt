package com.newfortech.app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.newfortech.app.R
import com.newfortech.app.data.model.Post
import com.newfortech.app.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var postAdapter: PostAdapter
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupSwipeRefresh()
        observeData()
        if (savedInstanceState == null) viewModel.loadPosts()
    }
    
    private fun setupRecyclerView() {
        postAdapter = PostAdapter { post -> navigateToDetail(post) }
        binding.recyclerView.apply {
            adapter = postAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }
    
    private fun setupSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener { viewModel.refreshPosts() }
    }
    
    private fun observeData() {
        lifecycleScope.launch {
            viewModel.posts.collectLatest { posts -> postAdapter.submitList(posts) }
        }
        lifecycleScope.launch {
            viewModel.loadingState.collectLatest { state ->
                when (state) {
                    is LoadingState.Loading -> binding.shimmerViewContainer.visibility = View.VISIBLE
                    is LoadingState.Success -> {
                        binding.shimmerViewContainer.visibility = View.GONE
                        binding.swipeRefresh.isRefreshing = false
                    }
                    is LoadingState.Error -> {
                        binding.shimmerViewContainer.visibility = View.GONE
                        binding.swipeRefresh.isRefreshing = false
                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
    
    private fun navigateToDetail(post: Post) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(post)
        findNavController().navigate(action)
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
