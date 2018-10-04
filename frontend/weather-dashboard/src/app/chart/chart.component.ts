import {Component, OnInit} from '@angular/core';
import {Chart} from 'angular-highcharts';
import {VertXEventBusService} from "../vertx/vertXEventBus.service";
import {Subscription} from "rxjs/index";
import * as Highcharts from 'highcharts';

interface TemperaturePoint {
  x: number;
  y: number;
}

@Component({
  selector: 'app-chart',
  templateUrl: './chart.component.html',
  styleUrls: ['./chart.component.css']
})
export class ChartComponent implements OnInit {
  private temperaturePoints: TemperaturePoint[] = [];
  chart: Chart;
  chartOptions: any;
  readingSubscription: Subscription = null;

  constructor(private vertXEventBusService: VertXEventBusService) {
  }

  ngOnInit(): void {
    this.chartOptions = {
      global: {
        timezoneOffset: new Date().getTimezoneOffset()
      },
      title: {
        text: null
      },
      xAxis: {
        type: 'datetime'
      },
      yAxis: {
        title: {
          text: 'Temperature C°'
        }
      },
      credits: {
        enabled: false
      },
      navigator: {
        enabled: true
      },
      rangeSelector: {
        enabled: false
      },
      series: [{
        type: 'areaspline',
        name: 'temperature',
        fillColor: {
          linearGradient: {x1: 0, y1: 1, x2: 0, y2: 0},
          stops: [
            [0, '#BBE2DE'],
            [1, '#f4f4f4']
          ]
        },
        color: '#BBE2DE',
        data: this.temperaturePoints
      }]
    };

    Highcharts.setOptions({
      global: {
        timezoneOffset: new Date().getTimezoneOffset()
      }
    });

    this.chart = new Chart(this.chartOptions);

    this.readingSubscription = this.vertXEventBusService.getWaterTemperatureVertxObservable()
      .subscribe((reading) => {
        console.log('reading : ' + JSON.stringify(+reading.temperature));
        this.chart.ref.series[0].addPoint([+new Date(),
          +reading.temperature], true, false);
      });
  }

}
