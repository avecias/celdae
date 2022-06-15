/*
Interfaz de conexion serial
 */
package com.avecias.celdae.model.cnn;

import jssc.SerialPortException;

public interface ConexionSerial {


    public void cerrar() throws SerialPortException;

    public void escribirMensaje(String mensaje) throws SerialPortException;

    public String leerMensaje() throws SerialPortException;

    public boolean estaAbierto();

    public void abrir(String port) throws SerialPortException;

}
