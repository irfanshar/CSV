package com.irs.csv;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class DataToDelimiterStrTest {

    @Test
    public void testDataToDelimiterStr(){

        DelimitedParser delimitedParser = new DelimitedParserImpl();

        final String[][] input = {
                new String[] {"Aziz", "100, 500, 000", "hello", "good,bye"}
                , new String[] {"Aziz", "100"}
                , new String[] {"Aziz", "100,000.00"}
                , new String[] {"Aziz", "100"}
                , new String[] {"Aziz", "He is a \"HERO\""}
                , new String[] {"Aziz", "He is a \"HERO\" not a \"villain\", sike"}
        };


        final String[] output = {
                "Aziz,\"100, 500, 000\",hello,\"good,bye\"",
                "Aziz,100",
                "Aziz,\"100,000.00\"",
                "Aziz,100",
                "Aziz,\"He is a \"\"HERO\"\"\"",
                "Aziz,\"He is a \"\"HERO\"\" not a \"\"villain\"\", sike\""
        };

        for(int i  = 0; i < input.length; i++){
            final List<String> inputList = Arrays.asList(input[i]);

            final String expected = output[i];

            final String actual = delimitedParser.dataToDelimiterStr(',', inputList);

            Assertions.assertEquals(expected, actual);

        }
    }

}
