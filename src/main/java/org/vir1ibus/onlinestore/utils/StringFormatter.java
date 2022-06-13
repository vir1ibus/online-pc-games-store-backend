package org.vir1ibus.onlinestore.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StringFormatter {

    public static String toId(String string) {
        return string.replaceAll("(?i)[^А-ЯЁA-Z]", "-");
    }

    public static List<Integer> toIntegerList(String[] strings) {
        return Arrays.stream(strings).map(Integer::parseInt).toList();
    }
}
