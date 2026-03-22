package com.qa.tests;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


public class Test1 {

    public static void main(String[] args) {

        String path = "src/com/qa/file/IDs.txt";

//        String scid = getUnusedScenarioId(path);
//        System.out.println(scid);

        appendMoreScenarioIDs(path);

//        usedScenarioCleanUp(path, "|U");


    }

    public static String getUnusedScenarioId(String sourceFilePath){

        usedScenarioCleanUp(sourceFilePath, "|U");

        Path filePath = Paths.get(sourceFilePath);

        int n = 0;
        String str = "";

        try{
            List<String> lines = Files.readAllLines(filePath);

            for(int i=0; i<lines.size(); i++){

                if(!lines.get(i).contains("|U")){
                    lines.set(i, lines.get(i) + "|U");

                    str = lines.get(i);
                    break;
                }
            }
            Files.write(filePath, lines);

        }catch (IOException e){
            e.printStackTrace();
        }

        return str;
    }

    public static void appendMoreScenarioIDs(String sourceFilePath){

        Path path = Paths.get(sourceFilePath);

        int n = 0;
        int scn = 0;

        String currentLine;
        String  lastLine = null;

        try{

            try (BufferedReader br = new BufferedReader(new FileReader(String.valueOf(path)))) {
                while ((currentLine = br.readLine()) != null) {
                    lastLine = currentLine;
                    n = n + 1;
                }
                if(lastLine != null){
                    String[] splitStr = lastLine.split("-");
                    scn = Integer.parseInt(splitStr[1]);
                }

                if(n <= 10){
                    for(int j=1; j<=20; j++){
                        scn = scn + 1;
                        Files.writeString(path, "\nS-" + scn, java.nio.file.StandardOpenOption.APPEND);
                    }
                }
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void usedScenarioCleanUp(String filePath, String strUsedScenario){
        Path path = Paths.get(filePath);

        try{

            // Read all lines from the file
            List<String> lines = Files.readAllLines(path);

            // Filter out lines that meet the condition
            List<String> updatedLines = lines.stream()
                    .filter(line -> !line.contains(strUsedScenario))
                    .collect(Collectors.toList());

            // Write the updated list of lines back to the same file
            Files.write(path, updatedLines);

        }catch (IOException e){
            e.printStackTrace();
        }

    }


}
