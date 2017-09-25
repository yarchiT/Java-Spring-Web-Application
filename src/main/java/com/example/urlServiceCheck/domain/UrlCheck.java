package com.example.urlServiceCheck.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


@Entity
public class UrlCheck {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String urlS;
    private int responseCode;

    public UrlCheck(){}

    public UrlCheck(String url){
        this.urlS= url;
    }

    public UrlCheck(String url, int responseCode){
        this.urlS=url;
        this.responseCode = responseCode;
    }

    public String getUrlS() {
        return urlS;
    }

    public void setUrlS(String urlS) {
        this.urlS = urlS;
    }

    public Long getId() {
        return id;
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
                "Url[url=%s, code='%d']",
                urlS,responseCode);
    }


    public int pingURL(String url, int timeout) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setConnectTimeout(timeout);
            connection.setReadTimeout(timeout);
            connection.setRequestMethod("HEAD");
            responseCode = connection.getResponseCode();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return responseCode;
    }
}
