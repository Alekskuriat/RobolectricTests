package com.geekbrains.tests.presenter.search

import com.geekbrains.tests.presenter.PresenterContract
import com.geekbrains.tests.view.ViewContract

internal interface PresenterSearchContract<T : ViewContract> : PresenterContract<T> {
    fun searchGitHub(searchQuery: String)
}
