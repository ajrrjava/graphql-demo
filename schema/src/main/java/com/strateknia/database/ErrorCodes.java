package com.strateknia.database;

public enum ErrorCodes {

    NORMAL("Normal termination", 0),
    DATABASE_ERROR("Database error", 100)
    ;

    private final String name;
    private final Integer code;

    ErrorCodes(String name, Integer code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public Integer getCode() {
        return code;
    }
}
