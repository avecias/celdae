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
public class Result {

    private Integer status;
    private String message;

    public Result() {
    }

    public Result(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        String m = "sin mensaje";
        if (message == null) {
            m = "";
        } else {
            if (message.length() > 60) {
                m = message.substring(0, 60) + "...";
            }
        }
        return "Estatus = " + status + ", mensaje = " + m;
    }

}
