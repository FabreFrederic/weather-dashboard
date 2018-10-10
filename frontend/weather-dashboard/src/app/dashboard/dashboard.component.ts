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
  public waterTemperatureDate: number;

  @Input()
  public airTemperatureTitle: string = 'Temperature';
  @Input()
  public airTemperatureSubtitle: string = 'air';
  @Input()
  public airTemperatureValue: number;
  @Input()
  public airTemperatureDate: number;

  constructor(private vertXEventBusService: VertXEventBusService) {
  }

  ngOnInit(): void {
    console.log('OnInit');

    this.vertXEventBusService.getWaterTemperatureVertxObservable()
      .subscribe((reading) => {
        console.log('reading : ' + JSON.stringify(reading));
        this.waterTemperatureValue = reading.temperature;
        // this.waterTemperatureDate = +reading.date;
        this.waterTemperatureDate = +new Date();
      });

    this.vertXEventBusService.getAirTemperatureVertxObservable()
      .subscribe((reading) => {
        console.log('reading : ' + JSON.stringify(reading));
        this.airTemperatureValue = reading.temperature;
        // this.airTemperatureDate = +reading.date;
        this.airTemperatureDate = +new Date();
      });
  }
}
