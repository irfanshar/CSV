import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Parser {

    // the csv file being parsed
    String fileName;

    // arraylist of the headers
    ArrayList<String> headers;

    // == constructor ==
    public Parser(String fileName) {
        this.fileName = fileName;
        this.headers = outputHeaders();
    }

    // == ArrayList extra methods ==

    // outputs all elements of a String ArrayList
    private void outputStringArrayList(ArrayList<String> arrayList) {
        for (String str : arrayList)
            System.out.println(str);
    }


    // == parsing methods ==

    // parses a line with comma separated values with no
    // edge cases
    //TODO base cases
    private String[] parseLineNoEdgeCases(String line) {
        return line.split(",");
    }

    // returns an ArrayList of the headers of the file
    private ArrayList<String> outputHeaders() {

        ArrayList<String> headerList = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.fileName));
            String line = reader.readLine();
            reader.close();

            // array storing the column headers
            String[] array = parseLineNoEdgeCases(line);

            // adds delimited line to the ArrayList
            Collections.addAll(headerList, array);

        } catch (IOException e) {

            // program exits if file could not be read
            e.printStackTrace();
            System.exit(-1);
        }

        return headerList;
    }

    // method returns an ArrayList of Strings containing
    // all elements in a column (excluding the header)
    // returns null if there is no column with that header
    private ArrayList<String> columnRecord(String column) {

        ArrayList<String> columnRecordList = null;

        // there is no column with String column
        if (!headers.contains(column))
            System.out.println("The file does not contain a column: " + column);
        else {

            try {
                BufferedReader reader = new BufferedReader(new FileReader(fileName));

                // initializes the ArrayList
                columnRecordList = new ArrayList<>();

                // position in the line
                int pos = headers.indexOf(column);

                // skips the first line (header line)
                String line = reader.readLine();
                line = reader.readLine();

                // each line separated by a different delimiter
                String[] lineList;

                // reading every line of the file
                while (line != null) {

                    // getting the array of separated items in the line
                    lineList = parseLineNoEdgeCases(line);


                    // adds the item to the array list
                    columnRecordList.add(lineList[pos]);

                    // reads the next line
                    line = reader.readLine();
                }

                // closes the buffered reader
                reader.close();

            } catch (IOException e) {
                // program exits if file could not be read
                e.printStackTrace();
                System.exit(-1);
            }
        }

        // returns null if there is a problem reading the file
        // nothing to be done as method already prints appropriate message
        return columnRecordList;
    }

    // given an array of strings an a char c, returns
    // a string with all of the elements of the array separated by c
    private String arrayToStringDelimiter(String[] strArray, char c){

        String returnString = "";
        for(String str : strArray)
            returnString += str + c;

        return returnString;
    }

    // takes a file and changes the delimiter and writes that new file
    // returns true if successful, false if unsuccessful
    private boolean changeDelimiter(char c, String newFileName) {

        try {

            // used to write to the new file
            BufferedWriter writer = new BufferedWriter(new FileWriter(newFileName));

            // used to read from the current file
            BufferedReader reader = new BufferedReader(new FileReader(fileName));

            String line = reader.readLine();

            while (line != null) {
                writer.write(arrayToStringDelimiter(parseLineNoEdgeCases(line), c));
                writer.newLine();
                line = reader.readLine();
            }

            reader.close();
            writer.close();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
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
            System.out.println("3. Replace Delimiter to one of your choice. New file will be created.");
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

                    ArrayList<String> recordList = columnRecord(scanner.next());

                    if (recordList != null)
                        outputStringArrayList(recordList);
                    break;

                case 3:
                    System.out.println("Please input a character to replace the current delimiter: ");
                    char c = scanner.next().charAt(0);
                    System.out.println("Please enter the name of the new file: ");
                    changeDelimiter(c, scanner.next());
                    break;

                default:
                    System.out.println("Not a valid option.");

            }
            System.out.println();
        } while (command != 0);
    }

    public static void main(String[] args) {

        Parser parse = new Parser("UCLfinals.csv");

        parse.commands();

    }
}
