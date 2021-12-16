package com.example.robolectrictests.com.geekbrains.tests.repository

interface RepositoryContract {
    fun searchGithub(
        query: String,
        callback: RepositoryCallback
    )
}