import {Component, OnInit} from '@angular/core';
import * as Highcharts from 'highcharts';
import {VertXEventBusService} from "../vertx/vertXEventBus.service";

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
  Highcharts: any;
  chartOptions: any;

  constructor(private vertXEventBusService: VertXEventBusService) {
    this.Highcharts = Highcharts;
    this.chartOptions = {
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
  }

  ngOnInit() {
    this.vertXEventBusService.initialize(this.waterTemperatureNewValueCallback, this.airTemperatureNewValueCallback);
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
      console.log(this.Highcharts);
      this.Highcharts.charts[0].series[0].addPoint([+new Date(),
        message.body.value], true, false);
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
      //this.airTemperatureValue = message.body.value;
    }
  }


}
