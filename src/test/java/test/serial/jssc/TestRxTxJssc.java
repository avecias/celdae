/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test.serial.jssc;

import com.avecias.celdae.controller.DataController;
import com.avecias.celdae.model.cnn.ConexionSerial;
import com.avecias.celdae.model.cnn.ConexionSerialImple;
import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.SerialPortException;

/**
 *
 * @author avecias
 */
public class TestRxTxJssc {

    public static void main(String[] args) {
        DataController dataController = new DataController();
        ConexionSerial cnn = new ConexionSerialImple(dataController);
//        ConexionSerial cnn = new ConexionSerialImple();
        try {
            cnn.abrir("COM3");
            while (true) {
                Thread.sleep(2000);
                String msj = cnn.leerMensaje();
                System.out.print(msj);
            }
        } catch (SerialPortException ex) {
            Logger.getLogger(TestRxTxJssc.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(TestRxTxJssc.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
