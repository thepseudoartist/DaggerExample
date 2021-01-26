package com.daggerexample.ui.main.posts

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.daggerexample.SessionManager
import com.daggerexample.models.Post
import com.daggerexample.network.main.MainAPI
import com.daggerexample.ui.main.Resource
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

private const val TAG = "PostsViewModel"

class PostsViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val mainAPI: MainAPI
) : ViewModel() {

    private lateinit var posts: MediatorLiveData<Resource<List<Post>>>

    fun observePosts(): LiveData<Resource<List<Post>>> {
        posts = MediatorLiveData()
        posts.value = Resource.loading(null)

        val source: LiveData<Resource<List<Post>>> = LiveDataReactiveStreams.fromPublisher(
            mainAPI.getPostsFromUser(
                // DO NOT DO THIS, INSTEAD OBSERVE THE DATA
                sessionManager.getAuthUser().value?.data?.id
                    ?: throw NullPointerException("Null UserID.")
            )
                .onErrorReturn {
                    Log.e(TAG, "observePosts: ", it)

                    val posts = ArrayList<Post>()
                    posts.add(Post(null, -1, null, null))
                    return@onErrorReturn posts
                }
                .map {
                    if (it.isNotEmpty() && it[0].id == -1) {
                        return@map Resource.error("Something went wrong.", null as List<Post>?)
                    }

                    return@map Resource.success(it)
                }
                .subscribeOn(Schedulers.io())
        )

        posts.addSource(source) {
            posts.value = it
            posts.removeSource(source)
        }

        return posts
    }

    init {
        Log.d(TAG, "PostsViewModel: ViewModel created.")
    }
}