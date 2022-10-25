package de.riagade.modular.chess;

public enum CastlingOptions {
    BOTH("kq"), KING("k"), QUEEN("q"), NONE("");

    private final String value;

    CastlingOptions(String value) {
        this.value = value;
    }

    public static CastlingOptions fromValue(String value) {
        for(var option : values()) {
            if(option.value.equals(value.toLowerCase())) {
                return option;
            }
        }
        throw new IllegalArgumentException("no CastlingOptions found for the given value");
    }
}
