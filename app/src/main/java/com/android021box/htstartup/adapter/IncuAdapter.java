package com.android021box.htstartup.adapter;

import android.app.Activity;
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
import com.android021box.htstartup.info.IncuInfo;

import java.util.List;

/**
 * Create by wanginbeijing on 15/8/3.
 */
public class IncuAdapter extends BaseAdapter {
    private List<IncuInfo> inclist;
    private Activity context;
    private String BaseUrl = new Base().getBaseUrl();
    public static boolean scroll;
    public int start_index=0, end_index=6;
    private GetAsyncPicture getPic;
    public IncuAdapter(List<IncuInfo> blist, Activity context) {
        this.context = context;
        this.inclist = blist;
        getPic= new GetAsyncPicture(context);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return inclist.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return inclist.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final LayoutInflater layoutinflater = LayoutInflater.from(context);
        IncuHolder ih = new IncuHolder();
        if (convertView == null) {
            convertView = layoutinflater.inflate(R.layout.list_item_incu, null);
            ih.text_name = (TextView) convertView
                    .findViewById(R.id.incu_text_name);
            ih.text_summary = (TextView) convertView
                    .findViewById(R.id.incu_text_summary);
            ih.text_tag1= (TextView) convertView
                    .findViewById(R.id.incu_text_tag1);
            ih.text_tag2= (TextView) convertView
                    .findViewById(R.id.incu_text_tag2);
            ih.text_tag3= (TextView) convertView
                    .findViewById(R.id.incu_text_tag3);
            ih.text_tag4= (TextView) convertView
                    .findViewById(R.id.incu_text_tag4);
            ih.img_header = (ImageView) convertView.findViewById(R.id.incu_img_header);
            convertView.setTag(ih);
        } else {
            ih = (IncuHolder) convertView.getTag();
        }
        if ((position < inclist.size()) && (position >= 0)) {
            try {
                final IncuInfo inc = inclist.get(position);
                if (position >= start_index && position <= end_index ) {
                    ih.img_header.setTag(BaseUrl
                            + inc.getImgBg().getImgPath());
                    getPic.getSquareImage(BaseUrl
                                    + inc.getImgBg().getImgPath(),
                            ih.img_header);
                } else {
                    ih.img_header.setImageResource(R.drawable.phbg);
                }
                ih.text_name.setText(inc.getName());
                ih.text_summary.setText(inc.getSummary());
                if(inc.getPrice().equals("免费")){
                    ih.text_tag1.setText(inc.getPrice());
                    ih.text_tag1.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                Log.e("Adapter", "error to get incuInfo" + e.toString());
            }
        }
        return convertView;
    }
}

class IncuHolder {
    public ImageView img_header;
    public TextView text_name;
    public TextView text_tag1;
    public TextView text_tag2;
    public TextView text_tag3;
    public TextView text_tag4;
    public TextView text_summary;
}
