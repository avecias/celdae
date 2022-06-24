/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avecias.celdae.model.dto;

/**
 *
 * @author aveci
 */
public class ResultPort extends Result {

    private String port;

    public ResultPort() {
    }

    public ResultPort(String port, Integer status, String message) {
        super(status, message);
        this.port = port;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

}
