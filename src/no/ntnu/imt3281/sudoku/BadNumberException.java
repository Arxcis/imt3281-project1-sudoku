package no.ntnu.imt3281.sudoku;

@SuppressWarnings("serial")
public class BadNumberException extends IllegalArgumentException {
    public BadNumberException(String errorMessage) {
        super(errorMessage);
    }
}
