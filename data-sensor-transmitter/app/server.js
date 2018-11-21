const serialport = require('./serialPort');
const EventBus = require('vertx3-eventbus-client');

let sensorType;
let dataSensor;
let waterTemperature;
let airTemperature;
let airPressure;
let airHumidity;

const waterTemperatureAddress = 'water.temperature.address';
const airTemperatureAddress = 'air.temperature.address';
const eventBus = new EventBus('http://localhost:8080/eventbus');

eventBus.onclose = (param) => {
    console.log('closed event bus', param)
}

eventBus.onopen = () => {
    console.log('open event bus');
    serialport.on('data', function (data) {

        // Identify the type of sensor
        if (data && data.length > 4) {
            dataSensor = data.trim();
            sensorType = dataSensor.substring(0, 4);
            let today = new Date().toISOString();

            switch (sensorType) {
                case '*wt*':
                    waterTemperature = dataSensor.substring(4, dataSensor.length);
                    sendValueToEventBus(waterTemperature, today, waterTemperatureAddress);
                    break;
                case '*at*':
                    airTemperature = dataSensor.substring(4, dataSensor.length);
                    sendValueToEventBus(airTemperature, today, airTemperatureAddress);
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
}

function sendValueToEventBus(value, date, address) {
    eventBus.publish(address, '{"value":' + value + ', "date":"' + date + '"}');
    // console.log('sendValueToEventBus - value : ' + value + ' address : ' + address);
}

serialport.on('close', function (err) {
    console.log('serial port closed', err);
});

serialport.on('open', function () {
    console.log('serial port open');
});
