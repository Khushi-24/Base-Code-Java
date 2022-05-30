package com.demo.BaristaBucks.Util;

import java.util.Objects;
import java.util.StringJoiner;

public class StringUtil {

    public static boolean nonNullNonEmpty(String strObj) {

        return (Objects.nonNull(strObj) && !strObj.trim().isEmpty());
    }

    /**
     * converts given string to snake_case
     *
     * @param string
     * @param separator
     * @return
     * @Note for camelCase to snake_case conversion, pass null separator
     */
    public static String convert_to_snake_case(String string, String separator) {
        String[] s;
        if (Objects.nonNull(separator)) {
            s = string.split(separator);
        } else {
            s = string.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])");
        }
        return Objects.requireNonNull(stringJoin("_", s)).toLowerCase();
    }

    /**
     * Join Strings with given separator.
     *
     */
    public static String stringJoin(String separator, String... strings) {
        if (Objects.nonNull(separator)) {
            StringJoiner stringJoiner = new StringJoiner(separator);
            if (Objects.nonNull(strings)) {
                for (String string : strings) {
                    if (nonNullNonEmpty(string)) {
                        stringJoiner.add(string);
                    }
                }
            }
            return stringJoiner.toString();
        }
        return null;
    }
}
