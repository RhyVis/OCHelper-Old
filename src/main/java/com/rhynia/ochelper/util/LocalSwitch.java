package com.rhynia.ochelper.util;

import static com.rhynia.ochelper.util.Preload.SWITCH_MAP_NAME_FLUID;

public class LocalSwitch {
    public static String trySwitchFluidName(String s) {
        if (SWITCH_MAP_NAME_FLUID.containsKey(s))
            return SWITCH_MAP_NAME_FLUID.get(s);
        return s;
    }
}
