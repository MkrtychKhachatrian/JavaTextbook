package khachatrian.model.enums;

import khachatrian.util.WrongInputException;

public enum Material {
    GLOSS ("gloss"),
    PAPER("paper");
    private String material;

    Material(String material) {
        this.material = material;
    }

    public String getmaterial() {
        return material;
    }

    public static Material getTypeByUrl(String url) throws WrongInputException {
        for (Material env : values()) {
            if (env.getmaterial().equals(url)) {
                return env;
            }
        }
        throw new WrongInputException("No enum found with url: [" + url + "] in Material");
    }

    @Override
    public String toString() {
        return material;
    }
}
