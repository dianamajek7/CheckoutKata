import org.junit.jupiter.api.Test;
import util.ExceptionHandling;

import static org.junit.jupiter.api.Assertions.*;
import static util.Constants.*;

class UIValidatorTest {

    @Test
    public void validateIsNumeric_NullInput() {
        Exception exception = assertThrows(ExceptionHandling.class, () ->
                //given
            UIValidator.validateIsNumeric(null)
        );
        //then
        assertEquals(NULLFOUND, exception.getMessage());
    }

    @Test
    public void validateIsNumericError() {
        Exception exception = assertThrows(ExceptionHandling.class, () ->
                //given
            UIValidator.validateIsNumeric("Test")
        );
        //then
        assertEquals(NUMERIC_ERROR, exception.getMessage());
    }

    @Test
    public void validateIsNumeric() {
        //given
        assertDoesNotThrow( () -> UIValidator.validateIsNumeric("123"));
    }

    @Test
    public void validateInputFormat_OnlyLettersCheck_WithNumbersAndLettersInInput() {
        Exception exception = assertThrows(ExceptionHandling.class, () ->
                //given
                UIValidator.validateInputFormat("B43", REGEX_ONLY_LETTERS, ONLY_LETTERS_ALLOWED)
        );
        //then
        assertEquals(ONLY_LETTERS_ALLOWED, exception.getMessage());
    }

    @Test
    public void validateInputFormat_OnlyLettersCheck_WithNumbersInInput() {
        Exception exception = assertThrows(ExceptionHandling.class, () ->
                //given
            UIValidator.validateInputFormat("2713", REGEX_ONLY_LETTERS, ONLY_LETTERS_ALLOWED)
        );
        //then
        assertEquals(ONLY_LETTERS_ALLOWED, exception.getMessage());
    }


    @Test
    public void validateInputFormat__OnlyLettersCheck_WithLettersAndCharactersInInput() {
        Exception exception = assertThrows(ExceptionHandling.class, () ->
                //given
            UIValidator.validateInputFormat("A/..332", REGEX_ONLY_LETTERS, ONLY_LETTERS_ALLOWED)
        );
        //then
        assertEquals(ONLY_LETTERS_ALLOWED, exception.getMessage());

    }

    @Test
    public void validateInputFormat__OnlyLettersCheck_WithLettersAndSpace_InInput() {
        Exception exception = assertThrows(ExceptionHandling.class, () ->
                //given
            UIValidator.validateInputFormat("A A A A", REGEX_ONLY_LETTERS, ONLY_LETTERS_ALLOWED)
        );
        //then
        assertEquals(ONLY_LETTERS_ALLOWED, exception.getMessage());

    }

    @Test
    public void validateInputFormat_WithOnlyLetters() {
        assertDoesNotThrow( () -> UIValidator.validateInputFormat("ABBBBB", REGEX_ONLY_LETTERS, ONLY_LETTERS_ALLOWED));
    }

    @Test
    public void validateInputFormat_OnlyLettersAndNumbersCheck_WithLettersAndCharactersInInput() {
        Exception exception = assertThrows(ExceptionHandling.class, () ->
                //given
            UIValidator.validateInputFormat("B / ///",REGEX_ONLY_LETTERS_AND_NUMBERS, ONLY_LETTERS_AND_NUMBERS_ALLOWED)
        );
        //then
        assertEquals(ONLY_LETTERS_AND_NUMBERS_ALLOWED, exception.getMessage());
    }

    @Test
    public void validateInputFormat_OnlyLettersAndNumbersCheck_WithNumbersInInput() {
        Exception exception = assertThrows(ExceptionHandling.class, () ->
                //given
            UIValidator.validateInputFormat("4 43 434",REGEX_ONLY_LETTERS_AND_NUMBERS, ONLY_LETTERS_AND_NUMBERS_ALLOWED)
        );
        //then
        assertEquals(ONLY_LETTERS_AND_NUMBERS_ALLOWED, exception.getMessage());
    }

    @Test
    public void validateInputFormat_OnlyLettersAndNumbersCheck_WithCharactersInInput() {
        Exception exception = assertThrows(ExceptionHandling.class, () ->
                //given
            UIValidator.validateInputFormat("/ / / ",REGEX_ONLY_LETTERS_AND_NUMBERS, ONLY_LETTERS_AND_NUMBERS_ALLOWED)
        );
        //then
        assertEquals(ONLY_LETTERS_AND_NUMBERS_ALLOWED, exception.getMessage());
    }

    @Test
    public void validateInputFormat_OnlyLettersAndNumbersCheck_WithOneSpace() {
        Exception exception = assertThrows(ExceptionHandling.class, () ->
                //given
            UIValidator.validateInputFormat("A4 50",REGEX_ONLY_LETTERS_AND_NUMBERS, ONLY_LETTERS_AND_NUMBERS_ALLOWED)
        );
        //then
        assertEquals(ONLY_LETTERS_AND_NUMBERS_ALLOWED, exception.getMessage());
    }

    @Test
    public void validateInputFormat_OnlyLettersAndNumbersCheck_WithTwoSpaceInput() {
        //given
        assertDoesNotThrow( () -> UIValidator.validateInputFormat("A 4 50",REGEX_ONLY_LETTERS_AND_NUMBERS, ONLY_LETTERS_AND_NUMBERS_ALLOWED));

    }

    @Test
    public void validateInputFormat_OnlyLettersAndNumbersCheck_WithOneSpaceInput() {
        //given
        assertDoesNotThrow( () -> UIValidator.validateInputFormat("C 20",REGEX_ONLY_LETTERS_AND_NUMBERS, ONLY_LETTERS_AND_NUMBERS_ALLOWED));
    }

}