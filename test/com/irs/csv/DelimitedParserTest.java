package com.irs.csv;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class DelimitedParserTest {

    @Test
    public void testParser(){

        DelimitedParser delimitedParser = new DelimitedParserImpl();

        final String[] input = {"Aziz,\"100, 500, 000\"    , hello, \"good,bye\"",
                "Aziz, 100",
                "Aziz,\"100,000.00\"",
                "Aziz,\"100\"",
                "Aziz,\"He is a \"\"HERO\"\"\"",
                "Aziz,\"He is a \"\"HERO\"\" not a \"\"villain\"\", sike\""
        };

        final String[][] output = {
                new String[] {"Aziz", "100, 500, 000", "hello", "good,bye"}
                , new String[] {"Aziz", "100"}
                , new String[] {"Aziz", "100,000.00"}
                , new String[] {"Aziz", "100"}
                , new String[] {"Aziz", "He is a \"HERO\""}
                , new String[] {"Aziz", "He is a \"HERO\" not a \"villain\", sike"}
        };

        for (int i = 0; i < input.length; i++) {
            final String str = input[i];
            List<String> strings = delimitedParser.parseLine(',', str);

            final String[] expected = output[i];
            final String[] actual = strings.toArray(new String[0]);

            Assertions.assertArrayEquals(expected, actual);
        }
    }

}
