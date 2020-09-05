package com.example.newsapp;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import org.ocpsoft.prettytime.PrettyTime;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Articles implements Serializable {

    private String author;


    @SerializedName("publishedAt")
    private String date;
    private String title;

    @SerializedName("description")
    private String desc;
    private String id;
    private String content;
    private String name;
    private Source source;
    private String urlToImage;
    private String time;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        String date1=date.substring(0,10);
        return date1;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public String getTime() {
        Date currentDate = new Date();
        try {
            Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(date.substring(1,10));
            int t=currentDate.compareTo(date1);
            PrettyTime p = new PrettyTime();
            System.out.println(p.format(new Date()));
            if(t==1) {
                time = String.valueOf(t) + " day";
            }else {
                time = String.valueOf(t) + " days ago";
            }
            Log.i("MYTAG",time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


}
