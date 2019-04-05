import {Injectable, OnDestroy} from '@angular/core';
import {Subject} from "rxjs/index";
import {Reading} from "../business/reading";

import * as EventBus from 'vertx3-eventbus-client';

export const VERTX_EVENTBUS_CONFIG = {
  eventBusRoute: window.location.protocol + '//' + window.location.hostname + ':8080/eventbus',
  waterAddress: 'water.temperature.frontend.address',
  airAddress: 'air.temperature.frontend.address',
};

@Injectable()
export class VertXEventBusService implements OnDestroy {

  private eventBus;
  private waterTemperatureVertxSubject = new Subject<Reading>();
  private airTemperatureVertxSubject = new Subject<Reading>();

  constructor() {
    console.log('VertXEventBusService constructor');

    const self = this;
    self.eventBus = new EventBus(VERTX_EVENTBUS_CONFIG.eventBusRoute);

    self.eventBus.onopen = function () {
      console.log('connected to vertx eventbus');
      self.eventBus.registerHandler(VERTX_EVENTBUS_CONFIG.waterAddress, self.waterCallback);
      self.eventBus.registerHandler(VERTX_EVENTBUS_CONFIG.airAddress, self.airCallback);

      self.eventBus.onclose = function () {
        console.log("disconnected from vertx eventbus");
        self.eventBus = null;
      };
    }
  }

  private waterCallback = (error: any, message: any) => {
    if (error) {
      console.error(error);
    } else if (message && message.body) {
      let temperature: Reading = new Reading(message.body.value, message.body.date);
      // Send the response to the observable
      this.waterTemperatureVertxSubject.next(temperature);
    } else {
      console.log('No body in water temperature callback');
    }
  }

  private airCallback = (error: any, message: any) => {
    if (error) {
      console.error(error);
    } else if (message && message.body) {
      let temperature: Reading = new Reading(message.body.value, message.body.date);
      // Send the response to the observable
      this.airTemperatureVertxSubject.next(temperature);
    } else {
      console.log('No body in air temperature callback');
    }
  }

  public getWaterTemperatureVertxObservable() {
    return this.waterTemperatureVertxSubject.asObservable();
  }

  public getAirTemperatureVertxObservable() {
    return this.airTemperatureVertxSubject.asObservable();
  }

  ngOnDestroy() {
    this.eventBus.close();
    console.log('eventBus close');
  }

}
