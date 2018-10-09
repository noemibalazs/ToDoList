package com.example.android.todolist;

import android.support.test.rule.ActivityTestRule;
import android.support.test.espresso.contrib.RecyclerViewActions;

import com.example.android.todolist.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(JUnit4.class)
public class MainActivityScreenTest {

    private static final String TASK_NAME = "John Deere US android developer";

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void clickRecycleViewItem_openAddTaskActivity(){

       onView(withId(R.id.my_recycle))
               .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }

    @Test
    public void johnDeere(){
        onView(withId(R.id.my_recycle))
                .perform(RecyclerViewActions.actionOnItem(hasDescendant(withText(TASK_NAME)), click()));
    }
}
