/***************************************************************************
  This is a library for the BMP280 humidity, temperature & pressure sensor

  Designed specifically to work with the Adafruit BMEP280 Breakout
  ----> http://www.adafruit.com/products/2651

  These sensors use I2C or SPI to communicate, 2 or 4 pins are required
  to interface.

  Adafruit invests time and resources providing this open source code,
  please support Adafruit andopen-source hardware by purchasing products
  from Adafruit!

  Written by Limor Fried & Kevin Townsend for Adafruit Industries.
  BSD license, all text above must be included in any redistribution
 ***************************************************************************/

#include <Wire.h>
#include <SPI.h>
#include <Adafruit_Sensor.h>
#include <Adafruit_BMP280.h>

#include <OneWire.h>
// Sensor librarie
#include <DallasTemperature.h>

#define BMP_SCK 13
#define BMP_MISO 12
#define BMP_MOSI 11
#define BMP_CS 10

// Bus One Wire Bus on the arduino pin 2
OneWire oneWire(2);
DallasTemperature sensors(&oneWire);
DeviceAddress sensorDeviceAddress;
const int debit = 9600;

Adafruit_BMP280 bme; // I2C
//Adafruit_BMP280 bme(BMP_CS); // hardware SPI
//Adafruit_BMP280 bme(BMP_CS, BMP_MOSI, BMP_MISO,  BMP_SCK);

void setup() {
  Serial.begin(9600);
  // Serial.println(F("BMP280 test"));

  if (!bme.begin()) {
    Serial.println("Could not find a valid BMP280 sensor, check wiring!");
    while (1);
  }

  //Sensor activation
  sensors.begin();
  sensors.getAddress(sensorDeviceAddress, 0);
  // Resolutions: 9,10,11 or 12
  sensors.setResolution(sensorDeviceAddress, 12);
}

void loop() {
  Serial.print("*at*");
  Serial.print(bme.readTemperature());
  Serial.println();

  Serial.print("*ap*");
  Serial.print(bme.readPressure());
  Serial.println();

  // Serial.print("Approx altitude = ");
  // Serial.print(bme.readAltitude(1017.70)); // this should be adjusted to your local forcase
  // Serial.println(" m");

  sensors.requestTemperatures();
  // Sensor number 0
  Serial.print("*wt*");
  Serial.println(sensors.getTempCByIndex(0));
  Serial.println();

  delay(5000);
}
