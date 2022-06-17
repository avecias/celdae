/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.avecias.celdae.controller;

import com.avecias.celdae.model.dto.Data;
import com.avecias.celdae.model.dto.ResultData;
import java.util.Random;
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
    private final double rangeMin = 0;
    private final double rangeMax = 5;

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
        double v = rangeMin + (rangeMax - rangeMin) * random.nextDouble();
        d.setValue(v);
        result.setData(d);
        result.setStatus(200);
        result.setMessage("Numero aleatorio: " + v);
        return result;
    }

}
