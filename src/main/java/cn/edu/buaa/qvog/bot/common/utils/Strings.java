/*
 * Copyright (C) QVoG@BUAA 2024
 * Programmed by Tony S.
 */

package cn.edu.buaa.qvog.bot.common.utils;

import java.io.BufferedReader;

public class Strings {
    public static String toString(BufferedReader reader) {
        try {
            var sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        } catch (Exception e) {
            // not a good practice to return such a message
            return "Failed to read output";
        }
    }
}
