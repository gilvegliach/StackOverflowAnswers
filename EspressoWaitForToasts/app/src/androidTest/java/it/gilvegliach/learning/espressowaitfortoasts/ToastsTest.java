package it.gilvegliach.learning.espressowaitfortoasts;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;

import android.test.ActivityInstrumentationTestCase2;

/**
 * @author  Gil Vegliach <gil.vegliach@zalando.de>
 */
public class ToastsTest extends ActivityInstrumentationTestCase2<MainActivity> {
    public ToastsTest() {
        super(MainActivity.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        Espresso.registerIdlingResources(ToastManager.getIdlingResource());
        getActivity();
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
        Espresso.unregisterIdlingResources(ToastManager.getIdlingResource());
    }

    @Test
    public void testButton() {
		// Waits for the three toasts to disappear before performing click
        onView(withId(R.id.button)).perform(click());
    }
}
