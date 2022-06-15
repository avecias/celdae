package com.avecias.celdae.model.cnn;

import com.fazecast.jSerialComm.SerialPort;

public class ConexionSerialFazecast {

    private SerialPort serialPort;
    private String port;
    private String mensaje;
    private String mensajeAux;

    public ConexionSerialFazecast() {
        this.mensaje = "";
        this.mensajeAux = "";
    }

    public ConexionSerialFazecast(String port) {
        this.port = port;
        this.mensaje = "";
        this.mensajeAux = "";
    }
    
    public void abrir(String port) {
        //Hacer conexion al puerto COM
        mensajeAux = "";
//        serialPort = new SerialPort(parametros.getPuerto());
//        serialPort.openPort();
//        serialPort.setParams(parametros.getBaudios(), parametros.getDato(), parametros.getParo(), parametros.getParidad());
//        serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN | SerialPort.FLOWCONTROL_RTSCTS_OUT);
//        serialPort.addEventListener(muestreo, SerialPort.MASK_RXCHAR);
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void cerrar() {
        serialPort.closePort();
    }

    public void escribirMensaje(String mensaje) {
        byte[] buffer = null;
        serialPort.readBytes(buffer, 0);
    }

    public String leerMensaje() {
        mensajeAux = mensaje;
        mensaje = "";
        return mensajeAux;
    }

    public boolean estaAbierto() {
        return serialPort != null;
    }

    public SerialPort getSerialPort() {
        return serialPort;
    }
}
