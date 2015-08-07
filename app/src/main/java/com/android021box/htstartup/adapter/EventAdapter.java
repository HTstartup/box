package com.android021box.htstartup.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android021box.htstartup.R;
import com.android021box.htstartup.info.EventInfo;

import java.util.List;

/**
 * Created by arctu on 2015/8/7.
 */
public class EventAdapter extends BaseAdapter {
    private final List<EventInfo> mEvents;
    private final LayoutInflater mInflater;

    class ViewHolder {
        ImageView img_header;
        TextView text_name;
        TextView text_time;
        TextView text_address;
        TextView text_host_company;
    }

    public EventAdapter(Context context, List<EventInfo> events) {
        mInflater = LayoutInflater.from(context);
        mEvents = events;
    }

    @Override
    public int getCount() {
        return mEvents.size();
    }

    @Override
    public Object getItem(int position) {
        return mEvents.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_event, null);
            holder = new ViewHolder();
            holder.img_header = (ImageView) convertView.findViewById(R.id.li_event_img_header);
            holder.text_name = (TextView) convertView.findViewById(R.id.li_event_text_name);
            holder.text_time = (TextView) convertView.findViewById(R.id.li_event_text_time);
            holder.text_address = (TextView) convertView.findViewById(R.id.li_event_text_address);
            holder.text_host_company = (TextView) convertView.findViewById(R.id.li_event_text_host_company);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // TODO: 填充布局
        return convertView;
    }
}
