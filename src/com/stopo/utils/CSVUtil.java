package com.stopo.utils;

import java.io.*;

public class CSVUtil {

    public static void createCsv(String nArq1, String csvHead) {
        File file = new File(nArq1);
        if(!file.exists()){
            try(PrintWriter pw = new PrintWriter(new FileWriter(file))){
                pw.write(csvHead);
            } catch(IOException e){
                System.out.println("Erro ao criar o arquivo CSV: " + e.getMessage());
            }
        }
    }

    public static void saveCSV(String nArq1, String csvHead, String[] fields) {
        try(PrintWriter pw = new PrintWriter(new FileWriter(nArq1))) {
            pw.print(csvHead);
            for(String line:fields){
                if(line != null){
                    pw.print(line);
                }
            }
        }catch(IOException e){
            System.out.println("Erro ao salvar o arquivo CSV: " + e.getMessage());
        }
    }

    public static String[] readAllCSV(String nArq1) {
        int allLines = 0;
        try(BufferedReader br = new BufferedReader(new FileReader(nArq1))) {
            br.readLine();
            while(br.readLine() != null) {
                allLines++;
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo CSV: " + e.getMessage());
            return new String[0];
        }

        String[] lines = new String[allLines];

        try(BufferedReader br = new BufferedReader(new FileReader(nArq1))) {
            br.readLine();
            String line;
            int index = 0;
            while((line = br.readLine()) != null) {
                if(!line.trim().isEmpty()) {
                    lines[index] = line;
                    index++;
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo CSV: " + e.getMessage());
        }
        return lines;
    }
}
