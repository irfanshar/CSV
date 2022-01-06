package com.irs.csv;

import java.io.IOException;
import java.util.List;

public interface Parser {

    // Method returns a List of the headers of the file
    // May throws IOException when reading file
    List<String> getHeaders() throws IOException;

    // Method returns an List of Strings containing
    // all elements in a column (excluding the header)
    // Returns null if there is no column with that header
    List<String> columnRecord(String column) throws IOException;


    // takes a file and changes the delimiter and writes that new file
    // returns true if successful, false if unsuccessful
    void save(char newDelimiter, String newFileName) throws IOException;


}

