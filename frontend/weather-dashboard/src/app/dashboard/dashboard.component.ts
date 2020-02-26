import {Component, Input, OnInit} from '@angular/core';
import {VertXEventBusService} from '../vertx/vertXEventBus.service';
import {ReadingService} from '../service/reading.service';
import {ChartConfig} from '../chart/chartConfig';
import { Reading } from '../business/reading';

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
      this.airTemperatureValue = reading.value;
      this.airTemperatureDate = reading.date;
    });

    this.vertXEventBusService.getAirTemperatureMinVertxObservable().subscribe((reading) => {
      this.airTemperatureMinValue = reading.value;
      // TODO add date field
      // this.airTemperatureDate = reading.date;
    });

    this.vertXEventBusService.getAirTemperatureMaxVertxObservable().subscribe((reading) => {
      this.airTemperatureMaxValue = reading.value;
      // TODO add date field
      // this.airTemperatureDate = reading.date;
    });

    this.vertXEventBusService.getWaterTemperatureVertxObservable().subscribe((reading) => {
      this.waterTemperatureValue = reading.value;
      this.waterTemperatureDate = reading.date;
    });

    this.vertXEventBusService.getWaterTemperatureMinVertxObservable().subscribe((reading) => {
      this.waterTemperatureMinValue = reading.value;
      // TODO add date field
      // this.airTemperatureDate = reading.date;
    });

    this.vertXEventBusService.getWaterTemperatureMaxVertxObservable().subscribe((reading) => {
      this.waterTemperatureMaxValue = reading.value;
      // TODO add date field
      // this.airTemperatureDate = reading.date;
    });

    this.readingService.getSingleReading('/air/temperature/today/min').subscribe((reading) => {
      console.log('reading: ', reading);
      this.airTemperatureMinValue = reading[0].value;
    });

    this.readingService.getSingleReading('/air/temperature/today/max').subscribe((reading) => {
      console.log('reading: ', reading);
      this.airTemperatureMaxValue = reading[0].value;
    });

    this.readingService.getSingleReading('/water/temperature/today/min').subscribe((reading) => {
      console.log('reading: ', reading);
      this.waterTemperatureMinValue = reading[0].value;
    });

    this.readingService.getSingleReading('/water/temperature/today/max').subscribe((reading) => {
      console.log('reading: ', reading);
      this.waterTemperatureMaxValue = reading[0].value;
    });
  }

}
