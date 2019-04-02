#include <OneWire.h>
// Sensor librarie
#include <DallasTemperature.h>

// Bus One Wire Bus on the arduino pin 2
OneWire oneWire(2); 
DallasTemperature sensors(&oneWire);

const int debit = 9600;
//int devicesFound = 0;
int sensorResolution = 12;
String value;

void setup() {
 Serial.begin(debit);
 sensors.begin();

// devicesFound = sensors.getDeviceCount();
// Serial.print(F("Number of sensors : "));
// Serial.println(devicesFound);

 // Resolutions: 9,10,11 or 12
 sensors.setResolution(sensorResolution);
}

void loop() {
  sensors.requestTemperatures();

  value = sensors.getTempCByIndex(0);
  Serial.println("*wt*" + value);

  value = sensors.getTempCByIndex(1);
  Serial.println("*at*" + value);

  delay(2000);
}

