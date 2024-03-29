package com.rhynia.ochelper.jsonMap;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Objects;

import com.alibaba.fastjson2.annotation.JSONField;
import com.rhynia.ochelper.util.Format;

public class AEItem {

    @JSONField(name = "name")
    private String aeName;

    @JSONField(name = "label")
    private String aeLabel;

    @JSONField(name = "size")
    private String aeSize;

    @JSONField(name = "damage")
    private int aeMeta;

    @JSONField(name = "hasTag")
    private boolean aeTag;

    @JSONField(name = "isCraftable")
    private boolean aeCraftable;

    public AEItem(String name, String label, String size, int damage, boolean hasTag, boolean isCraftable) {
        super();
        this.aeName = name;
        this.aeLabel = label;
        this.aeMeta = damage;
        this.aeSize = size;
        this.aeTag = hasTag;
        this.aeCraftable = isCraftable;
    }

    public String getAeName() {
        return aeName;
    }

    public String getAeLabel() {
        return aeLabel;
    }

    public String getAeSize() {
        if (aeSize.contains("E")) {
            BigDecimal bd = new BigDecimal(aeSize);
            return bd.toPlainString();
        }
        if (aeSize.contains(".0"))
            return aeSize.substring(0, aeSize.indexOf("."));
        return aeSize;
    }

    public String getAeSizeDisplay() {
        BigDecimal bd = new BigDecimal(this.getAeSize());
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(bd);
    }

    public String getAeSizeByte() {
        String temp = this.getAeSize();
        String out = Format.formatStringByte(this.getAeSize());
        if (Objects.equals(temp, out))
            return "";
        return "(" + out + ")";
    }

    public int getAeMeta() {
        return aeMeta;
    }

    public boolean hasAeTag() {
        return aeTag;
    }

    public boolean isAeCraftable() {
        return aeCraftable;
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

    public void setAeTag(boolean aeTag) {
        this.aeTag = aeTag;
    }

    public void setAeCraftable(boolean aeCraftable) {
        this.aeCraftable = aeCraftable;
    }
}
