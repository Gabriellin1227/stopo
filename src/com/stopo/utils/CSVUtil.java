package com.stopo.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

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
}
