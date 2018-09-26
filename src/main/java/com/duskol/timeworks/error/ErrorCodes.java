package com.duskol.timeworks.error;

public enum ErrorCodes {

    PROJECT_CAN_NOT_BE_FOUND(0X001),
    DEVELOPER_CAN_NOT_BE_ADDED(0X002),
    INTERNAL_ERROR(0x003);

    private final long code;

    ErrorCodes(long code) {
        this.code = code;
    }

    public long getCode() {
        return code;
    }
}
