package com.assignment2.book_store;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.fail;

@Slf4j
public class LearnAboutUuidTest {

    private static final Integer numberOfCharacters = getAUuid().length();
    private static final Integer numberOfTests = 100_000;

    private static String getAUuid() {
        return UUID.randomUUID().toString();
    }

    @Test
    public void checkConstantNumberOfCharactersInUUID() {
        for (int i = 0; i < numberOfTests; i++) {
            if (getAUuid().length() != numberOfCharacters) {
                fail("The uuid has a different number of characters every time");
            }
        }
        log.info("It actually has the same number of characters: {}", numberOfCharacters);
    }

    @Test
    public void checkIfTheUuidsAreUnique() {
        Set<String> checkSet = new TreeSet<>();
        for (int i = 0; i < numberOfTests; i++) {
            checkSet.add(getAUuid());
        }
        if (checkSet.size() != numberOfTests) {
            fail("The UUIDs are not unique");
        }
        log.info("The UUIDs are unique!");
    }


}
