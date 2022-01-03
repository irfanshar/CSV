package com.irs.csv;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Parser {

    // the csv file being parsed
    String fileName;

    // arraylist of the headers
    List<String> headers;

    // == constructor ==
    public Parser(String fileName) throws IOException {
        this.fileName = fileName;
        this.headers = outputHeaders();
    }

    // == ArrayList extra methods ==

    // outputs all elements of a String ArrayList
    private void outputStringArrayList(List<String> arrayList) {
        for (String str : arrayList)
            System.out.println(str);
    }


    // == parsing methods ==
    // parses a line with comma separated values with no
    // edge cases
    // TODO DELETE (useless now)
    /*private List<String> parseLineNoEdgeCases(String line) {

        String[] parsedLine = line.split(",");
        ArrayList<String> arrayList = new ArrayList<>();

        // to clean up trailing and leading spaces
        for (int i = 0; i < parsedLine.length; i++) {
            parsedLine[i] = parsedLine[i].strip();
            arrayList.add(parsedLine[i]);
        }
        return arrayList;
    }*/

    // parses a line with edge cases (DOES NOT WORK)
    /*private ArrayList<String> untilDelimiter(String str) {

        String[] list = str.split(",");

        *//*for (String s : list)
            System.out.println(s);*//*

        boolean inQuotation = false;
        ArrayList<String> arrayList = new ArrayList<>();
        String inQuoteString = "";

        for (int i = 0; i < list.length; i++) {

            if (!(list[i].charAt(0) == '\"') && !inQuotation) {

                if (inQuoteString != "") {
                    arrayList.add(inQuoteString);
                    inQuoteString = "";
                }

                arrayList.add(list[i]);
            } else {

                if (list[i].charAt(list[i].length() - 1) == '\"') {
                    inQuoteString += list[i];
                    inQuotation = false;
                } else {
                    inQuoteString += list[i] + ",";
                    inQuotation = true;
                }

                *//*if(inQuoteString.charAt(inQuoteString.length() - 1) == '\"')
                    inQuotation = false;
                else
                    inQuotation = true;*//*
            }
        }

        *//*Iterator iter = arrayList.iterator();

        while(iter.hasNext())
            System.out.println(iter.next());*//*

        return arrayList;
    }*/


    // Method returns a List of the headers of the file
    // May throws IOException when reading file
    private List<String> outputHeaders() throws IOException {

        List<String> headerList = new ArrayList<>();

//        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.fileName));
            String line = reader.readLine();
            reader.close();

            headerList = ParsingLibrary.parseLine(line);

        /*} catch (IOException e) {

            // program exits if file could not be read
            e.printStackTrace();
            System.exit(-1);
        }*/

        return headerList;
    }

    // Method returns an List of Strings containing
    // all elements in a column (excluding the header)
    // Returns null if there is no column with that header
    private List<String> columnRecord(String column) throws IOException {

        List<String> columnRecordList = null;

        // there is no column with String column
        if (!headers.contains(column))
            System.out.println("The file does not contain a column: " + column);
        else {

            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

                // initializes the ArrayList
                columnRecordList = new ArrayList<>();

                // position in the line
                int pos = headers.indexOf(column);

                // skips the first line (header line)
                String line = reader.readLine();
                line = reader.readLine();

                // each line separated by a different delimiter
                String[] lineList;

                // reading every line of the file until reaching EOF or empty lines
                while (line != null && !line.equals("")) {

                    // getting the array of separated items in the line
                    lineList = ParsingLibrary.parseLine(line).toArray(new String[0]);

                    // adds the item to the array list
                    // System.out.println(lineList[pos]);
                    columnRecordList.add(lineList[pos]);

                    // reads the next line
                    line = reader.readLine();
                }

            }
        }

        // returns null if there is a problem reading the file
        // nothing to be done as method already prints appropriate message
        return columnRecordList;
    }

    // given a List of strings and a char c, returns
    // a string with all of the elements of the array separated by c
    private String listToStringDelimiter(List<String> strArray, char c) {

        StringBuilder returnString = new StringBuilder();
        for (String str : strArray)
            returnString.append(str).append(c);

        return returnString.toString();
    }

    // takes a file and changes the delimiter and writes that new file
    // returns true if successful, false if unsuccessful
    private boolean changeDelimiter(char c, String newFileName) throws IOException {

//        try {

            // used to write to the new file
            BufferedWriter writer = new BufferedWriter(new FileWriter(newFileName));

            // used to read from the current file
            BufferedReader reader = new BufferedReader(new FileReader(fileName));

            String line = reader.readLine();

            while (line != null) {
                writer.write(listToStringDelimiter(ParsingLibrary.parseLine(line), c));
                writer.newLine();
                line = reader.readLine();
            }

            reader.close();
            writer.close();

            return true;
       /* } catch (IOException e) {
            e.printStackTrace();
            return false;
        }*/
    }


    private void commands() {

        /* Options
         *  1. Output to Console all the headers (separated by new lines)
         *  2. Output to Console all the records in a column (given a String header)
         */

        Scanner scanner = new Scanner(System.in);

        int command;

        do {

            // == option list to user ==
            System.out.println("Choose one of the options: ");

            System.out.println("1. Output to Console all the headers (separated by new lines)");
            System.out.println("2. Output to Console all the records in a column (given a String header)");
            System.out.println("3. Replace delimiter to one of your choice. New file will be created.");
            System.out.println("0. Exit");

            command = scanner.nextInt();
            switch (command) {

                case 0:
                    return;

                case 1:
                    outputStringArrayList(headers);
                    break;

                case 2:
                    System.out.println("Which column would you like to see records for?: ");

                    List<String> recordList = null;
                    try {
                        recordList = columnRecord(scanner.next());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (recordList != null)
                        outputStringArrayList(recordList);
                    break;

                case 3:
                    System.out.println("Please input a character to replace the current delimiter: ");
                    char c = scanner.next().charAt(0);
                    System.out.println("Please enter the name of the new file: ");

                    try {
                        changeDelimiter(c, scanner.next());
                    } catch (IOException e) {
                        System.out.println("There was a problem processing: " + fileName);
                        e.printStackTrace();
                    }
                    break;

                default:
                    System.out.println("Not a valid option.");

            }
            System.out.println();
        } while (command != 0);
    }



    public static void main(String[] args) {

        String file = "books_quoteInDouble.csv";

        try {
            Parser parse = new Parser(file);
            parse.commands();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
