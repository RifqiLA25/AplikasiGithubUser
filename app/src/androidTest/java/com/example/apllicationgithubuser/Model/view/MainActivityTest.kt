package com.example.apllicationgithubuser.Model.view

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.apllicationgithubuser.Adapter.ListUserAdapter
import com.example.apllicationgithubuser.R
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {


    @Before
    fun setUp() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun testSearchAndClick() {
        Espresso.onView(ViewMatchers.withId(R.id.edReview))
            .perform(ViewActions.typeText("RifqiLA25"), ViewActions.closeSoftKeyboard())

        Espresso.onView(ViewMatchers.withId(R.id.btnSend))
            .perform(ViewActions.click())

        Thread.sleep(2000)

        Espresso.onView(ViewMatchers.withId(R.id.result_user))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.result_user))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<ListUserAdapter.ListViewHolder>(
                    0,
                    ViewActions.click()
                )
            )
    }
}