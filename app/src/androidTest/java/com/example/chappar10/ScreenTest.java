package com.example.chappar10;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.hamcrest.CoreMatchers.anything;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.chappar10.ui.activities.LoginActivity;
import com.example.chappar10.ui.activities.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ScreenTest {
    @Rule
    public ActivityScenarioRule<LoginActivity> activityScenarioRule = new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void clickOnBottomNavigationItem() {
        //check if open a new fragment

        onView(withId(R.id.register))
                .perform(click())
                .check(matches(isDisplayed()));

//        onData(anything())
//                .inAdapterView(withId(R.id.list))
//                .atPosition(0)
//                .perform(click());

        //the result is open a new fragment
//        onView(withId(R.id.bottom_navigation_view)).check();
    }
}
