package it.gilvegliach.learning.espressoassertwhilepressed;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static it.gilvegliach.learning.espressoassertwhilepressed.LowLevelActions.pressAndHold;
import static it.gilvegliach.learning.espressoassertwhilepressed.LowLevelActions.release;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import android.support.test.InstrumentationRegistry;

import android.test.ActivityInstrumentationTestCase2;

/**
 * @author  Gil Vegliach <gil.vegliach@zalando.de>
 */
public class AssertWhilePressedTest extends ActivityInstrumentationTestCase2<MainActivity> {
    public AssertWhilePressedTest() {
        super(MainActivity.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        getActivity();
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
        LowLevelActions.tearDown();
    }

    @Test
    public void testAssertWhilePressed() {
        onView(withId(R.id.button)).perform(pressAndHold());
        onView(withId(R.id.text)).check(matches(withText("Button is held down")));
        onView(withId(R.id.button)).perform(release());
    }
}
