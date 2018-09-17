import {Injectable} from '@angular/core';

declare var EventBus: any;

export const VERTX_EVENTBUS_CONFIG = {
  eventBusRoute: window.location.protocol + '//' + window.location.hostname + ':8080/eventbus',
  temperatureAddress: 'water.temperature.frontend.address'
};

@Injectable()
export class VertXEventBusService {

  constructor() {
  }

  public initialize(newValueCallback: any) {
    let eventBus = new EventBus(VERTX_EVENTBUS_CONFIG.eventBusRoute);
    eventBus.onopen = function () {
      console.log('connected');
      eventBus.registerHandler(VERTX_EVENTBUS_CONFIG.temperatureAddress, newValueCallback);

      eventBus.onclose = function () {
        console.log("disconnected");
        eventBus = null;
      };
    }
  }
}
