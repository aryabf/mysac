package com.finalproject.mysac.utils;

import java.util.ArrayList;
import java.util.Arrays;

public class ArrayListStringUtils {

    public static ArrayList<String> rawDataToArrayList(String data) {
        // Memisahkan string menjadi array menggunakan karakter '^'
        String[] parts = data.split("\\^");

        // Membuat ArrayList dan menambahkan elemen-elemen dari array
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(parts));

        return arrayList;
    }

}
