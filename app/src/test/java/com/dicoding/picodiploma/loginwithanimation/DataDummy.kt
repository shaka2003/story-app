package com.dicoding.picodiploma.loginwithanimation

import com.dicoding.picodiploma.loginwithanimation.data.api.response.ListStoryItem
import com.dicoding.picodiploma.loginwithanimation.data.api.response.StoryResponse

object DataDummy {

    fun generateDummyStoryResponse(): List<ListStoryItem> {
        val items: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100) {
            val story = ListStoryItem(
                "photo_url_$i",
                "2023-10-30T12:00:00",
                "Story $i",
                "Description $i",
                "lon_$i",
                "$i",
                "lat_$i"
            )
            items.add(story)
        }
        return items
    }
}