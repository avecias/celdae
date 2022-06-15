/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test.serial.fazecast;

import com.fazecast.jSerialComm.SerialPort;

/**
 *
 * @author avecias
 */
public class TestGetPortsFacecast {

    public static void main(String[] args) {
        System.out.println("Hello world");
        SerialPort[] serialPorts = SerialPort.getCommPorts();
        for (SerialPort serialPort : serialPorts) {
            System.out.println(serialPort.getSystemPortName());
        }
    }

}
