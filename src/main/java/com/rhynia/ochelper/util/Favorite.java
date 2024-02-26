package com.rhynia.ochelper.util;

import com.alibaba.fastjson2.annotation.JSONField;

public class Favorite {
    @JSONField(name = "name")
    private String favName;
    @JSONField(name = "label")
    private String favLabel;
    @JSONField(name = "damage")
    private int favMeta;

    public Favorite(String name, int damage) {
        favName = name;
        favMeta = damage;
    }
    public Favorite(String label) {
        favLabel = label;
    }

    public String getFavName() {
        return favName;
    }
    public String getFavLabel() {
        return favLabel;
    }
    public int getFavMeta() {
        return favMeta;
    }
    public void setFavName(String name) {
        favName = name;
    }
    public void setFavLabel(String label) {
        favLabel = label;
    }
    public void setFavMeta(int damage) {
        favMeta = damage;
    }
}
