const EventBus = require('vertx3-eventbus-client');
const cron = require("node-cron");

const waterTemperatureAddress = 'water.temperature.address';
const airTemperatureAddress = 'air.temperature.address';
const eventBus = new EventBus('http://localhost:8080/eventbus');

eventBus.onopen = () => {
    cron.schedule("*/1 * * * * *", function() {
        let waterValue = Math.floor(Math.random() * 6) + 1 + '.' + Math.floor(Math.random() * 6) + 1;
        let airValue = Math.floor(Math.random() * 6) + 1 + '.' + Math.floor(Math.random() * 6) + 1;
        let today = new Date().toISOString();

        eventBus.publish(waterTemperatureAddress, '{"date":"' + today +
            '" ,"value":' + waterValue +
            ' ,"sensorEnvironment":"WATER"' +
            ' ,"sensorType":"TEMPERATURE"}');

        eventBus.publish(airTemperatureAddress, '{"date":"' + today +
            '" ,"value":' + airValue +
            ' ,"sensorEnvironment":"AIR"' +
            ' ,"sensorType":"TEMPERATURE"}');

        console.log('waterValue : ' + waterValue);
        console.log('airValue : ' + airValue);
    });

    console.log('open event bus');
}

eventBus.onclose = (param) => {
    console.log('closed event bus', param)
}
