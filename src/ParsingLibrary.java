import java.util.ArrayList;
import java.util.List;

public class ParsingLibrary {

    public static List<String> parseLine(String str){

        String[] parsed = str.split(",");
        List<String> finalParsed = new ArrayList<>();

        int j = 0;
        while(j < parsed.length) {

            if(!parsed[j].contains("\""))
                finalParsed.add(parsed[j].strip());
            else{

                String toAdd = quotationAppender(parsed, j);
                toAdd = toAdd.strip();

                if((toAdd.charAt(0) == '\"') && (toAdd.charAt(toAdd.length() - 1) == '\"'))
                    toAdd = toAdd.substring(1, toAdd.length() - 1);
                else if(toAdd.charAt(0) == '\"')
                    toAdd = toAdd.substring(1);
                else
                    toAdd = toAdd.substring(0, toAdd.length() - 1);

                if(!toAdd.contains("\""))
                    finalParsed.add(toAdd);
                else{
                    finalParsed.add(helper(toAdd));
                }
                j += toAdd.chars().filter(ch -> ch == ',').count();
            }
            j++;
        }

        /*for (String s : finalParsed)
            System.out.println(s);*/

        return finalParsed;
    }

    private static String helper(String str){

        StringBuilder newStr = new StringBuilder();
        int i = 0;

        while(i < str.length()){
            if(str.charAt(i) != '\"')
                newStr.append(str.charAt(i));
            else{
                if(((i + 1) < str.length()) && (str.charAt(i + 1) != '\"'))
                    newStr.append(str.charAt(i));

                // if we reached the last character and its a quotation
                if(((i + 1) == str.length()) && (str.charAt(i) == '\"'))
                    newStr.append(str.charAt(i));
            }
            i++;
        }

        return newStr.toString();
    }

    // method goes through array and pieces together a String
    // given a position to where the quotation started
    private static String quotationAppender(String[] strArray, int pos){

        // copy of semi-working code
        /*String returnString = "";
        int i = pos;

        for(; i < strArray.length; i++){
            if((strArray[i].strip().charAt(strArray[i].strip().length() - 1) == '\"'))
                break;

            returnString = returnString + strArray[i] + ",";
        }

        returnString = returnString + strArray[i];

        return returnString.strip();*/

        StringBuilder returnString = new StringBuilder();
        int i = pos;
        int countQuotes = 0;
        String s;

        for(; i < strArray.length; i++){
            s = strArray[i].strip();
            countQuotes += (int) s.chars().filter(ch -> ch == '\"').count();

            if(countQuotes % 2 == 0)
                break;

            returnString.append(strArray[i]).append(",");
        }

        returnString.append(strArray[i]);

        return returnString.toString().strip();
    }

    private static void testParseLine(){


        /*String strArray[] = {"Aziz,\"100, 500, 000\"    , hello, \"good,bye\"",
                "Aziz, 100",
                "Aziz,\"100,000.00\"",
                "Aziz,\"100\"",
                "Aziz,\"He is a \"\"HERO\"\"\"",
                "Aziz,\"He is a \"\"HERO\"\" not a \"\"villain\"\", sike\""};*/

        String strArray[] = {
                "Statistical Learning Theory,\"Vapnik, Vladimir\",data_science,228,\n"
        };

        for(String str : strArray) {
            System.out.println("Input: ");
            System.out.println(str);

            System.out.println("Got: ");
            parseLine(str);

            System.out.println();
            System.out.println();
        }
    }

    public static void main(String[] args) {

        testParseLine();

    }
}
