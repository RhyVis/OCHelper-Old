package com.rhynia.ochelper.jsonMap;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Objects;

import com.alibaba.fastjson2.annotation.JSONField;
import com.rhynia.ochelper.util.Format;

public class AEFluid {

    @JSONField(name = "name")
    private String aeName;

    @JSONField(name = "label")
    private String aeLabel;

    @JSONField(name = "amount")
    private String aeSize;

    public AEFluid(String name, String label, String amount) {
        super();
        this.aeName = name;
        this.aeLabel = label;
        this.aeSize = amount;
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

    public void setAeName(String aeName) {
        this.aeName = aeName;
    }

    public void setAeLabel(String aeLabel) {
        this.aeLabel = aeLabel;
    }

    public void setAeSize(String aeSize) {
        this.aeSize = aeSize;
    }

}
