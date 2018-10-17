const EventBus = require('vertx3-eventbus-client');

const waterTemperatureAddress = 'water.temperature.address';
const airTemperatureAddress = 'air.temperature.address';
const eventBus = new EventBus('http://localhost:8080/eventbus');
const moment = require('moment');

eventBus.onopen = () => {
    console.log('open event bus');
    let waterValue = Math.floor(Math.random() * 6) + 1;
    let airValue = Math.floor(Math.random() * 6) + 1;

    let today = moment().format('YYYY/MM/DD');

    eventBus.publish(waterTemperatureAddress, '{"date":"' + today + '" ,"value":' + waterValue + ' ,"sensorEnvironment":"WATER"}');
    eventBus.publish(airTemperatureAddress, '{"date":"' + today + '" ,"value":' + airValue + ' ,"sensorEnvironment":"AIR"}');
    console.log('waterValue : ' + waterValue);
    console.log('airValue : ' + airValue);
}

eventBus.onclose = (param) => {
    console.log('closed event bus', param)
}
