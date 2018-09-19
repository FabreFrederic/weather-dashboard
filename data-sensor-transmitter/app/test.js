const EventBus = require('vertx3-eventbus-client');

const waterTemperatureAddress = 'water.temperature.address';
const airTemperatureAddress = 'air.temperature.address';
const eventBus = new EventBus('http://localhost:8080/eventbus');

eventBus.onopen = () => {
    console.log('open event bus');
    let waterValue = Math.floor(Math.random() * 6) + 1;
    let airValue = Math.floor(Math.random() * 6) + 1;
    eventBus.publish(waterTemperatureAddress, '{"value":' + waterValue + ',"sensorEnvironment":"WATER"}');
    eventBus.publish(airTemperatureAddress, '{"value":' + airValue + ',"sensorEnvironment":"AIR"}');
    console.log('waterValue : ' + waterValue);
    console.log('airValue : ' + airValue);
}

eventBus.onclose = (param) => {
    console.log('closed event bus', param)
}
