package com.laytest.hacker.vip.Models;

/**
 * Created by Hacker on 9/30/2017.
 */

public class Hod_Model {

    private String paper;
    private String url;

    Hod_Model(){}

    public Hod_Model(String paper, String url){
        this.paper=paper;
        this.url=url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {

        return url;
    }

    public void setPaper(String paper) {
        this.paper = paper;
    }

    public String getPaper() {

        return paper;
    }
}
