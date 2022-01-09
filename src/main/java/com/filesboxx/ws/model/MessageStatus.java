package com.filesboxx.ws.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MessageStatus {
    RECEIVED("RECEIVED"),
    DELIVERED("DELIVERED");

    private String value;

    MessageStatus(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static MessageStatus fromValue(String value){
        for (MessageStatus m : MessageStatus.values()) {
            if (m.value.equals(value)){
                return m;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}
