package com.hackaton.hackton.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

public class Utils {

    public static Long generateProtocol(){
        long leftLimit = 1L;
        long rightLimit = 999999L;
        return leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
    }

    public static String encodeFileToBase64(String filename) throws IOException {
        return Base64.getEncoder().encodeToString(loadResourceFile(filename));
    }

    private static byte[] loadResourceFile(String filename) throws IOException {
        try(InputStream inputStream = new FileInputStream(filename)){
            return inputStream.readAllBytes();
        }
    }

    public static  void deleteIfExists(String file) {
        try {
            Files.deleteIfExists(Path.of(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
