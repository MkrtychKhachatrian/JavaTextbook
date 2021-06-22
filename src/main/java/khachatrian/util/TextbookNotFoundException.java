package khachatrian.util;

import khachatrian.model.enums.Subject;
import khachatrian.model.enums.Size;

import java.util.NoSuchElementException;

public class TextbookNotFoundException extends NoSuchElementException {

    public TextbookNotFoundException(String name) {
        super("No textbook with name: " + name + " found");
    }
    public TextbookNotFoundException(long id) {
        super("No textbook with " + id + " id found");
    }
    public TextbookNotFoundException(Size s) {
        super("No textbook with " + s + " size found");
    }
    public TextbookNotFoundException(Subject sub) {
        super("No textbook with " + sub + " subject found");
    }
}
