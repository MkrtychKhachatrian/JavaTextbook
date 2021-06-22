package khachatrian.model.enums;

import khachatrian.util.WrongInputException;

public enum Subject {
    MATH("Math"),
    SCIENCE("Science"),
    BIOLOGY("Biology"),
    HISTORY("History"),
    ENGLISH("English");
    private String subject_s;

    Subject(String subject_s) {
        this.subject_s = subject_s;
    }

    public String getSubject_s() {
        return subject_s;
    }

    public static Subject getTypeByUrl(String url) throws WrongInputException {
        for (Subject env : values()) {
            if (env.getSubject_s().equals(url)) {
                return env;
            }
        }
        throw new WrongInputException("None subject found with url: [" + url + "]");
    }

    @Override
    public String toString() {
        return "Subject: "+ subject_s;
    }
}
