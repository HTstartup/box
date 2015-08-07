package com.android021box.htstartup;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.android021box.htstartup.adapter.EventAdapter;
import com.android021box.htstartup.info.EventInfo;
import com.android021box.htstartup.view.PullToRefreshStaggeredGridView;
import com.etsy.android.grid.StaggeredGridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import java.util.ArrayList;
import java.util.List;


public class EventActivity extends Activity {

    private PullToRefreshStaggeredGridView ptrStaggeredGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        initViews();
    }

    private void initViews() {
        ptrStaggeredGridView = (PullToRefreshStaggeredGridView) findViewById(R.id.event_staggered_events);
        List<EventInfo> events = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            events.add(new EventInfo());
        }
        ptrStaggeredGridView.getRefreshableView().setAdapter(new EventAdapter(this, events));
        ptrStaggeredGridView.setMode(PullToRefreshBase.Mode.BOTH);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event, menu);
        return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
