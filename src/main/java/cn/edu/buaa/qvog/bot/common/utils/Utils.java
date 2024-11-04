/*
 * Copyright (C) QVoG@BUAA 2024
 * Programmed by Tony S.
 */

package cn.edu.buaa.qvog.bot.common.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Utils {
    /**
     * Count files with the given extension in the root directory.
     *
     * @param root      The root directory
     * @param extension The file extension
     * @return The number of files with the given extension
     */
    public static int countFiles(String root, String extension) throws IOException {
        try (var files = Files.walk(Paths.get(root))) {
            return (int) files.filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(extension))
                    .count();
        }
    }
}
