package com.gsardina.lastwin.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseModel<T> {

    private T data;
    private String message;

    public ResponseModel(String message) {
        this.message = message;
    }
}
