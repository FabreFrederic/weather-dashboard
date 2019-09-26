import {Component, Input, OnInit} from '@angular/core';
import {VertXEventBusService} from "../vertx/vertXEventBus.service";
import {ChartConfig} from "../chart/chartConfig";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  @Input()
  public waterTemperatureTitle: string = 'Temperature C°';
  @Input()
  public waterTemperatureSubtitle: string = 'eau';
  @Input()
  public waterTemperatureValue: number;
  @Input()
  public waterTemperatureMinValue: number;
  @Input()
  public waterTemperatureMaxValue: number;
  @Input()
  public waterTemperatureDate: number;
  @Input()
  public waterTemperatureConfig: ChartConfig = new ChartConfig();

  @Input()
  public airTemperatureTitle: string = 'Temperature C°';
  @Input()
  public airTemperatureSubtitle: string = 'air';
  @Input()
  public airTemperatureValue: number;
  @Input()
  public minSubtitle = 'Min';
  @Input()
  public airTemperatureMinValue: number;
  @Input()
  public maxSubtitle = 'Max';
  @Input()
  public airTemperatureMaxValue: number;
  @Input()
  public airTemperatureDate: number;
  @Input()
  public airTemperatureConfig: ChartConfig = new ChartConfig();

  constructor(private vertXEventBusService: VertXEventBusService) {
    this.airTemperatureConfig.url = '/air/temperatures/today';
    this.airTemperatureConfig.xAxisType = 'datetime';
    this.airTemperatureConfig.yAxisTitleText = 'temperature C°';
    this.airTemperatureConfig.seriesName = "Temperature de l'air";

    this.waterTemperatureConfig.url = '/water/temperatures/today';
    this.waterTemperatureConfig.xAxisType = 'datetime';
    this.waterTemperatureConfig.yAxisTitleText = 'temperature C°';
    this.waterTemperatureConfig.seriesName = "Temperature de l'eau";
  }

  ngOnInit(): void {
    this.vertXEventBusService.getAirTemperatureVertxObservable().subscribe((reading) => {
      this.airTemperatureValue = reading.value;
      this.airTemperatureDate = reading.date;
    });

    this.vertXEventBusService.getAirTemperatureMinVertxObservable().subscribe((reading) => {
      this.airTemperatureMinValue = reading.value;
      // this.airTemperatureDate = reading.date;
    });

    this.vertXEventBusService.getAirTemperatureMaxVertxObservable().subscribe((reading) => {
      this.airTemperatureMaxValue = reading.value;
      // this.airTemperatureDate = reading.date;
    });

    this.vertXEventBusService.getWaterTemperatureVertxObservable().subscribe((reading) => {
      this.waterTemperatureValue = reading.value;
      this.waterTemperatureDate = reading.date;
    });

    this.vertXEventBusService.getWaterTemperatureMinVertxObservable().subscribe((reading) => {
      this.waterTemperatureMinValue = reading.value;
      // this.airTemperatureDate = reading.date;
    });

    this.vertXEventBusService.getWaterTemperatureMaxVertxObservable().subscribe((reading) => {
      this.waterTemperatureMaxValue = reading.value;
      // this.airTemperatureDate = reading.date;
    });

  }

}
