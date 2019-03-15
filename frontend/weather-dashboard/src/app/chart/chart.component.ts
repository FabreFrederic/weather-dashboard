import {Component, Input, OnChanges, OnInit, SimpleChange} from '@angular/core';
import {Chart} from 'angular-highcharts';
import * as Highcharts from 'highcharts';

interface Point {
  x: number;
  y: number;
}

@Component({
  selector: 'app-chart',
  templateUrl: './chart.component.html',
  styleUrls: ['./chart.component.css']
})
export class ChartComponent implements OnInit, OnChanges {
  @Input()
  public lastValue: number;
  @Input()
  public lastDate: number;
  @Input()
  public points: Point[] = [];

  chart: Chart;
  chartOptions: any;

  constructor() {
    this.setOptions();
    this.chart = new Chart(this.chartOptions);
    Highcharts.setOptions({
      global: {
        timezoneOffset: new Date().getTimezoneOffset()
      }
    });
  }

  ngOnInit(): void {
    // TODO : call the service to retrieve the today value
    this.points.push({
      x: +new Date(),
      y: 1
    });
  }

  private setOptions() {
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
        data: this.points
      }]
    };
  }

  ngOnChanges(changes: { [propertyName: string]: SimpleChange }) {
    if (changes['lastDate'] && this.lastDate) {
      // console.log("new reading to display in chart");
      if (this.chart) {
        this.chart.ref.series[0].addPoint([+new Date(this.lastDate),
          +this.lastValue], true, false);
      }
    }
  }

}
