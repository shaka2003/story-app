package com.dicoding.picodiploma.loginwithanimation.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dicoding.picodiploma.loginwithanimation.data.api.response.ListStoryItem
import com.dicoding.picodiploma.loginwithanimation.data.api.response.StoryResponse
import com.dicoding.picodiploma.loginwithanimation.data.api.retrofit.ApiService

class StoryPagingSource(private val apiService: ApiService) : PagingSource<Int, ListStoryItem>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, ListStoryItem>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListStoryItem> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getListStory(position, params.loadSize)
            val listStory = responseData.listStory ?: emptyList() // Handle null listStory
            LoadResult.Page(
                data = listStory.filterNotNull(), // Filter out null elements
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (listStory.isEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }
}