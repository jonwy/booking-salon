package com.booking.util;

public class IDGenerator {
    
    public static String generateID(String prefix, int index) {
        int indexLength = Integer.toString(index).length();
        StringBuilder sb = new StringBuilder(prefix + "-");
        if (indexLength == 1) {
            sb.append("00" + index);
        }
        else if (indexLength == 2) {
            sb.append("0" + index);
        }
        else if (indexLength > 2) {
            sb.append(index);
        }
        return sb.toString();
    }
}
