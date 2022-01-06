package com.irs.csv;
import java.util.List;

public interface DelimitedParser {

    List<String> parseLine(char delimiter, String str);

    String dataToDelimiterStr(char delimiter, List<String> dataList, int headerSize);

}
