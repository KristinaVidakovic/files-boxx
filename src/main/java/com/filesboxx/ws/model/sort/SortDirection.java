package com.filesboxx.ws.model.sort;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SortDirection {

    ASC("ASC"),
    DESC("DESC");

    private final String value;

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static SortDirection fromValue(String value){
        for (SortDirection m : SortDirection.values()) {
            if (m.value.equals(value)){
                return m;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}
