package com.example.robolectrictests.com.geekbrains.tests

import com.geekbrains.tests.presenter.details.DetailsPresenter
import com.geekbrains.tests.view.details.ViewDetailsContract
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DetailsPresenterTest {

    private lateinit var presenter: DetailsPresenter

    @Mock
    private lateinit var viewContract: ViewDetailsContract

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = DetailsPresenter()
    }

    @Test //проверяем что у View вызывается метод setCount(), когда к View прикреплен Presenter
    fun onIncrement_Test_AttachedPresenter() {
        presenter.onAttach(viewContract)
        presenter.onIncrement()
        Mockito.verify(viewContract, Mockito.times(1)).setCount(1)
    }

    @Test //проверяем что у View НЕ вызывается метод setCount(), когда к View НЕ прикреплен Presenter
    fun onIncrement_Test_DetachedPresenter() {
        presenter.onAttach(viewContract)
        presenter.onDetach()
        presenter.onIncrement()
        Mockito.verify(viewContract, Mockito.never()).setCount(anyInt())
    }

    @Test //проверяем что у View вызывается метод setCount(), когда к View прикреплен Presenter
    fun onDecrement_Test_AttachedPresenter() {
        presenter.onAttach(viewContract)
        presenter.onDecrement()
        Mockito.verify(viewContract, Mockito.times(1)).setCount(-1)
    }

    @Test //проверяем что у View НЕ вызывается метод setCount(), когда к View НЕ прикреплен Presenter
    fun onDecrement_Test_DetachedPresenter() {
        presenter.onAttach(viewContract)
        presenter.onDetach()
        presenter.onDecrement()
        Mockito.verify(viewContract, Mockito.never()).setCount(anyInt())
    }

    @Test //проверяем метод onAttach() у Presenter
    fun onAttach_Test() {
        presenter.onAttach(viewContract)
        assertEquals(viewContract, presenter.getViewContract())
    }

    @Test //проверяем метод onDetach() у Presenter
    fun onDetach_Test() {
        presenter.onAttach(viewContract)
        presenter.onDetach()
        assertEquals(null, presenter.getViewContract())
    }



}