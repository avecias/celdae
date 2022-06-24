package com.avecias.celdae.model.cnn;

import com.avecias.celdae.controller.DataController;
import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;

public class ConexionSerialImple implements ConexionSerial {

    private SerialPort serialPort;
    private String mensaje;
    private String mensajeAux;
    private DataController controller;

    public ConexionSerialImple(DataController controller) {
        this.controller = controller;
    }

    @Override
    public void abrir(String port) throws SerialPortException {
        //Hacer conexion al puerto COM
        mensajeAux = "";
        serialPort = new SerialPort(port);
        serialPort.openPort();
        serialPort.setParams(9600, 8, 1, 0);
        serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN | SerialPort.FLOWCONTROL_RTSCTS_OUT);
        serialPort.addEventListener(controller, SerialPort.MASK_RXCHAR);
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

}
