package cn.liutils.api.util;

public class DebugUtils {

    public static <T> String formatArray(T... arr) {
        StringBuilder b = new StringBuilder("(");
        for(T a : arr) {
            b.append(a).append(", ");
        }
        b.append("\b\b )");
        return b.toString();
    }

}
