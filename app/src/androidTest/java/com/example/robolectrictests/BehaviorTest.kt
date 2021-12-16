package com.example.robolectrictests

import android.content.Context
import android.content.Intent
import android.widget.TextView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.base.MainThread
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.*
import androidx.test.uiautomator.Until.findObject
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 18)
class BehaviorTest {

    //Класс UiDevice предоставляет доступ к вашему устройству.
    //Именно через UiDevice вы можете управлять устройством, открывать приложения
    //и находить нужные элементы на экране
    private val uiDevice: UiDevice = UiDevice.getInstance(getInstrumentation())

    //Контекст нам понадобится для запуска нужных экранов и получения packageName
    private val context = ApplicationProvider.getApplicationContext<Context>()

    //Путь к классам нашего приложения, которые мы будем тестировать
    private val packageName = context.packageName

    @Before
    fun setup() {

        uiDevice.apply {
            pressHome()
            repeat(REPEAT_SWIPE) {
                swipe(START_X, START_Y, END_X, END_Y, STEPS_SWIPE)
            }
        }

        val testApp: UiObject = uiDevice.findObject(UiSelector().description("Tests"))
        testApp.clickAndWaitForNewWindow()

        uiDevice.wait(Until.hasObject(By.pkg(packageName)), TIMEOUT)
    }

    @Test //проверяем, что приложение запустилось
    fun test_MainActivityIsStarted() {

        val editText = uiDevice.findObject(By.res(packageName, "searchEditText"))
        Assert.assertNotNull(editText)
    }

    //Убеждаемся, что поиск работает как ожидается
    @Test
    fun test_SearchIsPositive() {

        val editText = uiDevice.findObject(By.res(packageName, "searchEditText"))
        val buttonSearch = uiDevice.findObject(By.res(packageName, "searchButton"))

        editText.text = "UiAutomator"
        buttonSearch.click()

        val changedText =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "totalCountTextView")),
                TIMEOUT
            )

        Assert.assertEquals(changedText.text.toString(), "Number of results: 42")
    }

    //Убеждаемся, что DetailsScreen открывается
    @Test
    fun test_OpenDetailsScreen() {

        val toDetails = uiDevice.findObject(By.res(packageName, "toDetailsActivityButton"))
        toDetails.click()

        val changedText =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "totalCountTextView")),
                TIMEOUT
            )
        Assert.assertEquals(changedText.text, "Number of results: 0")
    }

    @Test //убеждаемся что на DetailsScreen отображается корректное количество репозиториев
    fun test_Search_OpenDetailsScreen() {
        val editText = uiDevice.findObject(By.res(packageName, "searchEditText"))
        val buttonSearch = uiDevice.findObject(By.res(packageName, "searchButton"))
        val toDetails = uiDevice.findObject(By.res(packageName, "toDetailsActivityButton"))

        editText.text = TEST_STRING
        buttonSearch.click()
        toDetails.click()

        val changedText =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "totalCountTextView")),
                TIMEOUT
            )
        Assert.assertEquals(changedText.text, "Number of results: 42")
    }

    @Test //проверяем работу кнопки Increment
    fun test_Search_OpenDetailsScreen_ClickIncrementButton_AddFive() {
        val editText = uiDevice.findObject(By.res(packageName, "searchEditText"))
        val buttonSearch = uiDevice.findObject(By.res(packageName, "searchButton"))
        val toDetails = uiDevice.findObject(By.res(packageName, "toDetailsActivityButton"))

        editText.text = TEST_STRING
        buttonSearch.click()
        toDetails.click()

        val positiveButton = uiDevice.wait(
            Until.findObject(By.res(packageName, "incrementButton")),
            TIMEOUT
        )

        repeat(REPEAT_CLICK_FIVE) {
            positiveButton.click()
        }
        val changedText =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "totalCountTextView")),
                TIMEOUT
            )
        Assert.assertEquals(changedText.text, "Number of results: 47")
    }

    @Test //проверяем работу кнопки Decrement
    fun test_Search_OpenDetailsScreen_ClickDecrementButton_SubFive() {
        val editText = uiDevice.findObject(By.res(packageName, "searchEditText"))
        val buttonSearch = uiDevice.findObject(By.res(packageName, "searchButton"))
        val toDetails = uiDevice.findObject(By.res(packageName, "toDetailsActivityButton"))

        editText.text = TEST_STRING
        buttonSearch.click()
        toDetails.click()

        val negativeButton = uiDevice.wait(
            Until.findObject(By.res(packageName, "decrementButton")),
            TIMEOUT
        )
        repeat(REPEAT_CLICK_FIVE) {
            negativeButton.click()
        }
        val changedText =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "totalCountTextView")),
                TIMEOUT
            )
        Assert.assertEquals(changedText.text, "Number of results: 37")
    }

    @Test //проверяем, что если свернуть приложение, то данные на экране сохраняются
    fun test_Search_ClickHome_OpenApp() {
        val editText = uiDevice.findObject(By.res(packageName, "searchEditText"))
        val buttonSearch = uiDevice.findObject(By.res(packageName, "searchButton"))

        editText.text = TEST_STRING
        buttonSearch.click()

        val changedText =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "totalCountTextView")),
                TIMEOUT
            )
        Assert.assertEquals(changedText.text, "Number of results: 42")
        uiDevice.pressHome()
        val testApp: UiObject = uiDevice.findObject(UiSelector().description("Tests"))
        testApp.clickAndWaitForNewWindow()
        Assert.assertEquals(changedText.text, "Number of results: 42")
    }


    @Test //проверяем, что если закрыть приложение и запустить его вновь, то она открывается заново
    fun test_Search_ClickBack_OpenApp() {
        val editText = uiDevice.findObject(By.res(packageName, "searchEditText"))
        val buttonSearch = uiDevice.findObject(By.res(packageName, "searchButton"))

        editText.text = TEST_STRING
        buttonSearch.click()

        var changedText =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "totalCountTextView")),
                TIMEOUT
            )
        Assert.assertEquals(changedText.text, "Number of results: 42")
        uiDevice.pressBack()
        val testApp: UiObject = uiDevice.findObject(UiSelector().description("Tests"))
        testApp.clickAndWaitForNewWindow()

        changedText =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "totalCountTextView")),
                TIMEOUT
            )
        Assert.assertNull(changedText)
    }

    companion object {
        private const val TIMEOUT = 5000L
        private const val TEST_STRING = "testString"
        private const val START_X = 700
        private const val START_Y = 700
        private const val END_X = 500
        private const val END_Y = 700
        private const val REPEAT_SWIPE = 3
        private const val STEPS_SWIPE = 10
        private const val REPEAT_CLICK_TWO = 2
        private const val REPEAT_CLICK_THREE = 3
        private const val REPEAT_CLICK_FOUR = 4
        private const val REPEAT_CLICK_FIVE = 5


    }


}
