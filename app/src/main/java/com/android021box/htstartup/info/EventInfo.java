package com.android021box.htstartup.info;

import java.util.Date;
import java.util.List;

/**
 * Created by arctu on 2015/8/7.
 */
public class EventInfo {
    private int id;
    private String name;
    private String time;
    private String address;
    private String hostCompany;
    private PhotoInfo header;
    private List<PhotoInfo> images;

    public EventInfo() {
    }

    public List<PhotoInfo> getImages() {
        return images;
    }

    public void setImages(List<PhotoInfo> images) {
        this.images = images;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHostCompany() {
        return hostCompany;
    }

    public void setHostCompany(String hostCompany) {
        this.hostCompany = hostCompany;
    }

    public PhotoInfo getHeader() {
        return header;
    }

    public void setHeader(PhotoInfo header) {
        this.header = header;
    }
}
