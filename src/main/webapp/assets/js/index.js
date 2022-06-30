/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

const OK = 200;

var ports = {COM3: "COM3", COM4: "COM4", COM5: "COM5"};

var xValores = [];
var yValores = [];
var yValores2 = [];
var yValores3 = [];
//
for (var i = 0; i < 31; i++) {
    xValores.push(i);
}

function iniciarComunicacion() {
    Swal.fire({
        title: 'Seleccione un puerto',
        input: 'select',
        inputOptions: ports,
        inputPlaceholder: 'ejem. COM2',
        showCancelButton: true,
        inputValidator: (value) => {
            return new Promise((resolve) => {
                var r = abrirPuerto(value);
                console.log("puerto " + value);
                if (r !== null) {
                    if (r.status === OK) {
                        console.log("abrir el puerto");
                        resolve();
                        $("#btnIniciar").html("");
                        $("#canvasZone").html("<canvas id='myChart' style='width:100%;max-width:800px'></canvas>");
                        iniciarGraficar();
                    } else {
                        resolve(r.message);
                    }
                }else{
                    resolve("Error en la conexion");
                }
            });
        }
    });

}

function iniciarGraficar() {
    var cont = 0;
    setInterval(function () {
        if (cont < xValores.length) {
            $.ajax({
                async: false,
                type: "GET",
                url: "rest/data/rx/"
            }).done(function (result) {
                if (result.status === OK) {
                    dibujarGrafica(result.data.value1, cont);
                    dibujarGrafica2(result.data.value2, cont);
                    dibujarGrafica3(result.data.value3, cont);
                } else {
                    Swal.fire("Error del servidor", result.message, "error");
                }
            }).fail(function () {
                Swal.fire("Error del servidor", "Sucedió un error en el servidor, refresque la página o consulte al personal de sistemas", "error");
            });
            cont++;
        }
    }, 60 * 1000);
}

// dibujar el resultado 1
function dibujarGrafica(value, index) {
    yValores.push(value);
    console.log('xValues ' + xValores[index] + ' yValues ' + value);
    $("#myChart").empty();
    new Chart("myChart", {
        type: "line",
        data: {
            labels: xValores,
            datasets: [{
                    fill: false,
                    lineTension: 0,
                    backgroundColor: "rgba(0,0,255,1.0)",
                    borderColor: "rgba(0,0,255,0.1)",
                    data: yValores
                }]
        },
        options: {
            legend: {display: false},
            scales: {
                yAxes: [{ticks: {min: 0, max: 1500}}]
            }
        }
    });
}

// dibujar el resultado 2
function dibujarGrafica2(value, index) {
    yValores2.push(value);
    console.log('xValues ' + xValores[index] + ' yValues2 ' + value);
    $("#myChart2").empty();
    new Chart("myChart2", {
        type: "line",
        data: {
            labels: xValores,
            datasets: [{
                    fill: false,
                    lineTension: 0,
                    backgroundColor: "rgba(0,0,255,1.0)",
                    borderColor: "rgba(0,0,255,0.1)",
                    data: yValores2
                }]
        },
        options: {
            legend: {display: false},
            scales: {
                yAxes: [{ticks: {min: 0, max: 5}}]
            }
        }
    });
}

// dibujar el resultado 3
function dibujarGrafica3(value, index) {
    yValores3.push(value);
    console.log('xValues ' + xValores[index] + ' yValues3 ' + value);
    $("#myChart3").empty();
    new Chart("myChart3", {
        type: "line",
        data: {
            labels: xValores,
            datasets: [{
                    fill: false,
                    lineTension: 0,
                    backgroundColor: "rgba(0,0,255,1.0)",
                    borderColor: "rgba(0,0,255,0.1)",
                    data: yValores3
                }]
        },
        options: {
            legend: {display: false},
            scales: {
                yAxes: [{ticks: {min: 0, max: 5}}]
            }
        }
    });
}

function abrirPuerto(port) {
    // peticion para abrir el puerto
    var r;
    $.ajax({
        async: false,
        type: "GET",
        url: "rest/data/abrir/" + port
    }).done(function (result) {
        r = result;
    }).fail(function () {
        r = null;
    });
    return r;
}

$(document).ready({

});