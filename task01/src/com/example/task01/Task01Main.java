package com.example.task01;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task01Main {
    public static void main(String[] args) throws IOException, InterruptedException {
    }

    public static String extractSoundName(File file) throws IOException, InterruptedException {
        String[] args = {"ffprobe", "-v", "error", "-of", "flat", "-show_format", file.toString()};
        ProcessBuilder pb = new ProcessBuilder(args).redirectOutput(ProcessBuilder.Redirect.PIPE)
                .redirectError(ProcessBuilder.Redirect.INHERIT);
        Process p = pb.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

        if (p.waitFor() != 0) {
            System.out.println("Process failed!");
            return "";
        }

        Pattern re = Pattern.compile("format\\.tags\\.title=\"(.*?)\"");
        while (reader.ready()) {
            String s = reader.readLine();
            Matcher m = re.matcher(s);
            if (m.matches()) {
                return m.group(1);
            }
        }

        return "";
    }
}
