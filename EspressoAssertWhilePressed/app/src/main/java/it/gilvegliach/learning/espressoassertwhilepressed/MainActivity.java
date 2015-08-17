package it.gilvegliach.learning.espressoassertwhilepressed;

import android.graphics.Color;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import android.view.MotionEvent;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    TextView mText;
    Button mButton;

    @Override
    public boolean onTouch(final View v, final MotionEvent event) {
        int action = event.getActionMasked();
        if (action == MotionEvent.ACTION_DOWN) {
            mText.setText("Button is held down");
            mText.setTextColor(Color.RED);
            return true;
        } else if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mText.setText("Button is released");
            mText.setTextColor(Color.BLACK);
            return true;
        }

        return false;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mText = (TextView) findViewById(R.id.text);
        mButton = (Button) findViewById(R.id.button);
        mButton.setOnTouchListener(this);
    }
}
