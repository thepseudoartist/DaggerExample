package com.daggerexample.ui.main.posts

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daggerexample.R
import com.daggerexample.models.Post
import com.daggerexample.ui.main.Resource
import com.daggerexample.util.VerticalSpaceItemDecoration
import com.daggerexample.viewmodels.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

private const val TAG = "PostsFragment"

class PostsFragment : DaggerFragment() {
    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    @Inject
    lateinit var adapter: PostsRecyclerViewAdapter

    private lateinit var viewModel: PostsViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_posts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.recycler_view)
        viewModel = ViewModelProvider(this, providerFactory).get(PostsViewModel::class.java)

        initRecyclerView()
        subscribeObservers()
    }

    private fun subscribeObservers() {
        viewModel.observePosts().removeObservers(viewLifecycleOwner)
        viewModel.observePosts().observe(viewLifecycleOwner) {
            if (it != null) {
                when (it.status) {
                    Resource.Status.LOADING -> Log.d(TAG, "subscribeObservers: Loading Data.")
                    Resource.Status.SUCCESS -> {
                        Log.d(TAG, "subscribeObservers: Data Loaded.")
                        adapter.setPosts(it.data as ArrayList<Post>)
                    }
                    Resource.Status.ERROR -> Log.d(TAG, "subscribeObservers: ${it.message}")
                }
            }
        }
    }

    private fun initRecyclerView() {
        // WE CAN ADD THIS TWO OBJECTS AS DEPENDENCIES TOO :P
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.addItemDecoration(VerticalSpaceItemDecoration(15))
        recyclerView.adapter = adapter
    }
}
