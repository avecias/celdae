/*
Analizador de tramas para validar flujo de cadenas entrantes
 */
package com.avecias.celdae.model.util;

import com.avecias.celdae.model.dto.Data;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Analizador {

    private final String EXPRESION_REGULAR = "^a1?\\d{1,3}b1?\\d{1,3}c1?\\d{1,3}";

    public Data convertir(String linea) {
        if (!isTrama(linea)) {
            System.out.println(linea);
            return new Data(-1, -1, -1);
        }
        return seccionar(linea);
    }

    private boolean isTrama(String linea) {
        Pattern pat = Pattern.compile(EXPRESION_REGULAR);
        Matcher mat = pat.matcher(linea);
        return mat.matches();
    }

    private Data seccionar(String linea) {
        Data data = new Data();
        String a = "", b = a, c = a;
        int i = 0;
        while (i < linea.length()) {
            switch (linea.charAt(i)) {
                case 'a':
                    //Grabar a
                    i++;
                    while (isNumero(linea.charAt(i))) {
                        a += linea.charAt(i);
                        i++;
                    }
                    break;
                case 'b':
                    //Grabar a
                    i++;
                    while (isNumero(linea.charAt(i))) {
                        b += linea.charAt(i);
                        i++;
                    }
                    break;
                case 'c':
                    //Grabar a
                    i++;
                    while (i < linea.length()) {
                        c += linea.charAt(i);
                        i++;
                    }
                    break;
                default:
                    break;
            }
        }
        data.setValue1(Integer.valueOf(a));
        data.setValue2(Integer.valueOf(b));
        data.setValue3(Integer.valueOf(c));
        return data;
    }

    private boolean isNumero(char c) {
        return c >= 48 && c <= 57;
    }

}
