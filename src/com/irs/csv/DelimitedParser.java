package com.irs.csv;
import java.util.List;

public interface DelimitedParser {

    List<String> parseLine(char delimiter, String str);

    //TODO may have to delete this
    List<String> parseLine2(char delimiter, String str, int headerSize);

    String dataToDelimiterStr(char delimiter, List<String> dataList, int headerSize);

}
