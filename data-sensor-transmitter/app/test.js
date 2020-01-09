const EventBus = require('vertx3-eventbus-client');
const cron = require("node-cron");
const getmac = require('getmac').default;
const axios = require('axios');

const airTemperatureAddress = 'air.temperature.raw.address';
const waterTemperatureAddress = 'water.temperature.raw.address';
const eventBusUrl = 'http://localhost:8082/eventbus';
const macAddress = getmac();

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

axios.post('http://localhost:9090/sensor', {
    location: 'Berlin',
    name: macAddress
}).then(function (response) {
    console.log('The sensor has been registered');
    onOpenEventBus();
}).catch(function (error) {
    console.log('error : ', error);
});

const eventBus = new EventBus(eventBusUrl, options);
eventBus.enableReconnect(true);

function onOpenEventBus() {
    eventBus.onopen = () => {
        console.log('open event bus');

        cron.schedule("*/5 * * * * *", function () {
            let waterValue = Math.floor(Math.random() * 9) + '.' + Math.floor(Math.random() * 9);
            let airValue = Math.floor(Math.random() * 6) + 1 + '.' + Math.floor(Math.random() * 6) + 1;
            let today = new Date().toISOString();

            eventBus.publish(airTemperatureAddress, '{"date":"' + today +
                '" ,"value":' + airValue +
                ' ,"sensorEnvironment":"AIR"' +
                ' ,"sensorType":"TEMPERATURE"}');

            eventBus.publish(waterTemperatureAddress, '{"date":"' + today +
                '" ,"value":' + waterValue +
                ' ,"sensorEnvironment":"WATER"' +
                ' ,"sensorType":"TEMPERATURE"}');

            console.log('airValue : ' + airValue);
            console.log('waterValue : ' + waterValue);
        });
    };
}

eventBus.onclose = (param) => {
    console.log('closed event bus', param);
};