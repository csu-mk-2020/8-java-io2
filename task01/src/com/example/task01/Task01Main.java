package com.example.task01;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Task01Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        //здесь вы можете вручную протестировать ваше решение, вызывая реализуемый метод и смотря результат
        // например вот так:


        System.out.println(extractSoundName(new File("task01/src/main/resources/3727.mp3")));

    }

    public static String extractSoundName(File file) throws IOException, InterruptedException{
        // your implementation here
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("F:\\dowmload\\ffmpeg-4.3.1-2020-11-08-full_build\\ffmpeg-4.3.1-2020-11-08-full_build\\bin\\ffprobe", "-v", "error", "-of", "flat", "-show_format", file.toString())
                .redirectError(ProcessBuilder.Redirect.INHERIT)
                .redirectOutput(ProcessBuilder.Redirect.PIPE);
        Process process = processBuilder.start();

        int exitValue = process.waitFor();
        if(exitValue!=0){
            System.out.println("Subprocess fail");
            return "";
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))){
            String str;
            while ((str = reader.readLine())!=null){
                if (str.matches("format.tags.title=.*")){
                    return str.replaceFirst("format.tags.title=","").replaceAll("\"","");
                }
            }
        }
        return "";
    }
}
