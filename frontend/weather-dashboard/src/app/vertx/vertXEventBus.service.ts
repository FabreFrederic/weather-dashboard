import {Injectable, OnDestroy} from '@angular/core';
import {Subject} from "rxjs/index";
import {Reading} from "../business/reading";

import * as EventBus from 'vertx3-eventbus-client';

export const VERTX_EVENTBUS_CONFIG = {
  eventBusRoute: window.location.protocol + '//' + window.location.hostname + ':8082/eventbus',
  airTemperatureAddress: 'air.temperature.new.address',
  airTemperatureMinAddress: 'air.temperature.min.address',
  airTemperatureMaxAddress: 'air.temperature.max.address',
  waterTemperatureAddress: 'water.temperature.new.address',
  waterTemperatureMinAddress: 'water.temperature.min.address',
  waterTemperatureMaxAddress: 'water.temperature.max.address'
};

@Injectable()
export class VertXEventBusService implements OnDestroy {

  private eventBus;
  private airTemperatureVertxSubject = new Subject<Reading>();
  private airTemperatureMinVertxSubject = new Subject<Reading>();
  private airTemperatureMaxVertxSubject = new Subject<Reading>();
  private waterTemperatureVertxSubject = new Subject<Reading>();
  private waterTemperatureMinVertxSubject = new Subject<Reading>();
  private waterTemperatureMaxVertxSubject = new Subject<Reading>();


  constructor() {
    console.log('VertXEventBusService constructor');

    const self = this;
    self.eventBus = new EventBus(VERTX_EVENTBUS_CONFIG.eventBusRoute);

    self.eventBus.onopen = function () {
      console.log('connected to vertx eventbus');
      self.eventBus.registerHandler(VERTX_EVENTBUS_CONFIG.airTemperatureAddress, self.airTemperatureCallback);
      self.eventBus.registerHandler(VERTX_EVENTBUS_CONFIG.airTemperatureMinAddress, self.airTemperatureMinCallback);
      self.eventBus.registerHandler(VERTX_EVENTBUS_CONFIG.airTemperatureMaxAddress, self.airTemperatureMaxCallback);

      self.eventBus.registerHandler(VERTX_EVENTBUS_CONFIG.waterTemperatureAddress, self.waterTemperatureCallback);
      self.eventBus.registerHandler(VERTX_EVENTBUS_CONFIG.waterTemperatureMinAddress, self.waterTemperatureMinCallback);
      self.eventBus.registerHandler(VERTX_EVENTBUS_CONFIG.waterTemperatureMaxAddress, self.waterTemperatureMaxCallback);
      self.eventBus.onclose = function () {
        console.log("disconnected from vertx eventbus");
        self.eventBus = null;
      };
    }
  }

  private airTemperatureCallback = (error: any, message: any) => {
    console.error(message);

    if (error) {
      console.error(error);
    } else if (message && message.body) {
      const body = JSON.parse(message.body.toString());

      console.log(body.value);

      let temperature: Reading = new Reading(body.value, body.date);
      // Send the response to the observable
      this.airTemperatureVertxSubject.next(temperature);
    } else {
      console.log('No body in callback');
    }
  };

  private airTemperatureMinCallback = (error: any, message: any) => {
    if (error) {
      console.error(error);
    } else if (message && message.body) {
      const body = JSON.parse(message.body);
      let temperature: Reading = new Reading(body.value, body.date);
      // Send the response to the observable
      this.airTemperatureMinVertxSubject.next(temperature);
    } else {
      console.log('No body in callback');
    }
  };

  private airTemperatureMaxCallback = (error: any, message: any) => {
    if (error) {
      console.error(error);
    } else if (message && message.body) {
      const body = JSON.parse(message.body);
      let temperature: Reading = new Reading(body.value, body.date);
      // Send the response to the observable
      this.airTemperatureMaxVertxSubject.next(temperature);
    } else {
      console.log('No body in callback');
    }
  };

  private waterTemperatureCallback = (error: any, message: any) => {
    console.error(message);

    if (error) {
      console.error(error);
    } else if (message && message.body) {
      const body = JSON.parse(message.body);
      let temperature: Reading = new Reading(body.value, body.date);
      // Send the response to the observable
      this.waterTemperatureVertxSubject.next(temperature);
    } else {
      console.log('No body in callback');
    }
  };

  private waterTemperatureMinCallback = (error: any, message: any) => {
    if (error) {
      console.error(error);
    } else if (message && message.body) {
      console.log('waterTemperatureMinCallback : {}', message.body);
      const body = JSON.parse(message.body);
      let temperature: Reading = new Reading(body.value, body.date);
      // Send the response to the observable
      this.waterTemperatureMinVertxSubject.next(temperature);
    } else {
      console.log('No body in callback');
    }
  };

  private waterTemperatureMaxCallback = (error: any, message: any) => {
    if (error) {
      console.error(error);
    } else if (message && message.body) {
      const body = JSON.parse(message.body);
      let temperature: Reading = new Reading(body.value, body.date);
      // Send the response to the observable
      this.waterTemperatureMaxVertxSubject.next(temperature);
    } else {
      console.log('No body in callback');
    }
  };


  public getAirTemperatureVertxObservable() {
    return this.airTemperatureVertxSubject.asObservable();
  }

  public getAirTemperatureMinVertxObservable() {
    return this.airTemperatureMinVertxSubject.asObservable();
  }

  public getAirTemperatureMaxVertxObservable() {
    return this.airTemperatureMaxVertxSubject.asObservable();
  }

  public getWaterTemperatureVertxObservable() {
    return this.waterTemperatureVertxSubject.asObservable();
  }

  public getWaterTemperatureMinVertxObservable() {
    return this.waterTemperatureMinVertxSubject.asObservable();
  }

  public getWaterTemperatureMaxVertxObservable() {
    return this.waterTemperatureMaxVertxSubject.asObservable();
  }

  ngOnDestroy() {
    this.eventBus.close();
    console.log('eventBus close');
  }

}
