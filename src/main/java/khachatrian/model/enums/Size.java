package khachatrian.model.enums;

import khachatrian.util.WrongInputException;

public enum Size {
    A4("A4"),
    SRA5("SRA5"),
    A5("A5");
    private String size_s;

    Size(String size_s) {
        this.size_s = size_s;
    }

    public String getSize_s() {
        return size_s;
    }

    public static Size getTypeByUrl(String url) throws WrongInputException {
        for (Size env : values()) {
            if (env.getSize_s().equals(url)) {
                return env;
            }
        }
        throw new WrongInputException("None size found with url: [" + url + "]");
    }

    @Override
    public String toString() {
        return "Size: "+ size_s;
    }
}
