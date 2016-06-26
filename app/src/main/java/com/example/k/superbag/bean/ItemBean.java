package com.example.k.superbag.bean;

import android.graphics.drawable.Drawable;

import java.util.List;

/**
 * Created by K on 2016/6/26.
 */
public class ItemBean {

    private String content;
    private String oldTime;
    private String newTime;
    private List<Drawable> drawableList;
    private String dayTime;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOldTime() {
        return oldTime;
    }

    public void setOldTime(String oldTime) {
        this.oldTime = oldTime;
    }

    public String getNewTime() {
        return newTime;
    }

    public void setNewTime(String newTime) {
        this.newTime = newTime;
    }

    public List<Drawable> getDrawableList() {
        return drawableList;
    }

    public void setDrawableList(List<Drawable> drawableList) {
        this.drawableList = drawableList;
    }

    public String getDayTime() {
        return dayTime;
    }

    public void setDayTime(String dayTime) {
        this.dayTime = dayTime;
    }

}