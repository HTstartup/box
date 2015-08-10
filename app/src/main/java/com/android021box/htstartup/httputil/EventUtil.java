package com.android021box.htstartup.httputil;

import android.util.Log;

import com.android021box.htstartup.info.EventInfo;
import com.android021box.htstartup.info.PhotoInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by wanginbeijing on 15/8/7.
 */
public class EventUtil {
    public EventInfo getEvent(JSONObject json_data){
        EventInfo ev = new EventInfo();
        try {
            ev.setId(json_data.getInt("id"));
            ev.setName(json_data.getString("name"));
            ev.setHostName(json_data.getString("host_name"));
            ev.setAddress(json_data.getString("address"));
            ev.setEndTime(json_data.getString("end_time"));
            ev.setImages(getEventPhoto(json_data.getJSONArray("images"),ev));
        } catch (JSONException e) {
            Log.e("log_tag", "Error parsing incuData " + e.toString());
        }
        return ev;
    }
    public ArrayList<PhotoInfo> getEventPhoto(JSONArray imgJarray,EventInfo ev) {
        ArrayList<PhotoInfo> imgList = new ArrayList<PhotoInfo>();
        try {
            for (int j = 0; j < imgJarray.length(); j++) {
                PhotoInfo photo = new PhotoInfo();
                JSONObject json_data = imgJarray.getJSONObject(j);
                photo.setImgPath(json_data.getString("path"));
                photo.setType(json_data.getInt("type"));
                if(photo.getType()==1){
                    ev.setHeader(photo);
                }else{
                    imgList.add(photo);
                }
            }
        } catch (JSONException e) {
            Log.e("log_tag", "Error parsing imgData " + e.toString());
        }
        return imgList;
    }
}
