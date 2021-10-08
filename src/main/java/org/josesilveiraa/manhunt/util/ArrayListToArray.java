package org.josesilveiraa.manhunt.util;

import java.util.List;

public class ArrayListToArray {

    public static String[] transform(List<String> list) {
        return list.toArray(new String[0]);
    }

}
