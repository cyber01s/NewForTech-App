package com.newfortech.app.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.newfortech.app.data.model.Post
import com.newfortech.app.databinding.ItemPostCardBinding
import java.text.SimpleDateFormat

class PostAdapter(private val onPostClick: (Post) -> Unit) : ListAdapter<Post, PostAdapter.PostViewHolder>(PostDiffCallback()) {
    private val dateFormat = SimpleDateFormat(""MMM d, yyyy"", java.util.Locale.US)
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = ItemPostCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    
    inner class PostViewHolder(private val binding: ItemPostCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) {
            binding.apply {
                titleTextView.text = post.title
                excerptTextView.text = post.excerpt
                dateTextView.text = dateFormat.format(post.date)
                categoryTextView.text = post.category.name.replace(""_"", "" "")
                authorTextView.text = post.author
                readTimeTextView.text = post.readTime
                
                post.imageUrl?.let { url ->
                    Glide.with(itemView.context).load(url).centerCrop().into(featuredImageView)
                }
                
                root.setOnClickListener { onPostClick(post) }
            }
        }
    }
    
    class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean = oldItem == newItem
    }
}
