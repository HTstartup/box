package com.android021box.htstartup.info;

import java.util.List;

/**
 * Created by arctu on 2015/8/7.
 */
public class EventInfo {
    private int id;
    private String name;
    private String endTime;
    private String address;
    private String hostName;
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

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public PhotoInfo getHeader() {
        return header;
    }

    public void setHeader(PhotoInfo header) {
        this.header = header;
    }
}
