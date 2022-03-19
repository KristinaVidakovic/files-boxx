package com.filesboxx.ws.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageStatus {
    RECEIVED("RECEIVED"),
    DELIVERED("DELIVERED");

    private final String value;

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
