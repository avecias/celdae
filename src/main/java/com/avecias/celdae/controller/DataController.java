/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.avecias.celdae.controller;

import com.avecias.celdae.model.dto.Data;
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

    @RequestMapping(value = "/getById/{idData}", method = RequestMethod.GET)
    public Data getById(@PathVariable int idData) {
        Data d = new Data();
        d.setIdData(idData);
        return d;
    }

}
