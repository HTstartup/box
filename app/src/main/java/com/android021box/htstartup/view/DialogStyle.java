package com.android021box.htstartup.view;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class DialogStyle {
	private int position;
	private int style;
	private View view;
	private Builder builder;
	public DialogStyle(Builder builder,int pos,int style)
	{
		this.position=pos;
		this.style=style;	
		this.builder=builder;
	}	
	public DialogStyle(View view,int pos,int style)
	{
		this.position=pos;
		this.style=style;	
		this.view=view;
	}
	public Dialog setAnim()
	{
		Dialog dialog=builder.create();
		Window window =dialog.getWindow();
		window.setGravity(position); // �˴���������dialog��ʾ��λ��
		window.setWindowAnimations(style); // ��Ӷ���
		WindowManager.LayoutParams lp = window.getAttributes(); // ��Ļ��ɫ����
		lp.dimAmount = 0f;
		window.setAttributes(lp);
		return dialog;
	}
	public void setAnim(AlertDialog dialog)
	{
		dialog.setView(view, 0, 0, 0, 0);
		Window window =dialog.getWindow();
		window.setGravity(position); // �˴���������dialog��ʾ��λ��
		window.setWindowAnimations(style); // ��Ӷ���
		WindowManager.LayoutParams lp = window.getAttributes(); // ��Ļ��ɫ����
		lp.dimAmount = 0f;
		window.setAttributes(lp);
	}

}
