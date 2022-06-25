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
public class ResultData extends Result {

    private Data data;

    public ResultData() {
    }

    public ResultData(Data data, Integer status, String message) {
        super(status, message);
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}
