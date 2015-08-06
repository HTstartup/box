package com.android021box.htstartup.tool;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TextWatcher implements android.text.TextWatcher {
	private CharSequence temp;
	private Context context;
	private int editStart;
	private int editEnd;
	private EditText mEditText;
	private TextView mTextView;
	private int number;
	public static boolean state=true;
	public TextWatcher(Context context, EditText mEditText, TextView mTextView,
			int number,boolean state) {
		this.context = context;
		this.mEditText = mEditText;
		this.mTextView = mTextView;
		this.number = number;
		this.state=state;
	}

	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		try {
			temp = s;
		} catch (Exception e) {
			Log.e("TextWatcher", "on" + e.toString());
		}
		;
	}

	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		// mTextView.setText(s);//�����������ʵʱ��ʾ
	}

	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		try {
			editStart = mEditText.getSelectionStart();
			editEnd = mEditText.getSelectionEnd();
			int color;
			try {
				mTextView.setText(temp.length() + "/" + number);
			} catch (Exception e) {
			};
			if (temp.length() >= number) {
				state=false;
				try {
					mTextView.setTextColor(Color.RED);
				} catch (Exception e) {
					Log.e("TextWatcher", "change" + e.toString());
				};				
			}else
			{
				state=true;
				try {
					mTextView.setTextColor(Color.WHITE);
				} catch (Exception e) {
					Log.e("TextWatcher", "back" + e.toString());
				};
			}
		} catch (Exception e) {
			Log.e("TextWatcher", "after" + e.toString());
		}
		;
	}

}
