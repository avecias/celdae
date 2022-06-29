/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test.serial.jssc;

import com.avecias.celdae.model.cnn.ConexionSerialImple;
import com.avecias.celdae.model.dto.Data;
import com.avecias.celdae.model.util.Analizador;
import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.SerialPortException;

/**
 *
 * @author avecias
 */
public class TestRxTxJssc {

    public static void main(String[] args) {
        ConexionSerialImple cnn = new ConexionSerialImple();
        Analizador analizador = new Analizador();
        try {
            cnn.abrir("COM3");
            while (true) {
                Thread.sleep(2000);
                String msj = cnn.leerMensaje();
                System.out.print("lectura supervisada " + msj);
                if (msj != null && msj.contains("\n")) {
//                    System.out.println(mensaje.substring(0, mensaje.length() - 2));
                    Data d = analizador.convertir(msj.substring(0, msj.length() - 2));
                    System.out.println(d);
                }
            }
        } catch (SerialPortException ex) {
            Logger.getLogger(TestRxTxJssc.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(TestRxTxJssc.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
