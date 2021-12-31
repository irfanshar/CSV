import java.util.ArrayList;
import java.util.List;

public class ParsingLibrary {

    private static void parseLine(String str){
        String[] parsed = str.split(",");

       /* for (int i = 0; i < parsed.length; i++) {
            // parsed[i] = parsed[i].strip();
            System.out.println(parsed[i]);
        }*/

        List<String> finalParsed = new ArrayList<>();

        int j = 0;
        while(j < parsed.length) {

            if(!parsed[j].contains("\""))
                finalParsed.add(parsed[j].strip());
            else{
//                String toAdd = quotationAppender(parsed, j);
//                toAdd = toAdd.strip().substring(1, toAdd.strip().length() - 1);
//                // finalParsed.add(toAdd);

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

        for (String s : finalParsed)
            System.out.println(s);

    }

    private static String helper(String str){

        String newStr = "";
        int i = 0;

        while(i < str.length()){
            if(str.charAt(i) != '\"')
                newStr += str.charAt(i);
            else{
                if(((i + 1) < str.length()) && (str.charAt(i + 1) != '\"'))
                    newStr += str.charAt(i);

                // if we reached the last character and its a quotation
                if(((i + 1) == str.length()) && (str.charAt(i) == '\"'))
                    newStr += str.charAt(i);
            }
            i++;
        }

        return newStr;
    }

    // method goes through array and pieces together a String
    // given a position to where the quotation started
    private static String quotationAppender(String[] strArray, int pos){

        String returnString = "";
        int i = pos;

        for(; i < strArray.length; i++){
            if((strArray[i].strip().charAt(strArray[i].strip().length() - 1) == '\"'))
                break;

            returnString = returnString + strArray[i] + ",";
        }

        returnString = returnString + strArray[i];

        return returnString.strip();
    }

    private static void testParseLine(){


        String strArray[] = {"Aziz,\"100, 500, 000\"    , hello, \"good,bye\"",
                "Aziz, 100",
                "Aziz,\"100,000.00\"",
                "Aziz,\"100\"",
                "Aziz,\"He is a \"\"HERO\"\"\"",
                "Aziz,\"He is a \"\"HERO\"\" not a \"\"villain\"\", sike\""};

       /* String strArray[] = {
                "Aziz,\"He is a \"\"HERO\"\"\""
                , "Aziz,\"He is a \"\"HERO\"\" not a \"\"villain\"\", sike\""
        };*/

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
