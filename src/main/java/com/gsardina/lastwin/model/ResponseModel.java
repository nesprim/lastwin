package com.gsardina.lastwin.model;

import lombok.Data;

@Data
public class ResponseModel<T> {

    private String esito;
    private String messaggio;
    private T data;
}
