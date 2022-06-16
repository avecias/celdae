package com.avecias.celdae.model.cnn;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortList;

public class ConexionSerialImple implements ConexionSerial, SerialPortEventListener {

    private SerialPort serialPort;
    private String mensaje;
    private String mensajeAux;

    public ConexionSerialImple() {
    }


    @Override
    public void abrir(String port) throws SerialPortException {
        //Hacer conexion al puerto COM
        mensajeAux = "";
        serialPort = new SerialPort(port);
        serialPort.openPort();
        serialPort.setParams(9600, 0, 1, 1);
        serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN | SerialPort.FLOWCONTROL_RTSCTS_OUT);
        serialPort.addEventListener(this, SerialPort.MASK_RXCHAR);
    }

    @Override
    public void cerrar() throws SerialPortException {
        serialPort.closePort();
    }

    @Override
    public void escribirMensaje(String mensaje) throws SerialPortException {
        serialPort.writeBytes(mensaje.getBytes());
    }

    @Override
    public String leerMensaje() throws SerialPortException {
        mensajeAux = mensaje;
        mensaje = "";
        return mensajeAux;
    }

    @Override
    public boolean estaAbierto() {
        return serialPort != null;
    }

    public SerialPort getSerialPort() {
        return serialPort;
    }

    public void setSerialPort(SerialPort serialPort) {
        this.serialPort = serialPort;
    }

    public static Object[] puertosDisponibles() {
        return SerialPortList.getPortNames();
    }

    @Override
    public void serialEvent(SerialPortEvent event) {
        if (event.isRXCHAR()) {
            if (event.getEventValue() > 0) {
                try {
                    String receivedData = serialPort.readString(event.getEventValue());
                    mensajeAux += receivedData;
                    if (receivedData.contains("\n")) {
                        mensaje = mensajeAux;
                        mensajeAux = "";
                        System.out.println(mensaje);
                    }
                } catch (SerialPortException ex) {
                    System.out.println("Error in receiving string from COM-port: " + ex);
                }
            }
        }
    }

}