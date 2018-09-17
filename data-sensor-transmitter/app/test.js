const EventBus = require('vertx3-eventbus-client');

const waterTemperatureAddress = 'water.temperature.address';
const airTemperatureAddress = 'air.temperature.address';
const eventBus = new EventBus('http://localhost:8080/eventbus');

eventBus.onopen = () => {
    console.log('open event bus');
    let value = Math.floor(Math.random() * 6) + 1;
    eventBus.publish(waterTemperatureAddress, '{"value":' + value + ',"sensorEnvironment":"WATER"}');
    eventBus.publish(airTemperatureAddress, '{"value":' + value + ',"sensorEnvironment":"AIR"}');
    console.log('value : ' + value);
}

eventBus.onclose = (param) => {
    console.log('closed event bus', param)
}
