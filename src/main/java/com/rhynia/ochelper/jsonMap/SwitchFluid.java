package com.rhynia.ochelper.jsonMap;

import com.alibaba.fastjson2.annotation.JSONField;

public class SwitchFluid {
    private static final String DUMMY = "dummy";
    @JSONField(name = "pre")
    private String preName;
    @JSONField(name = "alt")
    private String altName;

    SwitchFluid(String pre, String alt) {
        this.preName = pre;
        this.altName = alt;
    }

    public String getPreName() {
        if (preName == null)
            return DUMMY;
        return preName;
    }

    public String getAltName() {
        if (altName == null)
            return DUMMY;
        return altName;
    }

    public void setPreName(String pre) {
        preName = pre;
    }

    public void setAltName(String alt) {
        altName = alt;
    }
}
