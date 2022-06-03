// Regulador de voltaje por arduino

//sensibilidad en Voltios/Amperio para sensor de 5A
float sensibilidad=0.185;
const int pinPwm = 3; 

void setup() {
  // inicio de la configuracion
  Serial.begin(9600);
}

void loop() {
  // put your main code here, to run repeatedly:
  int corriente = analogRead(A0);
  int outputValue = map(corriente, 0, 1023, 0, 255);
  // salida de  voltaje
  analogWrite(pinPwm, outputValue);
  Serial.println(corriente);
}
