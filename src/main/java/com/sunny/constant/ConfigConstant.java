package com.sunny.constant;

public enum ConfigConstant {

    UPLOAD_EXCEL_PATH("UPLOAD_EXCEL_PATH");

    private final String value;

    ConfigConstant(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}