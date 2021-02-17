import org.junit.jupiter.api.Test;
import util.ExceptionHandling;

import static org.junit.jupiter.api.Assertions.*;
import static util.Constants.*;

class UIValidatorTest {

    @Test
    void validateIsNumeric_NullInput() {
        Exception exception = assertThrows(ExceptionHandling.class, () ->
            UIValidator.validateIsNumeric(null)
        );
        assertEquals(NULLFOUND, exception.getMessage());
    }

    @Test
    void validateIsNumericError() {
        Exception exception = assertThrows(ExceptionHandling.class, () ->
            UIValidator.validateIsNumeric("Test")
        );
        assertEquals(NUMERIC_ERROR, exception.getMessage());
    }

    @Test
    void validateIsNumeric() {
        assertDoesNotThrow( () -> UIValidator.validateIsNumeric("123"));
    }

    @Test
    void validateInputFormat_OnlyLettersCheck_WithNumbersAndLettersInInput() {
        Exception exception = assertThrows(ExceptionHandling.class, () ->
                UIValidator.validateInputFormat("B43", REGEX_ONLY_LETTERS, ONLY_LETTERS_ALLOWED)
        );
        assertEquals(ONLY_LETTERS_ALLOWED, exception.getMessage());
    }

    @Test
    void validateInputFormat_OnlyLettersCheck_WithNumbersInInput() {
        Exception exception = assertThrows(ExceptionHandling.class, () ->
            UIValidator.validateInputFormat("2713", REGEX_ONLY_LETTERS, ONLY_LETTERS_ALLOWED)
        );
        assertEquals(ONLY_LETTERS_ALLOWED, exception.getMessage());
    }


    @Test
    void validateInputFormat__OnlyLettersCheck_WithLettersAndCharactersInInput() {
        Exception exception = assertThrows(ExceptionHandling.class, () ->
            UIValidator.validateInputFormat("A/..332", REGEX_ONLY_LETTERS, ONLY_LETTERS_ALLOWED)
        );
        assertEquals(ONLY_LETTERS_ALLOWED, exception.getMessage());

    }

    @Test
    void validateInputFormat__OnlyLettersCheck_WithLettersAndSpace_InInput() {
        Exception exception = assertThrows(ExceptionHandling.class, () ->
            UIValidator.validateInputFormat("A A A A", REGEX_ONLY_LETTERS, ONLY_LETTERS_ALLOWED)
        );
        assertEquals(ONLY_LETTERS_ALLOWED, exception.getMessage());

    }

    @Test
    void validateInputFormat_WithOnlyLetters() {
        assertDoesNotThrow( () -> UIValidator.validateInputFormat("ABBBBB", REGEX_ONLY_LETTERS, ONLY_LETTERS_ALLOWED));
    }

    @Test
    void validateInputFormat_OnlyLettersAndNumbersCheck_WithLettersAndCharactersInInput() {
        Exception exception = assertThrows(ExceptionHandling.class, () ->
            UIValidator.validateInputFormat("B / ///",REGEX_ONLY_LETTERS_AND_NUMBERS, ONLY_LETTERS_AND_NUMBERS_ALLOWED)
        );
        assertEquals(ONLY_LETTERS_AND_NUMBERS_ALLOWED, exception.getMessage());
    }

    @Test
    void validateInputFormat_OnlyLettersAndNumbersCheck_WithNumbersInInput() {
        Exception exception = assertThrows(ExceptionHandling.class, () ->
            UIValidator.validateInputFormat("4 43 434",REGEX_ONLY_LETTERS_AND_NUMBERS, ONLY_LETTERS_AND_NUMBERS_ALLOWED)
        );
        assertEquals(ONLY_LETTERS_AND_NUMBERS_ALLOWED, exception.getMessage());
    }

    @Test
    void validateInputFormat_OnlyLettersAndNumbersCheck_WithCharactersInInput() {
        Exception exception = assertThrows(ExceptionHandling.class, () ->
            UIValidator.validateInputFormat("/ / / ",REGEX_ONLY_LETTERS_AND_NUMBERS, ONLY_LETTERS_AND_NUMBERS_ALLOWED)
        );
        assertEquals(ONLY_LETTERS_AND_NUMBERS_ALLOWED, exception.getMessage());
    }

    @Test
    void validateInputFormat_OnlyLettersAndNumbersCheck_WithOneSpace() {
        Exception exception = assertThrows(ExceptionHandling.class, () ->
            UIValidator.validateInputFormat("A4 50",REGEX_ONLY_LETTERS_AND_NUMBERS, ONLY_LETTERS_AND_NUMBERS_ALLOWED)
        );
        assertEquals(ONLY_LETTERS_AND_NUMBERS_ALLOWED, exception.getMessage());
    }

    @Test
    void validateInputFormat_OnlyLettersAndNumbersCheck_WithTwoSpaceInput() {
        assertDoesNotThrow( () -> UIValidator.validateInputFormat("A 4 50",REGEX_ONLY_LETTERS_AND_NUMBERS, ONLY_LETTERS_AND_NUMBERS_ALLOWED));

    }

    @Test
    void validateInputFormat_OnlyLettersAndNumbersCheck_WithOneSpaceInput() {
        assertDoesNotThrow( () -> UIValidator.validateInputFormat("C 20",REGEX_ONLY_LETTERS_AND_NUMBERS, ONLY_LETTERS_AND_NUMBERS_ALLOWED));

    }


}