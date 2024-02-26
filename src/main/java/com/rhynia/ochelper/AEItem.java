package com.rhynia.ochelper;

import com.alibaba.fastjson2.annotation.JSONField;

public class AEItem {

    @JSONField(name = "name")
    private String aeName;

    @JSONField(name = "label")
    private String aeLabel;

    @JSONField(name = "size")
    private String aeSize;

    @JSONField(name = "damage")
    private int aeMeta;

    public AEItem(String name, String label, String size, int damage) {
        super();
        this.aeName = name;
        this.aeLabel = label;
        this.aeMeta = damage;
        this.aeSize = size;
    }

    public String getAeName() {
        return aeName;
    }

    public String getAeLabel() {
        return aeLabel;
    }

    public String getAeSize() {
        return aeSize;
    }

    public int getAeMeta() {
        return aeMeta;
    }

    public void setAeName(String aeName) {
        this.aeName = aeName;
    }

    public void setAeLabel(String aeLabel) {
        this.aeLabel = aeLabel;
    }

    public void setAeSize(String aeSize) {
        this.aeSize = aeSize;
    }

    public void setAeMeta(int aeMeta) {
        this.aeMeta = aeMeta;
    }
}
