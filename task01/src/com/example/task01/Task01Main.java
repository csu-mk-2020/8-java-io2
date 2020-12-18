package com.example.task01;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Task01Main {
    public static void main(String[] args) {

    }
    public static String extractSoundName(File file) throws IOException, InterruptedException {
        String[] args = {"ffprobe", "-v", "error", "-of", "flat", "-show_format", file.toString()};
        ProcessBuilder processBuilder = new ProcessBuilder(args).redirectOutput(ProcessBuilder.Redirect.PIPE);
        Process process = processBuilder.start();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            return null;
        }
        Pattern pattern = Pattern.compile("format\\.tags\\.title=\"(.+)\"");
        AtomicReference<String> result = new AtomicReference<>();
        bufferedReader.lines().forEach(line -> {
            if (result.get() == null) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.matches()) {
                    result.set(matcher.group(1));
                }
            }
        });
        return result.get();
    }
}
