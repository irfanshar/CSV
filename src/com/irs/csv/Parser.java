package com.irs.csv;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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

    // Method returns a List of the headers of the file
    // May throws IOException when reading file
    public List<String> outputHeaders() throws IOException {

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
    public List<String> columnRecord(String column) throws IOException {

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

/*
    public List<List<String>> fetchAllData(String... cols) throws IOException {

    }
*/

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
    public void save(char newDelimiter, String newFileName) throws IOException {

//        try {

            // used to write to the new file
            BufferedWriter writer = new BufferedWriter(new FileWriter(newFileName));

            // used to read from the current file
            BufferedReader reader = new BufferedReader(new FileReader(fileName));

            String line = reader.readLine();

            while (line != null) {
                writer.write(listToStringDelimiter(ParsingLibrary.parseLine(line), newDelimiter));
                writer.newLine();
                line = reader.readLine();
            }

            reader.close();
            writer.close();

    }
}
