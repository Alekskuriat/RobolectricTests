package com.geekbrains.tests.presenter.search

import com.example.robolectrictests.com.geekbrains.tests.repository.RepositoryCallback
import com.example.robolectrictests.com.geekbrains.tests.repository.RepositoryContract
import com.geekbrains.tests.model.SearchResponse
import com.geekbrains.tests.view.search.ViewSearchContract
import retrofit2.Response

/**
 * В архитектуре MVP все запросы на получение данных адресуются в Репозиторий.
 * Запросы могут проходить через Interactor или UseCase, использовать источники
 * данных (DataSource), но суть от этого не меняется.
 * Непосредственно Презентер отвечает за управление потоками запросов и ответов,
 * выступая в роли регулировщика движения на перекрестке.
 */

internal class SearchPresenter internal constructor(
    private val repository: RepositoryContract
) : PresenterSearchContract<ViewSearchContract>,
    RepositoryCallback {

    private var viewContract: ViewSearchContract? = null

    override fun searchGitHub(searchQuery: String) {
        viewContract?.displayLoading(true)
        repository.searchGithub(searchQuery, this)
    }

    override fun handleGitHubResponse(response: Response<SearchResponse?>?) {
        viewContract?.displayLoading(false)
        if (response != null && response.isSuccessful) {
            val searchResponse = response.body()
            val searchResults = searchResponse?.searchResults
            val totalCount = searchResponse?.totalCount
            if (searchResults != null && totalCount != null) {
                viewContract?.displaySearchResults(
                    searchResults,
                    totalCount
                )
            } else {
                viewContract?.displayError("Search results or total count are null")
            }
        } else {
            viewContract?.displayError("Response is null or unsuccessful")
        }
    }

    override fun handleGitHubError() {
        viewContract?.displayLoading(false)
        viewContract?.displayError()
    }

    override fun onAttach(viewContract: ViewSearchContract) {
       this.viewContract = viewContract
    }

    override fun onDetach() {
        viewContract= null
    }
}
