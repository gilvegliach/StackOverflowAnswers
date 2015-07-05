package com.example.goingroundimageview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class MainActivity extends Activity
{
	private View mView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mView = findViewById(R.id.view);
		((View) mView.getParent()).setOnTouchListener(new OnTouchListener()
		{
			@SuppressLint("ClickableViewAccessibility")
			@Override
			public boolean onTouch(View parent, MotionEvent event)
			{
				int action = event.getActionMasked();
				if (action == MotionEvent.ACTION_MOVE)
				{
					float cx = parent.getWidth() / 2.f;
					float cy = parent.getHeight() / 2.f;
					float x = event.getX();
					float y = event.getY();
					float w = mView.getWidth();
					float h = mView.getHeight();

					double r = Math.min(cx, cy) / 2.;
					double dx = x - cx;
					double dy = y - cy;
					double hypot = Math.hypot(dx, dy);
					double cos = dx / hypot;
					double sin = dy / hypot;
					double rdx = hypot < 1. ? 0. : r * cos;
					double rdy = hypot < 1. ? 0. : r * sin;

					mView.setX((float) (cx + rdx - w / 2.));
					mView.setY((float) (cy + rdy - h / 2.));
				}
				return true;
			}
		});
	}

}
