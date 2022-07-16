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
import com.opencsv.CSVWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jssc.SerialPortException;
import org.springframework.util.StreamUtils;
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
    // resultados
    private List<Data> datas;

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
                message = "mensaje 2, voltaje = " + vi2;
            } else if (i > 800) {
                message = "mensaje 3, voltaje = " + vi3;
            }
        }
        // guardar resultado
        if (datas != null) {
            datas.add(d);
        }
        result.setData(d);
        result.setStatus(OK);
        result.setMessage(message);
        return result;
    }

    @RequestMapping(value = "/estaAbierto/", method = RequestMethod.GET)
    public Result estaAbierto() {
        Result result = new Result(NULL, "puerto aun no abierto");
        if (cnn.estaAbierto()) {
            result.setStatus(OK);
            result.setMessage("Conexion abierta.");
        } else {
            result.setStatus(NULL);
            result.setMessage("Conexion cerrada.");
        }
        return result;
    }

    @RequestMapping(value = "/puertosDisponibles/", method = RequestMethod.GET)
    public List<ResultPort> puertosDisponibles() {
        List<ResultPort> ports = new ArrayList<>();
        Object[] puertosDisponibles = cnn.puertosDisponibles();
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
            datas = new ArrayList<>();
            result.setStatus(OK);
            result.setMessage("Puerto " + port + " abierto con exito.");
        } catch (SerialPortException ex) {
            result.setStatus(ERROR);
            result.setMessage("Error al intentar abrir el puerto " + port + "." + ex.toString());
        }
        return result;
    }

    @RequestMapping(value = "/cerrarLimpiar/", method = RequestMethod.GET)
    public Result cerrarLimpiar() {
        Result result = new Result(NULL, "puerto aun no abierto");
        try {
            if (cnn.estaAbierto()) {
                cnn.cerrar();
            }
            datas = new ArrayList<>();
            result.setStatus(OK);
            result.setMessage("Puerto cerrado con exito.");
        } catch (SerialPortException ex) {
            result.setStatus(ERROR);
            result.setMessage("Error al intentar cerrar el puerto " + ex.toString());
        }
        return result;
    }

    @RequestMapping(value = "/descargarExcel/", method = RequestMethod.GET)
    public void cierreMes(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<String[]> datos = new ArrayList();
            for (Data data : datas) {
                datos.add(new String[]{"" + data.getValue1(), "" + data.getValue2(), "" + data.getValue3()});
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(baos);
            CSVWriter csvw = new CSVWriter(osw);
            csvw.writeAll(datos);
            csvw.close();
            //
            InputStream inputStream = new ByteArrayInputStream(baos.toByteArray());
            // modifies response
            response.setContentType("application/octet-stream");
            response.setContentLength(baos.size());
            baos.close();
            // forces download
            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"", "Reporte.csv");
            response.setHeader(headerKey, headerValue);
            // 
            StreamUtils.copy(inputStream, response.getOutputStream());
            StreamUtils.drain(inputStream);
        } catch (IOException ex) {
            System.err.println("Error en I/O" + ex);
        } catch (Exception ex) {
            System.err.println("Error" + ex);
        }
    }

}
