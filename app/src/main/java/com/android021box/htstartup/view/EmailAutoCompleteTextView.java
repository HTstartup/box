package com.android021box.htstartup.view;


import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.android021box.htstartup.R;

public class EmailAutoCompleteTextView extends AutoCompleteTextView {

    private static final String TAG = "EmailAutoCompleteTextView";

    private String[] emailSufixs = new String[]{"@qq.com", "@163.com", "@126.com", "@gmail.com", "@sina.com", "@hotmail.com",
            "@yahoo.cn", "@sohu.com", "@foxmail.com", "@139.com", "@yeah.net", "@vip.qq.com", "@vip.sina.com"};

    public EmailAutoCompleteTextView(Context context) {
        super(context);
        init(context);
    }
    public EmailAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    public EmailAutoCompleteTextView(Context context, AttributeSet attrs,
                                     int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }


    public void setAdapterString(String[] es) {
        if (es != null && es.length > 0)
            this.emailSufixs = es;
    }


    private void init(final Context context) {
        //adapter��ʹ��Ĭ�ϵ�emailSufixs�е����ݣ�����ͨ��setAdapterString������
        this.setAdapter(new EmailAutoCompleteAdapter(context, R.layout.list_auto_email, emailSufixs));
        //ʹ��������1���ַ�֮��㿪���Զ����
        this.setThreshold(1);
        this.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String text = EmailAutoCompleteTextView.this.getText().toString();
                    //�����ı������»�ý���������Զ����
                    if (!"".equals(text))
                        performFiltering(text, 0);
                } else {
                    //���ı���ʧ����󣬼������email��ַ�ĸ�ʽ
                    EmailAutoCompleteTextView ev = (EmailAutoCompleteTextView) v;
                    String text = ev.getText().toString();
                    //��������д���е�ֱ�:)
                    if (text != null && text.matches("^[a-zA-Z0-9_]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+$")) {
                    } else {
                        Toast toast = Toast.makeText(context, "�ʼ���ַ��ʽ����ȷ", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            }
        });
    }

    @Override
    protected void replaceText(CharSequence text) {
        //����������������ѡ��һ��ʱ��android��Ĭ��ʹ��AutoCompleteTextView��Adapter����ı�������ı���
        //��Ϊ����Adapter��ֻ�Ǵ��˳���email�ĺ�׺
        //���Ҫ����replace�߼������û�����Ĳ������׺�ϲ�
        Log.i(TAG + " replaceText", text.toString());
        String t = this.getText().toString();
        int index = t.indexOf("@");
        if (index != -1)
            t = t.substring(0, index);
        super.replaceText(t + text);
    }


    @Override
    protected void performFiltering(CharSequence text, int keyCode) {
        //�÷��������û������ı�֮����ã�����������ı���adapter�е����ݶԱȣ�����ƥ��
        //adapter�����ݵ�ǰ�벿�֣���ôadapter�е��������ݽ������������г���
        Log.i(TAG + " performFiltering", text.toString() + "   " + keyCode);
        String t = text.toString();
        //��Ϊ�û���������ʱ����������ĸ�����ֿ�ʼ�������ǵ�adapter��ֻ���ṩ��������"@163.com"
        //�������׺������ڵ���super.performFilteringʱ�������һ������"@"��ͷ���ַ���
        int index = t.indexOf("@");
        if (index == -1) {
            if (t.matches("^[a-zA-Z0-9_]+$")) {
                super.performFiltering("@", keyCode);
            } else
                this.dismissDropDown();//���û���;����Ƿ��ַ�ʱ���ر�������ʾ��
        } else {
            super.performFiltering(t.substring(index), keyCode);
        }
    }


    private class EmailAutoCompleteAdapter extends ArrayAdapter<String> {
        public EmailAutoCompleteAdapter(Context context, int textViewResourceId, String[] email_s) {
            super(context, textViewResourceId, email_s);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Log.i(TAG, "in GetView");
            View v = convertView;
            if (v == null)
                v = LayoutInflater.from(getContext()).inflate(
                        R.layout.list_auto_email, null);
            TextView tv = (TextView) v.findViewById(R.id.text_email);
            String t = EmailAutoCompleteTextView.this.getText().toString();
            int index = t.indexOf("@");
            if (index != -1)
                t = t.substring(0, index);
            //���û�������ı���adapter�е�email��׺ƴ�Ӻ�������������ʾ
            tv.setText(t + getItem(position));
            Log.i(TAG, tv.getText().toString());
            return v;
        }

    }

}