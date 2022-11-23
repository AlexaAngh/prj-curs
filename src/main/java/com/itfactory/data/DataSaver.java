package com.itfactory.data;

import com.itfactory.model.Course;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class DataSaver {


    public Boolean saveCursuri(String newline) {

        String old = readfile("cursuri.csv");
        writefile("cursuri.csv",old + newline);
        return true;
    }


    public Boolean saveStudent(String newline) {

        String old = readfile("studenti.csv");
        writefile("studenti.csv",old + newline);
        return true;
    }

    public Boolean inrolareStudent(String newline) {

        String old = readfile("mapari.csv");
        writefile("mapari.csv",old + newline);
        return true;
    }


    public static void writefile(String path, String text) {
        try {

            File myfile = new File(path);
            myfile.createNewFile();

            //write to file
            FileWriter wf = new FileWriter(path);

            wf.write(text);

            wf.close();

        } catch (IOException e) {
            System.out.println("error");
            e.printStackTrace();
        }

    }

    public static String readfile(String path) {
        try {

            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);

            String csv="";
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                csv=csv + data + "\n";
            }
            myReader.close();
            return csv;

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return "";
        }

    }

}
