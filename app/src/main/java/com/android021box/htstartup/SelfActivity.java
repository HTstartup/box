package com.android021box.htstartup;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;


public class SelfActivity extends Activity {
    private ScrollView layout_main;
    private ImageView img_bg;
    private Button btn_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self);
        initView();
        setListener();
    }
    private void initView(){
        layout_main=(ScrollView)findViewById(R.id.layout_main);
        img_bg=(ImageView)findViewById(R.id.img_bg);
        btn_login=(Button)findViewById(R.id.btn_login);
    }
    private void setListener(){
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_bg.setVisibility(View.GONE);
                layout_main.setVisibility(View.VISIBLE);
                btn_login.setVisibility(View.GONE);
            }
        });
    }
}
