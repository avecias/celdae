/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

const OK = 200;

var ports = {COM3: "COM3", COM4: "COM4", COM5: "COM5"};

var xValores = [];
var yValores = [];
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
                console.log("resolve " + resolve);
                console.log("value " + value);
                resolve();
                $("#btnIniciar").html("");
                $("#canvasZone").html("<canvas id='myChart' style='width:100%;max-width:800px'></canvas>");
                iniciarGraficar();
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
                url: "rest/data/random/"
            }).done(function (result) {
                if (result.status === OK) {
                    dibujarGrafica(result.data.value1, cont);
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

// dibujar el resultado
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
                yAxes: [{ticks: {min: 0, max: 1}}]
            }
        }
    });
}

$(document).ready({

});