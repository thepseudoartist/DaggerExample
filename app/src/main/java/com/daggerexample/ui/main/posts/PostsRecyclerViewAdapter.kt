package com.daggerexample.ui.main.posts

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.daggerexample.R
import com.daggerexample.models.Post

private const val TAG = "PostRecyclerViewAdapter"

class PostsRecyclerViewAdapter : RecyclerView.Adapter<PostsRecyclerViewAdapter.PostsViewHolder>() {
    class PostsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.title)

        fun bind(post: Post) {
            textView.text = post.title
        }
    }

    private var posts = ArrayList<Post>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_post_list_item, parent, false)

        return PostsViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    fun setPosts(posts: List<Post>) {
        this.posts = posts as ArrayList<Post>
        notifyDataSetChanged()
    }
}
