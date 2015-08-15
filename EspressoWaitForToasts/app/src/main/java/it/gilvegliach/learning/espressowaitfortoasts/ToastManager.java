package it.gilvegliach.learning.espressowaitfortoasts;

import android.content.Context;

import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.CountingIdlingResource;

import android.view.View;

import android.widget.Toast;

/**
 * @author  Gil Vegliach <gil.vegliach@zalando.de>
 */
public final class ToastManager {
    private static final CountingIdlingResource idlingResource = new CountingIdlingResource("toast");
    private static final View.OnAttachStateChangeListener listener = new View.OnAttachStateChangeListener() {
        @Override
        public void onViewAttachedToWindow(final View v) {
            idlingResource.increment();
        }

        @Override
        public void onViewDetachedFromWindow(final View v) {
            idlingResource.decrement();
        }
    };

    private ToastManager() { }

    public static Toast makeText(final Context context, final CharSequence text, final int duration) {
        Toast t = Toast.makeText(context, text, duration);
        t.getView().addOnAttachStateChangeListener(listener);
        return t;
    }

    // For testing
    public static IdlingResource getIdlingResource() {
        return idlingResource;
    }
}
