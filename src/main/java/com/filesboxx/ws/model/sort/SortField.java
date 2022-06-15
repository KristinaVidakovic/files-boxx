package com.filesboxx.ws.model.sort;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SortField {

    DATE("DATE"),
    NAME("NAME");

    private final String value;

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static SortField fromValue(String value){
        for (SortField m : SortField.values()) {
            if (m.value.equals(value)){
                return m;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}
