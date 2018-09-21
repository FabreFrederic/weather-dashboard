import {Injectable} from '@angular/core';

declare var EventBus: any;

export const VERTX_EVENTBUS_CONFIG = {
  eventBusRoute: window.location.protocol + '//' + window.location.hostname + ':8080/eventbus',
  waterAddress: 'water.temperature.frontend.address',
  airAddress: 'air.temperature.frontend.address',
};

@Injectable()
export class VertXEventBusService {

  constructor() {
  }

  public initialize(waterTemperatureNewValueCallback: any, airTemperatureNewValueCallback: any) {
    let eventBus = new EventBus(VERTX_EVENTBUS_CONFIG.eventBusRoute);
    eventBus.onopen = function () {
      console.log('connected');
      eventBus.registerHandler(VERTX_EVENTBUS_CONFIG.waterAddress, waterTemperatureNewValueCallback);
      eventBus.registerHandler(VERTX_EVENTBUS_CONFIG.airAddress, airTemperatureNewValueCallback);

      eventBus.onclose = function () {
        console.log("disconnected");
        eventBus = null;
      };
    }
  }
}
