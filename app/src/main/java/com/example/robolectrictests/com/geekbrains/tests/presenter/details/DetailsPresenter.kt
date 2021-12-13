package com.geekbrains.tests.presenter.details

import com.geekbrains.tests.view.ViewContract
import com.geekbrains.tests.view.details.ViewDetailsContract

internal class DetailsPresenter internal constructor(
    private var count: Int = 0
) : PresenterDetailsContract<ViewDetailsContract> {

    private var viewContract: ViewDetailsContract? = null
    private var thereIsView = false

    override fun setCounter(count: Int) {
        this.count = count
    }

    override fun onIncrement() {
        count++
        viewContract?.setCount(count)
    }

    override fun onDecrement() {
        count--
        viewContract?.setCount(count)
    }

    fun getViewContract() = viewContract

    override fun onAttach(viewContract: ViewDetailsContract) {
        this.viewContract = viewContract
    }

    override fun onDetach() {
        viewContract = null
    }
}
