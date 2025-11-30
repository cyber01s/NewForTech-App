package com.newfortech.app.ui.detail

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.newfortech.app.databinding.FragmentDetailBinding
import java.text.SimpleDateFormat

class DetailFragment : Fragment(R.layout.fragment_detail) {
    private val args: DetailFragmentArgs by navArgs()
    private val viewModel: DetailViewModel by viewModels()
    private val dateFormat = SimpleDateFormat(""MMMM d, yyyy"", java.util.Locale.US)
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDetailBinding.bind(view)
        val post = args.post
        
        binding.titleTextView.text = post.title
        binding.dateTextView.text = dateFormat.format(post.date)
        binding.authorTextView.text = post.author
        
        post.imageUrl?.let { url ->
            Glide.with(requireContext()).load(url).into(binding.featuredImageView)
        }
        
        binding.contentWebView.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            loadData(post.content, ""text/html"", ""UTF-8"")
        }
    }
}
