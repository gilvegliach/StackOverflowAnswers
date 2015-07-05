package com.example.persistingspan;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity
{
	private static final String TAG = "MainActivity";
	private TextView mTextView;
	private String mText;
	private File mFile;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mTextView = (TextView) findViewById(R.id.text);
		mText = getString(R.string.text);
		mFile = new File(Environment.getExternalStorageDirectory(), "spans.txt");
	}

	public void set(View v)
	{
		SpannableString s = new SpannableString(mText);
		int len = mText.length();
		int start = 0;
		while (true)
		{
			while (start < len && !flipCoin())
				start++;

			int end = start + 1;
			while (end <= len && flipCoin())
				end++;

			if (start >= len)
				break;

			if (end > len)
				end = len;

			s.setSpan(new BackgroundColorSpan(Color.RED), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		mTextView.setText(s);
	}

	public void clear(View v)
	{
		mTextView.setText(mText);
	}

	public void store(View v)
	{
		Spanned s = (Spanned) mTextView.getText();
		BackgroundColorSpan[] spans = s.getSpans(0, s.length(), BackgroundColorSpan.class);

		BufferedWriter bw = null;
		try
		{
			int len = spans.length;
			bw = new BufferedWriter(new FileWriter(mFile));
			bw.write(String.valueOf(len));
			bw.newLine();
			for (BackgroundColorSpan span : spans)
			{
				int start = s.getSpanStart(span);
				int end = s.getSpanEnd(span);
				int color = span.getBackgroundColor();
				bw.write("" + start + "," + end + "," + color);
				bw.newLine();
			}
			bw.write(mText);
			clear(v);
		}
		catch (IOException e)
		{
			Log.e(TAG, "IO error", e);
		}
		finally
		{
			closeQuietly(bw);
		}
	}

	public void load(View v)
	{
		BufferedReader br = null;
		try
		{
			br = new BufferedReader(new FileReader(mFile));

			int len = Integer.parseInt(br.readLine());
			BackgroundColorSpan[] spans = new BackgroundColorSpan[len];
			int[] starts = new int[len];
			int[] ends = new int[len];
			for (int i = 0; i < len; i++)
			{
				String[] tokens = br.readLine().split(",");
				starts[i] = Integer.parseInt(tokens[0]);
				ends[i] = Integer.parseInt(tokens[1]);
				int color = Integer.parseInt(tokens[2]);
				spans[i] = new BackgroundColorSpan(color);
			}
			mText = br.readLine();

			SpannableString s = new SpannableString(mText);
			for (int i = 0; i < len; i++)
			{
				s.setSpan(spans[i], starts[i], ends[i], Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
			mTextView.setText(s);
		}
		catch (IOException e)
		{
			Log.e(TAG, "IO error", e);
		}
		finally
		{
			closeQuietly(br);
		}

	}

	private static boolean flipCoin()
	{
		int flip = (int) Math.floor(Math.random() * 2.0);
		return flip == 1;
	}

	private static void closeQuietly(Closeable... closeables)
	{
		if (closeables == null)
			return;

		for (Closeable c : closeables)
		{
			if (c != null)
			{
				try
				{
					c.close();
				}
				catch (Exception e)
				{
					Log.d(TAG, "Error in closing file", e);
				}
			}
		}
	}
}
