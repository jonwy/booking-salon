package com.booking.util;

import java.text.DecimalFormat;

public class Utils {
    
    public static String IDRFormatter(double num) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###,##0.00");
        return "Rp." + decimalFormat.format(num);
    }
}
