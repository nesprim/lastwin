package com.gsardina.lastwin.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseModel<T> {

    private T data;
    private String esito;
    private String message;

    public ResponseModel(String esito, String message) {
        this.esito = esito;
        this.message = message;
    }
}
