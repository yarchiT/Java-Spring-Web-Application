package com.example.urlServiceCheck.domain;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class UrlCheck {

    @Id
    private String urlString;

    private int responseCode;

    public UrlCheck(){}

    public UrlCheck(String url){
        this.urlString =url;
    }

    public UrlCheck(String url, int responseCode){
        this.urlString =url;
        this.responseCode = responseCode;
    }

    public String getUrlString() {
        return urlString;
    }

    public void setUrlString(String urlString ) {
        this.urlString = urlString;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    @Override
    public String toString() {
        return String.format(
                "Url[url=%s, code='%d']", urlString,responseCode);
    }
}