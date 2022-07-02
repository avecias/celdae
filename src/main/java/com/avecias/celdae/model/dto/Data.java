/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.avecias.celdae.model.dto;

import java.io.Serializable;

/**
 *
 * @author avecias
 */
public class Data implements Serializable {

    private int idData;
    private double value1;
    private double value2;
    private double value3;

    public Data() {
    }

    public Data(double value1, double value2, double value3) {
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
    }

    public int getIdData() {
        return idData;
    }

    public void setIdData(int idData) {
        this.idData = idData;
    }

    public double getValue1() {
        return value1;
    }

    public void setValue1(double value1) {
        this.value1 = value1;
    }

    public double getValue2() {
        return value2;
    }

    public void setValue2(double value2) {
        this.value2 = value2;
    }

    public double getValue3() {
        return value3;
    }

    public void setValue3(double value3) {
        this.value3 = value3;
    }

    @Override
    public String toString() {
        return "Data{" + "idData=" + idData + ", value1=" + value1 + ", value2=" + value2 + ", value3=" + value3 + '}';
    }

    
}
