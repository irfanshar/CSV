package com.irs.csv;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class DelimitedParserImpl implements DelimitedParser {


    @Override
    public List<String> parseLine(char delimiter, String str) {

        StringTokenizer stringTokenizer = new StringTokenizer(str, new String(new char[]{delimiter}));
        String[] parsed = new String[stringTokenizer.countTokens()];

        int i = 0;
        while (stringTokenizer.hasMoreTokens()) {
            parsed[i++] = stringTokenizer.nextToken();
        }

        List<String> finalParsed = new ArrayList<>();

        int j = 0;
        while (j < parsed.length) {

            if (!parsed[j].contains("\""))
                finalParsed.add(parsed[j].strip());
            else {

                String toAdd = quotationAppender(parsed, j);
                toAdd = toAdd.strip();

                if ((toAdd.charAt(0) == '\"') && (toAdd.charAt(toAdd.length() - 1) == '\"'))
                    toAdd = toAdd.substring(1, toAdd.length() - 1);
                else if (toAdd.charAt(0) == '\"')
                    toAdd = toAdd.substring(1);
                else
                    toAdd = toAdd.substring(0, toAdd.length() - 1);

                if (!toAdd.contains("\""))
                    finalParsed.add(toAdd);
                else {
                    finalParsed.add(helper(toAdd));
                }
                j += toAdd.chars().filter(ch -> ch == ',').count();
            }
            j++;
        }

        return finalParsed;
    }

    public List<String> parseLine2(char delimiter, String str, int headerSize) {


        String[] parsed = str.split(delimiter == '|' ? "\\|" : ("" + delimiter));

        int i = 0;


        List<String> finalParsed = new ArrayList<>();

        int j = 0;
        while (j < parsed.length) {

            if (parsed[j] != null) {
                if (!parsed[j].contains("\""))
                    finalParsed.add(parsed[j].strip());
                else {

                    String toAdd = quotationAppender(parsed, j);
                    toAdd = toAdd.strip();

                    if ((toAdd.charAt(0) == '\"') && (toAdd.charAt(toAdd.length() - 1) == '\"'))
                        toAdd = toAdd.substring(1, toAdd.length() - 1);
                    else if (toAdd.charAt(0) == '\"')
                        toAdd = toAdd.substring(1);
                    else
                        toAdd = toAdd.substring(0, toAdd.length() - 1);

                    if (!toAdd.contains("\""))
                        finalParsed.add(toAdd);
                    else {
                        finalParsed.add(helper(toAdd));
                    }
                    j += toAdd.chars().filter(ch -> ch == ',').count();
                }
            } else {
                finalParsed.add(null);
            }

            j++;
        }

       /* if (parsed.length < headerSize) {
            String[] parsedProper = new String[headerSize];
            int length = 0;
            i = 0;

            for (String s : finalParsed) {
                length += s.length();

                if (length < (str.length() - 1)) {
                    if ((str.charAt(length) == delimiter) && (str.charAt(length + 1) == delimiter)) {
                        parsedProper[i++] = s;
                        parsedProper[i++] = null;
                        length += 2;
                    } else {
                        parsedProper[i++] = s;
                        length += 1;
                    }
                } else {
                    if ((length != str.length()) && (str.charAt(length) == delimiter)) {
                        parsedProper[i++] = s;
                        parsedProper[i] = null;
                    }

                }

//                i++;
            }

//            parsed = parsedProper
            return Arrays.asList(parsedProper);
        }
*/

        return finalParsed;
    }

    private String helper(String str) {

        StringBuilder newStr = new StringBuilder();
        int i = 0;

        while (i < str.length()) {
            if (str.charAt(i) != '\"')
                newStr.append(str.charAt(i));
            else {
                if (((i + 1) < str.length()) && (str.charAt(i + 1) != '\"'))
                    newStr.append(str.charAt(i));

                // if we reached the last character and its a quotation
                if (((i + 1) == str.length()) && (str.charAt(i) == '\"'))
                    newStr.append(str.charAt(i));
            }
            i++;
        }

        return newStr.toString();
    }

    private String quotationAppender(String[] strArray, int pos) {


        StringBuilder returnString = new StringBuilder();
        int i = pos;
        int countQuotes = 0;
        String s;

        for (; i < strArray.length; i++) {
            s = strArray[i].strip();
            countQuotes += (int) s.chars().filter(ch -> ch == '\"').count();

            if (countQuotes % 2 == 0)
                break;

            returnString.append(strArray[i]).append(",");
        }

        returnString.append(strArray[i]);

        return returnString.toString().strip();
    }


    /*@Override
    public String dataToDelimiterStr(char delimiter, List<String> dataList) {
        StringBuilder retStr = new StringBuilder();

        for(String s : dataList) {

            if (!s.contains("\"")) {
                if (!s.contains(","))
                    retStr.append(s).append(",");
                else
                    retStr.append("\"").append(s).append("\"").append(",");
            }else{
                String str = addQuotesAroundQuotes(s);
                str = "\"" + str + "\"";
                retStr.append(str).append(",");
            }
        }

        return retStr.substring(0, retStr.length() - 1);
    }*/

    @Override
    public String dataToDelimiterStr(char delimiter, List<String> dataList, int headersSize) {
        StringBuilder retStr = new StringBuilder();

        for (String s : dataList) {

            if (s != null) {
                if (!s.contains("\"")) {
                    if (!s.contains(delimiter + ""))
                        retStr.append(s).append(delimiter);
                    else
                        retStr.append("\"").append(s).append("\"").append(delimiter);
                } else {
                    String str = addQuotesAroundQuotes(s);
                    str = "\"" + str + "\"";
                    retStr.append(str).append(delimiter);
                }
            } else {
                retStr.append(delimiter);
            }
        }

        retStr.deleteCharAt(retStr.length() - 1);

        int sizeOfList = dataList.size();
        while (sizeOfList < headersSize) {
            retStr.append(delimiter);
            sizeOfList++;

        }

        return retStr.toString();
    }

    private String addQuotesAroundQuotes(String str) {
        StringBuilder retStr = new StringBuilder();

        for (int i = 0; i < str.length(); i++) {

            if (str.charAt(i) != '\"')
                retStr.append(str.charAt(i));
            else
                retStr.append("\"").append(str.charAt(i));

        }

        return retStr.toString();
    }
}
