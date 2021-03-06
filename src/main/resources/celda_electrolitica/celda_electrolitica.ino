// Regulador de voltaje por arduino
int const SENSOR_A = A0;
int const SENSOR_B = A1;
int const SENSOR_C = A2;
// pines altos y bajos

//sensibilidad en Voltios/Amperio para sensor de 5A
float const SENSIBILIDAD = 0.185;
int const PIN_PWM = 3;

void setup() {
  // inicio de la configuracion
  Serial.begin(9600); // 9600 baudios comunicacion serial
}

void loop() {
  // put your main code here, to run repeatedly:
  int rv = analogRead(SENSOR_C); // potenciometro como regulador de voltaje
  int voltajeSalida = map(rv, 0, 1023, 0, 255);
  // salida de  voltaje
  analogWrite(PIN_PWM, voltajeSalida);
  // serializar sensores
  Serial.println(leerSensores());
  delay(100);
}

// metodo para leer los tres sensores
String leerSensores(){
  String valorA = "a" + String(analogRead(SENSOR_A));
  delay(10);
  String valorB = "b" + String(analogRead(SENSOR_B));
  delay(10);
  String valorC = "c" + String(analogRead(SENSOR_C));
  delay(10);
  return valorA + valorB + valorC;
}
