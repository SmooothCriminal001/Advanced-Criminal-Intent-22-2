package com.bignerdranch.android.advanced_criminal_intent_22_2

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragment
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CrimeDetailFragmentTest {

    private lateinit var scenario: FragmentScenario<CrimeDetailFragment>

    @Before
    fun setUp() {
        scenario = FragmentScenario.launch(CrimeDetailFragment::class.java)
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    fun checkBoxChangeModifiesCrime(){
        onView(withId(R.id.crime_solved)).perform(click())
        scenario.onFragment{ fragment ->
            onView(withId(R.id.crime_solved))
                .check(matches(ViewMatchers.isChecked()))
        }
    }
}