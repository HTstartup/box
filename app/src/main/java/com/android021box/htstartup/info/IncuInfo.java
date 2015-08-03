package com.android021box.htstartup.info;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by qliang on 2015/8/2.
 */
public class IncuInfo {

    public static final String INCU_ID = "id";
    public static final String INCU_NAME = "name";
    public static final String INCU_PRICE = "price";
    public static final String INCU_FUND_SUPPORT = "fund_support";
    public static final String INCU_COMPANY = "company";
    public static final String INCU_FEATURE = "feature";
    public static final String INCU_SUMMARY = "summary";

    private int id;
    private String name;
    private String price;
    private boolean fund_support;
    private String company;
    private String feature;

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    private String summary;

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isFundSupport() {
        return fund_support;
    }

    public void setFundSupport(boolean fund_support) {
        this.fund_support = fund_support;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public IncuInfo() {

    }

    public IncuInfo(int id, String name,
                    String price, boolean fund_support,
                    String company, String feature,
                    String summary) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.fund_support = fund_support;
        this.company = company;
        this.feature = feature;
        this.summary = summary;
    }

    public static IncuInfo fromJson(JSONObject json) throws JSONException {
        int id = json.getInt(INCU_ID);
        String name = json.getString(INCU_NAME);
        String price = json.getString(INCU_PRICE);
        boolean fund_support = json.getBoolean(INCU_FUND_SUPPORT);
        String company = json.getString(INCU_COMPANY);
        String feature = json.getString(INCU_FEATURE);
        String summary = json.getString(INCU_SUMMARY);

        return  new IncuInfo(id, name, price, fund_support, company, feature, summary);
    }
}
