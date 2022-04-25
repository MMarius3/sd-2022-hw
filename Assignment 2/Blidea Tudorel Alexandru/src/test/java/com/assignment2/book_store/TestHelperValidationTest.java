package com.assignment2.book_store;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@Slf4j
public class TestHelperValidationTest {

    private static final Integer numberOfTests = 100_000;

    @Test
    public void isGetRandomStringRight_32() {
        assertEquals(32, TestHelper.getRandomString(32).length());
    }

    @Test
    public void isGetRandomStringRight_generic() {
        for (int i = 0; i < numberOfTests; i++) {
            int expected = TestHelper.getRandomIntegerBetween(1, 10000);
            assertEquals(expected, TestHelper.getRandomString(expected).length());
        }
    }

    @Test
    public void getRandomIntegerRangeTest() {
        int li = 20, ls = 2000;
        for (int i = 0; i < numberOfTests; i++) {
            int generatedInteger = TestHelper.getRandomIntegerBetween(li, ls);
            if (!(li <= generatedInteger && generatedInteger <= ls)) {
                String message = String.format("Generated integer (%d) is not between [%d, %d]", generatedInteger, li, ls);
                log.error(message);
                fail(message);
            }
        }
    }

    @Test
    public void getRandomIntegerRangeNegativeTest() {
        int li = -2000, ls = 20;
        for (int i = 0; i < numberOfTests; i++) {
            int generatedInteger = TestHelper.getRandomIntegerBetween(li, ls);
            if (!(li <= generatedInteger && generatedInteger <= ls)) {
                String message = String.format("Generated integer (%d) is not between [%d, %d]", generatedInteger, li, ls);
                log.error(message);
                fail(message);
            }
        }
    }

}
