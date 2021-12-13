package com.example.robolectrictests.com.geekbrains.tests.repository

import com.geekbrains.tests.model.SearchResponse
import retrofit2.Response

class FakeRepository : RepositoryContract {
    override fun searchGithub(query: String, callback: RepositoryCallback) {
        callback.handleGitHubResponse(Response.success(SearchResponse(42, listOf())))
    }
}