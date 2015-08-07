package com.android021box.htstartup.httputil;

import android.util.Log;

import com.android021box.htstartup.info.IncuInfo;
import com.android021box.htstartup.info.PhotoInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by wanginbeijing on 15/8/3.
 */
public class IncuUtil {
    public IncuInfo getIncu(JSONObject json_data){
        IncuInfo inc = new IncuInfo();
        try {
            inc.setId(json_data.getInt("id"));
            inc.setName(json_data.getString("name"));
            inc.setCompany(json_data.getString("company"));
            inc.setFeature(json_data.getString("feature"));
            inc.setSummary(json_data.getString("abstract"));
            inc.setFundSupport(json_data.getInt("fund_support") == 1 ? true : false);
            inc.setPrice(json_data.getString("price"));
            inc.setImgList(getIncuPhoto(json_data.getJSONArray("images"),inc));
        } catch (JSONException e) {
            Log.e("log_tag", "Error parsing incuData " + e.toString());
        }
        return inc;
    }
    public ArrayList<PhotoInfo> getIncuPhoto(JSONArray imgJarray,IncuInfo inc) {
        ArrayList<PhotoInfo> imgList = new ArrayList<PhotoInfo>();
        try {
            for (int j = 0; j < imgJarray.length(); j++) {
                PhotoInfo photo = new PhotoInfo();
                JSONObject json_data = imgJarray.getJSONObject(j);
                photo.setImgPath(json_data.getString("url"));
                photo.setType(json_data.getInt("type"));
                if(photo.getType()==1){
                    inc.setImgBg(photo);
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
