/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test.serial.jssc;

import jssc.SerialPortList;

/**
 *
 * @author avecias
 */
public class TestGetPortsJssc {
    
    public static void main(String[] args) {
        String[] portNames = SerialPortList.getPortNames();
        for (String portName : portNames) {
            System.out.println(portName);
        }
    }
    
}
