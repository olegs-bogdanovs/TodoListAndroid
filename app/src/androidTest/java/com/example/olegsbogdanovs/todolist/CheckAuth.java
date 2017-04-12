package com.example.olegsbogdanovs.todolist;


import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class CheckAuth {

    @Rule
    public ActivityTestRule<AuthActivity> mActivityRule = new ActivityTestRule<>(
            AuthActivity.class);

    @Test
    public void changeEmailText(){
        onView(withId(R.id.auth_email_edittext))
                .perform(typeText("bogdanovsolegs@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.auth_password_edittext))
                .perform(typeText("testtest"), closeSoftKeyboard());
        onView(withId(R.id.auth_signIn_button)).perform(click());
        onView(withId(R.id.menu_item_add_todo)).perform(click());
        onView(withId(R.id.todo_title)).perform(typeText("One, Two, Three...."));
        onView(withId(R.id.todo_description)).perform(typeText("Automation"), closeSoftKeyboard());
        pressBack();
        onView(withId(R.id.menu_item_logout))
                .perform(click());

    }

}
