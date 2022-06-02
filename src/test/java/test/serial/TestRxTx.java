/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test.serial;

import com.fazecast.jSerialComm.SerialPort;

/**
 *
 * @author avecias
 */
public class TestRxTx {

    public static void main(String[] args) {
        System.out.println("Hello world");
        SerialPort[] serialPorts = SerialPort.getCommPorts();
        for (SerialPort serialPort : serialPorts) {
            System.out.println(serialPort.getSystemPortName());
        }
        SerialPort serialPort = SerialPort.getCommPort("");
        serialPort.openPort();
        serialPort.setParity(SerialPort.NO_PARITY);
        serialPort.setNumStopBits(SerialPort.ONE_STOP_BIT);
        serialPort.setNumDataBits(8);
//        serialPort.addDataListener(this);
        serialPort.setBaudRate(9600);
    }

}
