package com.demo.BaristaBucks.Util;

import java.util.Collection;
import java.util.Objects;

public class CollectionUtility {
    /**
     * Check given collection is not null and not empty.
     * @param collection Input
     * @return false if empty; true if not empty
     */
    public static boolean nonNullNonEmpty(Collection<?> collection) {
        return Objects.nonNull(collection) && !collection.isEmpty();
    }
}