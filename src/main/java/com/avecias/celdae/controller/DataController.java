/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.avecias.celdae.controller;

import com.avecias.celdae.model.cnn.ConexionSerialImple;
import com.avecias.celdae.model.dto.Data;
import com.avecias.celdae.model.dto.Result;
import com.avecias.celdae.model.dto.ResultData;
import com.avecias.celdae.model.dto.ResultPort;
import com.avecias.celdae.model.util.Analizador;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import jssc.SerialPortException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author avecias
 */
@RestController
@RequestMapping(value = "/data")
public class DataController {

    private final Random random = new Random();
    private final double RANGE_MIN = 0.0;
    private final double RANGER_MAX = 1500;
    private final int OK = 200;
    private final int NULL = 000;
    private final int ERROR = 500;
    //
    private final double VOLTAJE = 2.5;
    //
    private final Analizador analizador = new Analizador();
    private final ConexionSerialImple cnn = new ConexionSerialImple();

    @RequestMapping(value = "/getById/{idData}", method = RequestMethod.GET)
    public Data getById(@PathVariable int idData) {
        Data d = new Data();
        d.setIdData(idData);
        return d;
    }

    @RequestMapping(value = "/random/", method = RequestMethod.GET)
    public ResultData random() {
        Data d = new Data();
        ResultData result = new ResultData(d, 000, "nullo");
        double v = RANGE_MIN + (RANGER_MAX - RANGE_MIN) * random.nextDouble();
        d.setValue1(v);
        result.setData(d);
        result.setStatus(200);
        result.setMessage("Numero aleatorio: " + v);
        return result;
    }

    @RequestMapping(value = "/rx/", method = RequestMethod.GET)
    public ResultData rx() {
        String message = "";
        Data d = new Data();
        ResultData result = new ResultData(d, NULL, "nullo");
        String msj = cnn.leerMensaje();
        if (msj != null && msj.contains("\n")) {
            d = analizador.convertir(msj.substring(0, msj.length() - 2));
            // convertir
            double vi1 = d.getValue1() * 5.0 / 1023.0; // convertir bits en nivel de voltaje1
            double vi2 = d.getValue2() * 5.0 / 1023.0; // convertir bits en nivel de voltaje1
            double vi3 = d.getValue3() * 5.0 / 1023.0; // convertir bits en nivel de voltaje1
            double i = ((vi1 - VOLTAJE) / 0.185) * 1000.00; // colocar en milivolts
            d.setValue1(i);
            // value 2
            d.setValue2(vi2);
            // value 3
            d.setValue3(vi3);
            if (i < 600) {
                message = "mensaje 1, voltaje = " + vi1;
            } else if (i == 600 && i < 800) {
                message = "mensaje 2, voltaje = " + vi1;
            } else if (i > 800) {
                message = "mensaje 3, voltaje = " + vi1;
            }
        }
        result.setData(d);
        result.setStatus(OK);
        result.setMessage(message);
        return result;
    }

    @RequestMapping(value = "/puertosDisponibles/", method = RequestMethod.GET)
    public List<ResultPort> puertosDisponibles() {
        List<ResultPort> ports = new ArrayList<>();
        Object[] puertosDisponibles = ConexionSerialImple.puertosDisponibles();
        for (Object puertosDisponible : puertosDisponibles) {
            ResultPort result = new ResultPort("" + puertosDisponible, NULL, "nullo");
            ports.add(result);
        }
        return ports;
    }

    @RequestMapping(value = "/abrir/{port}", method = RequestMethod.GET)
    public Result abrir(@PathVariable String port) {
        Result result = new Result(NULL, "puerto aun no abierto");
        try {
            cnn.abrir(port);
            result.setStatus(OK);
            result.setMessage("Puerto " + port + " abierto con exito.");
        } catch (SerialPortException ex) {
            result.setStatus(ERROR);
            result.setMessage("Error al intentar abrir el puerto " + port + "." + ex.toString());
        }
        return result;
    }

}
