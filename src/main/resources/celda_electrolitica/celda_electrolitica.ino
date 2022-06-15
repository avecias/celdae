// Regulador de voltaje por arduino
int const SENSOR_A = A0;
int const SENSOR_B = A1;
int const SENSOR_C = A3;

//sensibilidad en Voltios/Amperio para sensor de 5A
float const SENSIBILIDAD = 0.185;
int const PIN_PWM = 3;

void setup() {
  // inicio de la configuracion
  Serial.begin(9600); // 9600 baudios comunicacion serial
}

void loop() {
  // put your main code here, to run repeatedly:
  int corriente = analogRead(SENSOR_A);
  int outputValue = map(corriente, 0, 1023, 0, 255);
  // salida de  voltaje
  analogWrite(PIN_PWM, outputValue);
  Serial.println(corriente);
}

// metodo para leer los tres sensores
String leerSensores(){
  return "";
}
