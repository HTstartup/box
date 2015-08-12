package com.android021box.htstartup.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android021box.htstartup.R;
import com.android021box.htstartup.async.GetAsyncPicture;
import com.android021box.htstartup.http.Base;
import com.android021box.htstartup.info.EventInfo;

import java.util.List;

/**
 * Created by arctu on 2015/8/7.
 */
public class EventAdapter extends BaseAdapter {
    private final List<EventInfo> mList;
    private final LayoutInflater mInflater;
    public int start_index=0, end_index=6;
    private String BaseUrl = new Base().getBaseUrl();
    public static boolean scroll;
    private GetAsyncPicture getPic;
    class ViewHolder {
        ImageView img_header;
        TextView text_name;
        TextView text_time;
        TextView text_address;
        TextView text_host_company;
    }

    public EventAdapter(Context context, List<EventInfo> events) {
        mInflater = LayoutInflater.from(context);
        mList = events;
        getPic= new GetAsyncPicture(context);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
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
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if ((position <= mList.size()) && (position >= 0)) {
            try {
                final EventInfo ev = mList.get(position);
                if (position >= (start_index-2) && position <= (end_index+2) ) {
                    holder.img_header.setTag(BaseUrl
                            + ev.getHeader().getImgPath());
                    getPic.getSquareImage(BaseUrl
                                    + ev.getHeader().getImgPath(),
                            holder.img_header);
                } else {
                    holder.img_header.setImageResource(R.drawable.phbg);
                }
                holder.text_name.setText(ev.getName());
                holder.text_host_company.setText(ev.getHostName());
                holder.text_address.setText(ev.getAddress());
                holder.text_time.setText(ev.getStartTime());
            } catch (Exception e) {
                Log.e("Adapter", "error to get eventInfo" + e.toString());
            }
        }
        return convertView;
    }
}
