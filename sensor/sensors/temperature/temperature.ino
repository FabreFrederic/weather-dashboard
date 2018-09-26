#include <OneWire.h>
// Sensor librarie
#include <DallasTemperature.h>

// Bus One Wire Bus on the arduino pin 2
OneWire oneWire(2); 
DallasTemperature sensors(&oneWire);
DeviceAddress sensorDeviceAddress;
const int debit = 9600;

void setup() {
 Serial.begin(debit);
 //Sensor activation
 sensors.begin(); 
 sensors.getAddress(sensorDeviceAddress, 0);
 // Resolutions: 9,10,11 or 12
 sensors.setResolution(sensorDeviceAddress, 12); 
}

void loop() {
  sensors.requestTemperatures();
  // Sensor number 0
  Serial.print("*wt*");
  Serial.println(sensors.getTempCByIndex(0));
  Serial.println();

 delay(100);
}
