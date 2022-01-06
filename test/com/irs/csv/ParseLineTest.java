package com.irs.csv;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class ParseLineTest {

    @Test
    public void testParser(){

        DelimitedParser delimitedParser = new DelimitedParserImpl();

        final String[] input = {
                "Aziz,\"100, 500, 000\"    , hello, \"good,bye\"",
                "Aziz, 100",
                "Aziz,\"100,000.00\"",
                "Aziz,\"100\"",
                "Aziz,\"He is a \"\"HERO\"\"\"",
                "Aziz,\"He is a \"\"HERO\"\" not a \"\"villain\"\", sike\"",
                "One,,Two"
        };

        final int[] inputHeaderSizes = {
                4,
                2,
                2,
                2,
                2,
                2,
                3
        };

        final String[][] output = {
                new String[] {"Aziz", "100, 500, 000", "hello", "good,bye"}
                , new String[] {"Aziz", "100"}
                , new String[] {"Aziz", "100,000.00"}
                , new String[] {"Aziz", "100"}
                , new String[] {"Aziz", "He is a \"HERO\""}
                , new String[] {"Aziz", "He is a \"HERO\" not a \"villain\", sike"}
                , new String[] {"One", "", "Two"}
        };

        for (int i = 0; i < input.length; i++) {
            final List<String> expected = Arrays.asList(output[i]);

            final String str = input[i];
            List<String> actual = delimitedParser.parseLine2(',', str, inputHeaderSizes[i]);


            Assertions.assertEquals(expected, actual);
        }
    }


}
