'use strict';

const arduinoSerialPort = require('./arduinoSerialPort');
const EventBus = require('vertx3-eventbus-client');

let dataSensor;
let waterTemperature;
let airTemperature;

const airTemperatureAddress = 'air.temperature.raw.address';
const waterTemperatureAddress = 'water.temperature.raw.address';
const eventBusUrl = 'http://localhost:8082/eventbus';

const waterSensorEnvironment = 'WATER';
const airSensorEnvironment = 'AIR';

const temperatureSensorType = 'TEMPERATURE';

let options = {
    // Max reconnect attempts
    vertxbus_reconnect_attempts_max: Infinity,
    // Initial delay (in ms) before first reconnect attempt
    vertxbus_reconnect_delay_min: 1000,
    // Max delay (in ms) between reconnect attempts
    vertxbus_reconnect_delay_max: 5000,
    // Exponential backoff factor
    vertxbus_reconnect_exponent: 2,
    // Randomization factor between 0 and 1
    vertxbus_randomization_factor: 0.5
};
const eventBus = new EventBus(eventBusUrl, options);
eventBus.enableReconnect(true);

eventBus.onclose = (param) => {
    console.log('Event bus connection closed', param)
};

eventBus.onopen = async () => {
    console.log('Event bus connection is open');
    const serialport = await arduinoSerialPort.initializeSerialPort();

    serialport.on('data', function (data) {
        // Identify the type of sensor
        if (data && data.length > 4) {
            dataSensor = String(data).trim();
            let sensorType = dataSensor.substring(0, 4);
            let today = new Date().toISOString();

            switch (sensorType) {
                case '*wt*':
                    waterTemperature = dataSensor.substring(4, dataSensor.length);
                    sendValueToEventBus(waterTemperature, today, waterTemperatureAddress,
                        waterSensorEnvironment, temperatureSensorType);
                    break;
                case '*at*':
                    airTemperature = dataSensor.substring(4, dataSensor.length);
                    sendValueToEventBus(airTemperature, today, airTemperatureAddress,
                        airSensorEnvironment, temperatureSensorType);
                    break;
                case '*ap*':
                    break;
                case '*ah*':
                    break;
                default:
                    console.log('Error, unknown sensor type : ' + sensorType);
            }
        }
    });
};

function sendValueToEventBus(value, date, address, sensorEnvironment, sensorType) {
    eventBus.publish(address, '{"date":"' + date +
        '" ,"value":' + value +
        ' ,"sensorEnvironment":' + '"' + sensorEnvironment + '"' +
        ' ,"sensorType":' + '"' + sensorType + '"' + '}');

    console.log('sendValueToEventBus - value : ' + value +
        ' - sensor type : ' + sensorType +
        ' - address : ' + address);
}