package com.android021box.htstartup.httputil;

import android.util.Log;

import com.android021box.htstartup.info.IncuInfo;
import com.android021box.htstartup.info.PhotoInfo;
import com.android021box.htstartup.info.TeamInfo;

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
            inc.setChargeMethod(json_data.getString("price"));
            inc.setFund(json_data.getString("fund"));
            inc.setBackground(json_data.getString("background"));
            inc.setIncubationPeriod(json_data.getString("incubation_period"));
            inc.setIncubationStage(json_data.getString("incubation_stage"));
            inc.setImgList(getIncuPhoto(json_data.getJSONArray("images"),inc));
            inc.setTeamList(getIncuTeam(json_data.getJSONArray("teams")));
        } catch (JSONException e) {
            Log.e("log_tag", "Error parsing incuData " + e.toString());
        }
        return inc;
    }
    public IncuInfo getIncuDetail(JSONObject json_data){
        IncuInfo inc = new IncuInfo();
        try {
            inc.setId(json_data.getInt("id"));
            inc.setName(json_data.getString("name"));
            inc.setChargeMethod(json_data.getString("price"));
            inc.setFund(json_data.getString("fund"));
            inc.setBackground(json_data.getString("background"));
            inc.setIncubationPeriod(json_data.getString("incubation_period"));
            inc.setIncubationStage(json_data.getString("incubation_stage"));
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
                photo.setImgPath(json_data.getString("path"));
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
    public ArrayList<TeamInfo> getIncuTeam(JSONArray teamJarray) {
        ArrayList<TeamInfo> teamList = new ArrayList<TeamInfo>();
        try {
            for (int j = 0; j < teamJarray.length(); j++) {
                TeamInfo team = new TeamInfo();
                JSONObject json_data = teamJarray.getJSONObject(j);
                team.setId(json_data.getInt("id"));
                team.setLogo(json_data.getString("logo"));
                team.setName(json_data.getString("name"));
                team.setSummary(json_data.getString("summary"));
            }
        } catch (JSONException e) {
            Log.e("log_tag", "Error parsing imgData " + e.toString());
        }
        return teamList;
    }
}
