package util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static util.Constants.INTERNAL_SERVER_ERROR;

class UtilityTest {
    static Utility utility;

    @BeforeEach()
    public void setUp() {
        //given
        utility = new Utility();
    }

    @AfterEach()
    public void cleanUp() {
        utility = null;
    }

    @Test
    void readInputFromResource_withInvalidFile() {
        Exception exception = assertThrows(ExceptionHandling.class, () ->
            utility.readInputFromResource("TestFile")
        );
        assertEquals(INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    @Test
    void writeToFile_NoFileSpecified() {
        Exception exception = assertThrows(ExceptionHandling.class, () ->
            Utility.writeToFile(null, "A 50")
        );
        assertEquals(INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    @Test
    void writeToFile_NoneExisting() {
        Exception exception = assertThrows(ExceptionHandling.class, () ->
            Utility.writeToFile("TestFile", "A 50")
        );
        assertEquals(INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    @Test
    void deleteALineFromFile_NoFileSpecified() {
        Exception exception = assertThrows(ExceptionHandling.class, () ->
            Utility.deleteALineFromFile(null, "A 50")
        );
        assertEquals(INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    @Test
    void deleteALineFromFile_NoneExisting() {
        Exception exception = assertThrows(ExceptionHandling.class, () ->
            Utility.deleteALineFromFile("TestFile", "A 50")
        );
        assertEquals(INTERNAL_SERVER_ERROR, exception.getMessage());
    }
}