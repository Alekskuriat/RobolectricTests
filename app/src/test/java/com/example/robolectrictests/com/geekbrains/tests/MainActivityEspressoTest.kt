package com.example.robolectrictests.com.geekbrains.tests

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.robolectrictests.R
import com.geekbrains.tests.view.search.MainActivity
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityEspressoTest {
    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun activitySearchEditText_IsDisplayed() {
        onView(withId(R.id.searchEditText)).check(matches(isDisplayed()))
    }

    @Test
    fun activitySearch_IsWorking() {
        with(onView(withId(R.id.searchEditText))){
            perform(click())
            perform(replaceText("algol"), closeSoftKeyboard())
            perform(pressImeActionButton())
        }
        onView(withId(R.id.totalCountTextView)).check(matches(withText("Number of results: 42")))
    }

    @After
    fun close() {
        scenario.close()
    }
}
