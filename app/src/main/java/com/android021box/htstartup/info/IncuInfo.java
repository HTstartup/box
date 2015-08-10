package com.android021box.htstartup.info;

import java.util.List;

/**
 * Created by qliang on 2015/8/2.
 */
public class IncuInfo {

    private int id;
    private String name;
    private String chargeMethod;
    private String fund;
    private String background;
    private String characteristic;
    private String incubationPeriod;
    private String incubationStage;
    private String summary;
    private List<PhotoInfo> imgList;
    private PhotoInfo imgBg;
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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

    public String getChargeMethod() {
        return chargeMethod;
    }
    public List<PhotoInfo> getImgList() {
        return imgList;
    }

    public void setImgList(List<PhotoInfo> imgList) {
        this.imgList = imgList;
    }

    public PhotoInfo getImgBg() {
        return imgBg;
    }

    public void setImgBg(PhotoInfo imgBg) {
        this.imgBg = imgBg;
    }

    public String getFund() {
        return fund;
    }

    public void setFund(String fund) {
        this.fund = fund;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(String characteristic) {
        this.characteristic = characteristic;
    }

    public String getIncubationPeriod() {
        return incubationPeriod;
    }

    public void setIncubationPeriod(String incubationPeriod) {
        this.incubationPeriod = incubationPeriod;
    }

    public String getIncubationStage() {
        return incubationStage;
    }

    public void setIncubationStage(String incubationStage) {
        this.incubationStage = incubationStage;
    }

    public void setChargeMethod(String chargeMethod) {
        this.chargeMethod = chargeMethod;
    }
}
