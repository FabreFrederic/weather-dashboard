import {Component, Input, OnInit} from '@angular/core';
import {VertXEventBusService} from "../vertx/vertXEventBus.service";
import {Temperature} from "../business/temperature";

interface Point {
  x: number;
  y: number;
}

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  @Input()
  public airTemperatureTitle: string = 'Temperature';
  @Input()
  public airTemperatureSubtitle: string = 'air';
  @Input()
  public airTemperatureValue: number;
  @Input()
  public airTemperatureDate: number;
  @Input()
  public todayAirTemperatureUrl: string = '/air/temperature/today';

  @Input()
  public waterTemperatureTitle: string = 'Temperature';
  @Input()
  public waterTemperatureSubtitle: string = 'eau';
  @Input()
  public waterTemperatureValue: number;
  @Input()
  public waterTemperatureDate: number;
  @Input()
  public todayWaterTemperatureUrl: string = '/water/temperature/today';

  constructor(private vertXEventBusService: VertXEventBusService) {
  }

  ngOnInit(): void {
    this.vertXEventBusService.getAirTemperatureVertxObservable().subscribe((reading) => {
      console.log('reading : ' + JSON.stringify(reading));
      this.airTemperatureValue = reading.value;
      // this.airTemperatureDate = +reading.date;
      this.airTemperatureDate = reading.date;
    });

    this.vertXEventBusService.getWaterTemperatureVertxObservable().subscribe((reading) => {
      console.log('reading : ' + JSON.stringify(reading));
      this.waterTemperatureValue = reading.value;
      // this.waterTemperatureDate = +reading.date;
      this.waterTemperatureDate = reading.date;
    });

  }

}
