package com.avecias.celdae.model.cnn;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortList;

public class ConexionSerialImple implements ConexionSerial, SerialPortEventListener {

    private SerialPort serialPort;
    private String mensaje;

    public ConexionSerialImple() {
    }

    @Override
    public void abrir(String port) throws SerialPortException {
        //Hacer conexion al puerto COM
        mensaje = "";
        serialPort = new SerialPort(port);
        serialPort.openPort();
        serialPort.setParams(9600, 8, 1, 0); // 9600 baudios, 8 bits, bit de paro y bit de paridad
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
    public String leerMensaje() {
        return mensaje;
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

    public Object[] puertosDisponibles() {
        return SerialPortList.getPortNames();
    }

    @Override
    public void serialEvent(SerialPortEvent event) {
        if (event.isRXCHAR()) {
            if (event.getEventValue() > 0) {
                try {
                    mensaje = serialPort.readString();
                } catch (SerialPortException ex) {
                    System.err.println("Error en lectura serial " + ex.toString());
                    mensaje = null;
                } catch (NullPointerException ex) {
                    System.err.println("Error en lectura serial nulla " + ex.toString());
                    mensaje = null;
                }
            }
        }
    }

}
