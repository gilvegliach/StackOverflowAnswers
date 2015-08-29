package it.gilvegliach.learning.espressoviewpager;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.instanceOf;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import org.junit.Test;

import android.support.test.InstrumentationRegistry;

import android.test.ActivityInstrumentationTestCase2;

import android.test.suitebuilder.annotation.Suppress;

import android.view.View;

import android.widget.AdapterView;

public class ViewPagerTestCase extends ActivityInstrumentationTestCase2<MainActivity> {
    public ViewPagerTestCase() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        getActivity();
    }

    // Test fails, don't run
    @Suppress
    public void testClickOnSecondItemInAllTabFailing() {
        onData(instanceOf(String.class)).atPosition(1) //
                                        .perform(click());
    }

    @Test
    public void testClickOnSecondItemInAllTab() {
        onData(instanceOf(String.class)).inAdapterView(withTag(false)) //
                                        .atPosition(1)                 //
                                        .perform(click());
    }

    @Test
    public void testClickOnSecondItemInAllTab2() {
        onData(instanceOf(String.class)).inAdapterView(allOf(isAssignableFrom(AdapterView.class), isDisplayed())) //
                                        .atPosition(1)                                                            //
                                        .perform(click());
    }

    static Matcher<View> withTag(final Object tag) {
        return new TypeSafeMatcher<View>() {

            @Override
            public void describeTo(final Description description) {
                description.appendText("has tag equals to: " + tag);
            }

            @Override
            protected boolean matchesSafely(final View view) {
                Object viewTag = view.getTag();
                if (viewTag == null) {
                    return tag == null;
                }

                return viewTag.equals(tag);
            }
        };
    }
}
