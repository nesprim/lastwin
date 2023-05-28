package com.gsardina.lastwin.model;

import lombok.Data;

@Data
public class ConfirmEmailModel {

    private String username;
    private String confirmCode;
}
