import { Component, Input, OnInit } from '@angular/core';
import { VertXEventBusService } from '../vertx/vertXEventBus.service';
import { ReadingService } from '../service/reading.service';
import { ChartConfig } from '../chart/chartConfig';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  @Input()
  public waterTemperatureTitle = 'Temperature C°';
  @Input()
  public waterTemperatureSubtitle = 'eau';
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
  public airTemperatureTitle = 'Temperature C°';
  @Input()
  public airTemperatureSubtitle = 'air';
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

  constructor(private vertXEventBusService: VertXEventBusService,
              private readingService: ReadingService) {
    this.airTemperatureConfig.url = '/air/temperatures/today';
    this.airTemperatureConfig.xAxisType = 'datetime';
    this.airTemperatureConfig.yAxisTitleText = 'temperature C°';
    this.airTemperatureConfig.seriesName = 'Temperature de l\'air';

    this.waterTemperatureConfig.url = '/water/temperatures/today';
    this.waterTemperatureConfig.xAxisType = 'datetime';
    this.waterTemperatureConfig.yAxisTitleText = 'temperature C°';
    this.waterTemperatureConfig.seriesName = 'Temperature de l\'eau';
  }

  ngOnInit(): void {
    this.vertXEventBusService.getAirTemperatureVertxObservable().subscribe((reading) => {
      if (reading && reading.value) {
        this.airTemperatureValue = reading.value;
        this.airTemperatureDate = reading.date;
      }
    });

    this.vertXEventBusService.getAirTemperatureMinVertxObservable().subscribe((reading) => {
      if (reading && reading.value) {
        this.airTemperatureMinValue = reading.value;
        // TODO add date field
        // this.airTemperatureDate = reading.date;
      }
    });

    this.vertXEventBusService.getAirTemperatureMaxVertxObservable().subscribe((reading) => {
      if (reading && reading.value) {
        this.airTemperatureMaxValue = reading.value;
        // TODO add date field
        // this.airTemperatureDate = reading.date;
      }
    });

    this.vertXEventBusService.getWaterTemperatureVertxObservable().subscribe((reading) => {
      if (reading && reading.value) {
        this.waterTemperatureValue = reading.value;
        this.waterTemperatureDate = reading.date;
      }
    });

    this.vertXEventBusService.getWaterTemperatureMinVertxObservable().subscribe((reading) => {
      if (reading && reading.value) {
        this.waterTemperatureMinValue = reading.value;
        // TODO add date field
        // this.airTemperatureDate = reading.date;
      }
    });

    this.vertXEventBusService.getWaterTemperatureMaxVertxObservable().subscribe((reading) => {
      if (reading && reading.value) {
        this.waterTemperatureMaxValue = reading.value;
        // TODO add date field
        // this.airTemperatureDate = reading.date;
      }
    });

    this.readingService.getSingleReading('/air/temperature/today/last').subscribe((reading) => {
      if (reading[0] && reading[0].value) {
        this.airTemperatureValue = reading[0].value;
      }
    });

    this.readingService.getSingleReading('/water/temperature/today/last').subscribe((reading) => {
      if (reading[0] && reading[0].value) {
        this.waterTemperatureValue = reading[0].value;
      }
    });

    this.readingService.getSingleReading('/air/temperature/today/min').subscribe((reading) => {
      if (reading[0] && reading[0].value) {
        this.airTemperatureMinValue = reading[0].value;
      }
    });

    this.readingService.getSingleReading('/air/temperature/today/max').subscribe((reading) => {
      if (reading[0] && reading[0].value) {
        this.airTemperatureMaxValue = reading[0].value;
      }
    });

    this.readingService.getSingleReading('/water/temperature/today/min').subscribe((reading) => {
      if (reading[0] && reading[0].value) {
        this.waterTemperatureMinValue = reading[0].value;
      }
    });

    this.readingService.getSingleReading('/water/temperature/today/max').subscribe((reading) => {
      if (reading[0] && reading[0].value) {
        this.waterTemperatureMaxValue = reading[0].value;
      }
    });
  }

}
