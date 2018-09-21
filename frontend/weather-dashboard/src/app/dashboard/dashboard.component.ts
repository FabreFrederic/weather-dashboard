import {Component, Input, OnInit} from '@angular/core';
import {VertXEventBusService} from "../vertx/vertXEventBus.service";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  @Input()
  public waterTemperatureTitle: string = 'Temperature';
  @Input()
  public waterTemperatureSubtitle: string = 'eau';
  @Input()
  public waterTemperatureValue: number;

  @Input()
  public airTemperatureTitle: string = 'Temperature';
  @Input()
  public airTemperatureSubtitle: string = 'air';
  @Input()
  public airTemperatureValue: number;

  constructor(private vertXEventBusService: VertXEventBusService) {
    vertXEventBusService.initialize(this.waterTemperatureNewValueCallback, this.airTemperatureNewValueCallback);
  }

  ngOnInit() {
  }

  /**
   * Vert.X event bus new value callback
   * @param {*} error Error message
   * @param {*} message Response message
   */
  public waterTemperatureNewValueCallback = (error: any, message: any) => {
    if (error) {
      console.error(error);
    } else {
      console.log('message : ' + JSON.stringify(message));
      this.waterTemperatureValue = message.body.value;
    }
  }

  /**
   * Vert.X event bus new value callback
   * @param {*} error Error message
   * @param {*} message Response message
   */
  public airTemperatureNewValueCallback = (error: any, message: any) => {
    if (error) {
      console.error(error);
    } else {
      console.log('message : ' + JSON.stringify(message));
      this.airTemperatureValue = message.body.value;
    }
  }

}
