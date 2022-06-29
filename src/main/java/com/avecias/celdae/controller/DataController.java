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
    private final double RANGE_MIN = 0.5;
    private final double RANGER_MAX = 0.8;
    private final int OK = 200;
    private final int NULL = 000;
    private final int ERROR = 500;
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
        Data d = new Data();
        ResultData result = new ResultData(d, NULL, "nullo");
        String msj = cnn.leerMensaje();
        if (msj != null && msj.contains("\n")) {
//                    System.out.println(mensaje.substring(0, mensaje.length() - 2));
            d = analizador.convertir(msj.substring(0, msj.length() - 2));
        }
        result.setData(d);
        result.setStatus(OK);
        result.setMessage("Numero aleatorio: " + 0.7);
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
