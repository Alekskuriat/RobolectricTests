package com.geekbrains.tests.presenter

import com.geekbrains.tests.view.ViewContract

interface PresenterContract<T : ViewContract> {
    fun onAttach(viewContract: T)
    fun onDetach()
}
